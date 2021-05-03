package com.plim.kati_app.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswordRequest {

    private	String beforePassword;
    private	String afterPassword;


}
