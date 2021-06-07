package com.plim.kati_app.kati.domain.signOut.model;


import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawRequest {

    @SerializedName("password")
    private String password;
}
