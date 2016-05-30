package com.kitsyambochka.yalantistask3.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kitsyambochka.yalantistask3.R;
import com.kitsyambochka.yalantistask3.adapters.InfoAdapterForRecyclerView;
import com.kitsyambochka.yalantistask3.model.File;
import com.kitsyambochka.yalantistask3.model.Ticket;
import com.kitsyambochka.yalantistask3.activities.mvp.InfoModel;
import com.kitsyambochka.yalantistask3.activities.mvp.InfoModelImpl;
import com.kitsyambochka.yalantistask3.activities.mvp.InfoPresenter;
import com.kitsyambochka.yalantistask3.activities.mvp.InfoPresenterImpl;
import com.kitsyambochka.yalantistask3.activities.mvp.InfoView;
import com.kitsyambochka.yalantistask3.activities.mvp.InfoViewHolder;
import com.kitsyambochka.yalantistask3.activities.mvp.InfoViewImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class InfoActivity extends AppCompatActivity {

    @BindView(R.id.info_toolbar) Toolbar mToolbar;
    @BindView(R.id.textViewEconomy) TextView mTvEconomy;
    @BindView(R.id.textViewInWork) TextView mTvInWork;
    @BindView(R.id.textViewCreatedDate) TextView mTvCreatedDate;
    @BindView(R.id.textViewRegisteredDate) TextView mTvRegisteredDate;
    @BindView(R.id.textViewDecisionDate) TextView mTvDecisionDate;
    @BindView(R.id.textViewResponsibleName) TextView mTvResponsibleName;
    @BindView(R.id.textViewDescription) TextView mTvDescription;
    @BindView(R.id.textViewCreated) TextView mTvCreated;
    @BindView(R.id.textViewRegistered) TextView mTvRegistered;
    @BindView(R.id.textViewDecision) TextView mTvDecision;
    @BindView(R.id.textViewResponsible) TextView mTvResponsible;
    @BindView(R.id.info_recyclerView) RecyclerView mRecyclerView;

    private List<Uri> mImageUri;

    private static final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mImageUri = new ArrayList<>();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        InfoViewHolder infoViewHolder = new InfoViewHolder(
                mTvEconomy,
                mTvInWork,
                mTvCreatedDate,
                mTvRegisteredDate,
                mTvDecisionDate,
                mTvResponsibleName,
                mTvDescription
        );

        InfoModel infoModel = new InfoModelImpl();
        InfoView infoView = new InfoViewImpl(infoViewHolder);
        InfoPresenter infoPresenter = new InfoPresenterImpl(infoModel, infoView);
        infoPresenter.onCreate(this, savedInstanceState, getIntent().getIntExtra(ID, 0));

        Realm realm = Realm.getDefaultInstance();

        Ticket ticket = realm.where(Ticket.class).equalTo(ID, getIntent().getIntExtra(ID, 0)).findFirst();
        getSupportActionBar().setTitle(ticket.getTicketId());

        setValues(ticket);

        addListeners();
    }

    private void setValues(Ticket ticket) {

        List <File> list = ticket.getFiles();

        for (File i : list) {
            mImageUri.add(Uri.parse(getString(R.string.photo_link)+i.getFilename()));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        InfoAdapterForRecyclerView adapter = new InfoAdapterForRecyclerView(mImageUri, this);
        mRecyclerView.setAdapter(adapter);
    }

    private void addListeners() {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InfoActivity.this, v.getClass().getSimpleName(), Toast.LENGTH_SHORT)
                        .show();
            }
        };

        mTvEconomy.setOnClickListener(onClickListener);
        mTvInWork.setOnClickListener(onClickListener);
        mTvCreatedDate.setOnClickListener(onClickListener);
        mTvRegisteredDate.setOnClickListener(onClickListener);
        mTvDecisionDate.setOnClickListener(onClickListener);
        mTvResponsibleName.setOnClickListener(onClickListener);
        mTvDescription.setOnClickListener(onClickListener);
        mTvCreated.setOnClickListener(onClickListener);
        mTvRegistered.setOnClickListener(onClickListener);
        mTvDecision.setOnClickListener(onClickListener);
        mTvResponsible.setOnClickListener(onClickListener);

        mToolbar.setOnClickListener(onClickListener);

        mRecyclerView.setOnClickListener(onClickListener);
    }
}
