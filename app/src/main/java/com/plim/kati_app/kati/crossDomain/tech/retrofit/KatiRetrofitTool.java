package com.plim.kati_app.kati.crossDomain.tech.retrofit;

import com.google.gson.GsonBuilder;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHNullOnEmptyConverterFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KatiRetrofitTool {

    private static final String BASE_URL = Constant.URL;

    public static KatiRestAPI getAPI(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build()
                .create(KatiRestAPI.class);
    }

    public static KatiRestAPI getAPIWithNullConverter(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(new JSHNullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build()
                .create(KatiRestAPI.class);
    }

    public static KatiRestAPI getAPIWithAuthorizationToken(String token){
        Interceptor interceptor = chain -> {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", token)
                    .build();
            return chain.proceed(newRequest);
        };
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create( new GsonBuilder().create()))
                .build()
                .create(KatiRestAPI.class);
    }
}
