package com.plim.kati_app.retrofit.dto;


import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Password {

    @SerializedName("password")
    private String password;
}
