package com.plim.kati_app.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse {

    @SerializedName("email")
    private String email;

    @SerializedName("createDate")
    private Timestamp createDate;
}
