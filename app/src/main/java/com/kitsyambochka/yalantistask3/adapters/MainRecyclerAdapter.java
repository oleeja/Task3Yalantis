package com.kitsyambochka.yalantistask3.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kitsyambochka.yalantistask3.R;
import com.kitsyambochka.yalantistask3.activities.InfoActivity;
import com.kitsyambochka.yalantistask3.model.Ticket;
import com.kitsyambochka.yalantistask3.utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 14.04.2016.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {
    private List<Ticket> mTicketList;
    private Context mContext;
    private final static String ID = "id";

    public MainRecyclerAdapter(List<Ticket> ticketList, Context context) {
        mTicketList = ticketList;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_economy) TextView mTvItemEconomy;
        @BindView(R.id.item_address) TextView mTvItemAddress;
        @BindView(R.id.item_date) TextView mTvItemDate;
        @BindView(R.id.item_days) TextView mTvItemDays;
        @BindView(R.id.likeSum) TextView mTvItemLike;
        @BindView(R.id.main_icon) ImageView mTvItemIcon;
        @BindView(R.id.item_layout)LinearLayout mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Ticket ticket = mTicketList.get(position);
        holder.mTvItemEconomy.setText(ticket.getCategory().getName());
        holder.mTvItemAddress.setText(Utilities.getFullAddress(ticket));
        holder.mTvItemDate.setText(Utilities.getStringDataFromMillis(ticket.getCreatedDate()));
        holder.mTvItemDays.setText(Utilities.getDaysToDeadLine(ticket.getDeadline()));
        holder.mTvItemLike.setText(String.valueOf(ticket.getLikesCounter()));
        holder.mTvItemIcon.setImageResource(R.drawable.ic_doc);

        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InfoActivity.class);
                intent.putExtra(ID, ticket.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTicketList.size();
    }
}
