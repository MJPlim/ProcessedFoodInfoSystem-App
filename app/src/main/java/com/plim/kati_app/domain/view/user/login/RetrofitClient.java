package com.plim.kati_app.domain.view.user.login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://13.124.55.59:8080/";

    Gson gson = new GsonBuilder().create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    ApiService apiService = retrofit.create(ApiService.class);


}


