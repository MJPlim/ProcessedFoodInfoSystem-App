package com.plim.kati_app.domain.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawResponse {

    @SerializedName("email")
    private String email;

    @SerializedName("state")
    private String state;
}
