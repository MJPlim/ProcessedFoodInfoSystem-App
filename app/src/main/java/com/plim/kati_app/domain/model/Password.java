package com.plim.kati_app.domain.model;


import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Password {

    @SerializedName("password")
    private String password;
}
