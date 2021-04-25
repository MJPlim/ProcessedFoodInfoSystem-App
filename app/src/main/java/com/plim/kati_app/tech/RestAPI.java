package com.plim.kati_app.tech;

import com.plim.kati_app.domain.model.FoodResponse;
import com.plim.kati_app.domain.model.Password;
import com.plim.kati_app.domain.model.SignUpResponse;
import com.plim.kati_app.domain.model.User;
import com.plim.kati_app.domain.model.WithdrawResponse;
import com.plim.kati_app.domain.view.user.login.LoginRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestAPI {

    @POST("signup")
    Call<SignUpResponse> signUp(@Body User user);

    @POST("withdraw")
    Call<WithdrawResponse> withdraw(@Body Password user);

    @POST("login")
    Call<LoginRequest> postRetrofitData(@Body LoginRequest loginRequest);

    @GET("/api/v1/food/findFood/foodName")
    Call<List<FoodResponse>> getFoodListByProductName( @Query("foodName") String foodName);

    @GET("/api/v1/food/findFood/manufacturerName")
    Call<List<FoodResponse>> getFoodListByCompanyName( @Query("manufacturerName") String bsshName);

}
