package com.sourav.rxnet.webapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit getClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rsnetbd.ispms.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


    public static Retrofit getClientIp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.myip.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
