package com.plim.kati_app.domain2.katiCrossDomain.domain.model.forFrontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KatiEntity {

    // Static Value
    public enum EKatiData {AUTHORIZATION, AUTO_LOGIN, EMAIL, PASSWORD, NAME, TRUE, FALSE}

    // Component
    private Map<EKatiData, String> dataset;  // email, pw, authorization... 등 엥간한 것들을 여기 저장
    private ArrayList<String> searchWords; // 검색 기록 저장

    // Constructor
    public KatiEntity() {
        this.dataset = new HashMap<>();
        this.dataset.put(EKatiData.AUTO_LOGIN, EKatiData.FALSE.name());
        this.searchWords = new ArrayList<>();
    }
}
