package com.plim.kati_app.domain2.model.forBackend.userAccount;


import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawRequest {

    @SerializedName("password")
    private String password;
}
