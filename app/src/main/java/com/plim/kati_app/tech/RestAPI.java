package com.plim.kati_app.tech;

import com.plim.kati_app.domain.model.Password;
import com.plim.kati_app.domain.model.SignUpResponse;
import com.plim.kati_app.domain.model.User;
import com.plim.kati_app.domain.model.WithdrawResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestAPI {

    @POST("signup")
    Call<SignUpResponse> signUp(@Body User user);

    @POST("withdraw")
    Call<WithdrawResponse> withdraw(@Body Password user);
}
