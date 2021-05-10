package com.plim.kati_app.retrofit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswordRequest {

    private	String beforePassword;
    private	String afterPassword;


}
