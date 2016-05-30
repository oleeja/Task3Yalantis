package com.kitsyambochka.yalantistask3.activities.mvp;

import android.widget.TextView;

/**
 * Created by Developer on 29.05.2016.
 */
public class InfoViewHolder {
    public final TextView mTvEconomy;
    public final TextView mTvInWork;
    public final TextView mTvCreatedDate;
    public final TextView mTvRegisteredDate;
    public final TextView mTvDecisionDate;
    public final TextView mTvResponsibleName;
    public final TextView mTvDescription;

    public InfoViewHolder(TextView tvEconomy, TextView tvInWork, TextView tvCreatedDate,
                          TextView tvRegisteredDate, TextView tvDecisionDate,
                          TextView tvResponsibleName, TextView tvDescription) {
        mTvEconomy = tvEconomy;
        mTvInWork = tvInWork;
        mTvCreatedDate = tvCreatedDate;
        mTvRegisteredDate = tvRegisteredDate;
        mTvDecisionDate = tvDecisionDate;
        mTvResponsibleName = tvResponsibleName;
        mTvDescription = tvDescription;
    }
}
