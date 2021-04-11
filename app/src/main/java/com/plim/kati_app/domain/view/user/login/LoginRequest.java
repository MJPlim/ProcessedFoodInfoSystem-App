package com.plim.kati_app.domain.view.user.login;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class LoginRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
