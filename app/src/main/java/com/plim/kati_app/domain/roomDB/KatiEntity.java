package com.plim.kati_app.domain.roomDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KatiEntity {

    // Component
    private Map<String, String> dataset;  // email, pw, authorization... 등 엥간한 것들을 여기 저장
    private ArrayList<String> searchWords; // 검색 기록 저장

    // Constructor
    public KatiEntity() {
        this.dataset = new HashMap<>();
        this.searchWords = new ArrayList<>();
    }
}
