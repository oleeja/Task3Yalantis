package com.kitsyambochka.yalantistask3.activities.mvp;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Developer on 29.05.2016.
 */
public class InfoPresenterImpl implements InfoPresenter {
    private final InfoModel mModel;
    private final InfoView mView;

    public InfoPresenterImpl(InfoModel model, InfoView view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState, int id) {
        mView.showResponse(mModel.request(id));
    }
}
