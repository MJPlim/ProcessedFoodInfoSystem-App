package com.plim.kati_app.domain.user.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
//    @FormUrlEncoded
    @POST("login")
//    Call<LoginRequest> postRetrofitData(@Field("email") String email, @Field("password") String password);
    Call<LoginRequest> postRetrofitData(@Body LoginRequest loginRequest);
}
