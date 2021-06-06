package com.plim.kati_app.kati.domain.editPassword.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswordRequest {
    private	String afterPassword;
    private	String beforePassword;
}
