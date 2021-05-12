package com.plim.kati_app.domain2.katiCrossDomain.domain.model.forBackend.userAccount;

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
