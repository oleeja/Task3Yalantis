package com.kitsyambochka.yalantistask3.activities.mvp;

import com.kitsyambochka.yalantistask3.model.Ticket;
import io.realm.Realm;

/**
 * Created by Developer on 29.05.2016.
 */
public class InfoModelImpl implements InfoModel {


    @Override
    public Ticket request(int id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Ticket.class).equalTo("id", id).findFirst();
    }
}
