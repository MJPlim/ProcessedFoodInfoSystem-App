package com.plim.kati_app.retrofit.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindPasswordResponse {

    private final String email;

}
