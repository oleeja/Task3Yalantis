package com.kitsyambochka.yalantistask3.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kitsyambochka.yalantistask3.network.ApiService;
import com.kitsyambochka.yalantistask3.utils.DatabaseLoader;
import com.kitsyambochka.yalantistask3.model.Ticket;
import com.kitsyambochka.yalantistask3.R;
import com.kitsyambochka.yalantistask3.adapters.MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Developer on 14.04.2016.
 */
public class FragmentForRecyclerView extends android.support.v4.app.Fragment{
    public final static String ITEMS_KEY = "FragmentForRecyclerView$ItemsState";
    private static final int AMOUNT = 20;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MainRecyclerAdapter mRecyclerAdapter;
    private Realm mRealm;
    private ProgressDialog mProgressDialog;

    private List <Ticket> mTickets;

    private int mCounter;
    private boolean mScrollFlag;

    public static FragmentForRecyclerView createInstance(String itemsState) {
        FragmentForRecyclerView fragmentForRecyclerView = new FragmentForRecyclerView();
        Bundle bundle = new Bundle();
        bundle.putString(ITEMS_KEY, itemsState);
        fragmentForRecyclerView.setArguments(bundle);
        return fragmentForRecyclerView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(
                R.layout.recycler_view_layout, container, false);
        return mSwipeRefreshLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Bundle bundle = getArguments();
        final String state = bundle.getString(ITEMS_KEY);

        initCreatePage(state);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager lManager = (LinearLayoutManager) layoutManager;
                    int lastVisibleItem = lManager.findLastVisibleItemPosition();

                    if (dy>0 && lastVisibleItem == mCounter-1 && mScrollFlag) {
                        downloadDate(state, mCounter);
                        mScrollFlag = false;
                    }
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DatabaseLoader().cleanBase(state);

                mCounter = 0;
                mSwipeRefreshLayout.setRefreshing(false);
                initCreatePage(state);
            }
        });
    }

    public void downloadDate(final String state, int offset){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.show();
        ApiService service = ApiService.Factory.createRetrofitService(ApiService.class);
        service.getTickets(offset, state, AMOUNT)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Ticket>>() {
                    @Override
                    public final void onCompleted() {
                        // TODO: some operation when data loaded
                    }

                    @Override
                    public final void onError(Throwable e) {
                        // TODO: some operation when error
                    }

                    @Override
                    public final void onNext(List<Ticket> response) {
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(response);
                        mRealm.commitTransaction();

                        mTickets.addAll(response);
                        mRecyclerAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                        mCounter = mTickets.size();
                        mScrollFlag = true;
                    }
                });
    }

    public void initCreatePage(String state){
        mTickets = new ArrayList<>(new DatabaseLoader().getTicketFromBase(state));

        int startOffset = 0;

        mRecyclerAdapter = new MainRecyclerAdapter(mTickets, getContext());
        mRecyclerView.setAdapter(mRecyclerAdapter);

        if(mTickets.isEmpty()){
            downloadDate(state, startOffset);
        }else {
            mCounter = mTickets.size();
            mScrollFlag = true;
        }
    }

}

