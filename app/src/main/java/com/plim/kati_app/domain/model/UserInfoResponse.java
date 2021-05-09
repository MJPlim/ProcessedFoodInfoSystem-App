package com.plim.kati_app.domain.model;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {
    @SerializedName("address")
    private String address;
    @SerializedName("birth")
    private String birth;
    @SerializedName("name")
    private String name;

}
