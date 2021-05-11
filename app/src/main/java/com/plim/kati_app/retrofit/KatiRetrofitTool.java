package com.plim.kati_app.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plim.kati_app.domain.constant.Constant;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KatiRetrofitTool {

    private static final String BASE_URL = Constant.URL;

    public static RestAPI getAPI(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build()
                .create(RestAPI.class);
    }

    public static RestAPI getAPIWithNullConverter(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build()
                .create(RestAPI.class);
    }

    public static RestAPI getAPIWithAuthorizationToken(String token){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", token)
                    .build();
            return chain.proceed(newRequest);
        }).build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create( new GsonBuilder().create()))
                .build()
                .create(RestAPI.class);
    }
}
