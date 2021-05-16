package com.plim.kati_app.domain.katiCrossDomain.domain.model.forBackend.userAccount;

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
