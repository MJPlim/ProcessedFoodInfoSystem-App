package com.plim.kati_app.domain.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserInfoModifyRequest {
    private String name;
    private LocalDate birth;
    private String address;
}
