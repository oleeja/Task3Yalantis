package com.kitsyambochka.yalantistask3.utils;

import com.kitsyambochka.yalantistask3.model.Ticket;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Developer on 27.05.2016.
 */
public class DatabaseLoader {

    private Realm mRealm;
    private static final String STATE_IN_PROGRESS = "0,9,5,7,8";
    private static final String STATE_DONE = "10,6";
    private static final String STATE_PENDING = "1,3,4";
    private static final String BASE_STATE_IN_PROGRESS = "В роботі";
    private static final String BASE_STATE_DONE = "Виконано";
    private static final String BASE_STATE_PENDING = "На модерації";

    public RealmResults<Ticket> getTicketFromBase(String state) {
        initRealm();

        String name = getStateName(state);

        return mRealm.where(Ticket.class).equalTo("state.name", name).findAll();
    }

    public void cleanBase(String state){
        initRealm();

        String name = getStateName(state);

        mRealm.beginTransaction();
        RealmResults<Ticket> results = mRealm.where(Ticket.class).equalTo("state.name", name).findAll();
        results.deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    private void initRealm(){
        mRealm = Realm.getDefaultInstance();
    }


    private String getStateName (String state){
        switch (state){
            case STATE_IN_PROGRESS:
                return BASE_STATE_IN_PROGRESS;
            case STATE_DONE:
                return BASE_STATE_DONE;
            case STATE_PENDING:
                return BASE_STATE_PENDING;
            default:
                return null;
        }
    }
}
