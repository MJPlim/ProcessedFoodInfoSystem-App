package com.plim.kati_app.kati.domain.temp.editData.userData.model;

import com.google.gson.annotations.SerializedName;

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
