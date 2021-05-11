package com.plim.kati_app.domain.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserInfoModifyRequest {
    private String name; //이름
    private String birth; //생일
    private String address; //주소
}
