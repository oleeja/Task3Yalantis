package com.kitsyambochka.yalantistask3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.kitsyambochka.yalantistask3.R;
import com.kitsyambochka.yalantistask3.model.FBProfile;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;



public class FBActivity extends AppCompatActivity {

    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.picture_view)ProfilePictureView mIvPictureProfile;
    @BindView(R.id.tv_authorization) TextView mTvAuthorization;
    @BindView(R.id.login_button)LoginButton mLoginButton;

    private CallbackManager mCallbackManager;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb);
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();

        mCallbackManager = CallbackManager.Factory.create();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.profile);
        }

        Profile mProfile = Profile.getCurrentProfile();

        if(mProfile != null){
            mIvPictureProfile.setProfileId(mProfile.getId());
            String name = mProfile.getFirstName()+" "+mProfile.getLastName();
            mTvName.setText(name);
        }else {
            mTvAuthorization.setText(R.string.authorization_please);
            mTvAuthorization.setVisibility(View.VISIBLE);
        }

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(final LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {

                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {

                            mProfileTracker.stopTracking();

                            mTvAuthorization.setVisibility(View.INVISIBLE);
                            mIvPictureProfile.setProfileId(profile2.getId());

                            String name = profile2.getFirstName()+" "+profile2.getLastName();
                            mTvName.setText(name);

                            FBProfile fbProfile = new FBProfile();
                            fbProfile.setFirstName(profile2.getFirstName());
                            fbProfile.setLastName(profile2.getLastName());
                            fbProfile.setId(profile2.getId());
                            fbProfile.setLinkUri(String.valueOf(profile2.getLinkUri()));
                            fbProfile.setToken(loginResult.getAccessToken().getToken());

                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(fbProfile);
                            mRealm.commitTransaction();
                        }
                    };
                }
            }

            @Override
            public void onCancel() {
                // TODO: some operation when login cancel
            }

            @Override
            public void onError(FacebookException exception) {
                // TODO: some operation when error
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
