package com.plim.kati_app.tech;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPIClient {
    private static final String BASE_URL = "http://13.124.55.59:8080/";

    public static RestAPI getApiService(){return RestAPIClient.getInstance().create(RestAPI.class);}

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().create();
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }
}
