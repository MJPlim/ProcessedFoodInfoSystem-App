package com.plim.kati_app.domain2.katiCrossDomain.domain.model.forBackend.userAccount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswordRequest {

    private	String beforePassword;
    private	String afterPassword;


}
