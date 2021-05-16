package com.plim.kati_app.kati.domain.temp.signOut.model;

import com.google.gson.annotations.SerializedName;

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
