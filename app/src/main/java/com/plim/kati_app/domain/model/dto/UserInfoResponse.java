package com.plim.kati_app.domain.model.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoResponse {

    private String name;

    private String birth;

    private String address;

}
