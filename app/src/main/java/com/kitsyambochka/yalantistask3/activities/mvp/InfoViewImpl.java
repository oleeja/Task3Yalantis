package com.kitsyambochka.yalantistask3.activities.mvp;


import com.kitsyambochka.yalantistask3.R;
import com.kitsyambochka.yalantistask3.model.Ticket;
import com.kitsyambochka.yalantistask3.utils.Utilities;

/**
 * Created by Developer on 29.05.2016.
 */
public class InfoViewImpl implements InfoView {
    private final InfoViewHolder mViewHolder;

    public InfoViewImpl(final InfoViewHolder viewHolder) {
        mViewHolder = viewHolder;
    }
    @Override
    public void showResponse(Ticket result) {
        mViewHolder.mTvEconomy.setText(result.getCategory().getName());
        mViewHolder.mTvInWork.setText(result.getState().getName());
        mViewHolder.mTvCreatedDate.setText(Utilities.getFullDataFromMillis(result.getCreatedDate()));
        mViewHolder.mTvRegisteredDate.setText(Utilities.getFullDataFromMillis(result.getStartDate()));
        mViewHolder.mTvDecisionDate.setText(Utilities.getFullDataFromMillis(result.getDeadline()));
        mViewHolder.mTvResponsibleName.setText(R.string.responsible_name);
        mViewHolder.mTvDescription.setText(result.getBody());
    }
}
