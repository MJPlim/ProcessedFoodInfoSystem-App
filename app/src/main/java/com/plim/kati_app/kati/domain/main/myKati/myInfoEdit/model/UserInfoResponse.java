package com.plim.kati_app.kati.domain.main.myKati.myInfoEdit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {
    private String address;
    private String birth;
    private String name;

    public UserInfoResponse(){
        this.address="";
        this.birth="";
        this.name="";
    }

}
