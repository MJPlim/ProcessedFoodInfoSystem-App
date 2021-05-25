package com.plim.kati_app.kati.domain.user.dataChange.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserInfoModifyRequest {
    private String name; //이름
    private String birth; //생일
    private String address; //주소
}
