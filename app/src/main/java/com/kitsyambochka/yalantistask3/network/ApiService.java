package com.kitsyambochka.yalantistask3.network;

import com.kitsyambochka.yalantistask3.model.Ticket;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Developer on 27.05.2016.
 */
public interface ApiService {
    String BASE_URL = "http://dev-contact.yalantis.com/rest/v1/";

    @GET("tickets")
    Observable<List<Ticket>> getTickets(@Query("offset") int offset, @Query("state") String state, @Query("amount") int amount);

    class Factory {
        public static <T> T createRetrofitService(final Class<T> clazz) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            T service = retrofit.create(clazz);

            return service;
        }
    }
}
