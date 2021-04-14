package com.plim.kati_app.domain.view.user.login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plim.kati_app.Constant;
import com.plim.kati_app.tech.RestAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = Constant.URL;

    Gson gson = new GsonBuilder().create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

   public RestAPI apiService = retrofit.create(RestAPI.class);


}


