package com.plim.kati_app.kati.domain.nnew.signUp.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse {

    @SerializedName("email")
    private String email;

    @SerializedName("message")
    private String message;

//    @SerializedName("createDate")
//    private Timestamp createDate;
}
