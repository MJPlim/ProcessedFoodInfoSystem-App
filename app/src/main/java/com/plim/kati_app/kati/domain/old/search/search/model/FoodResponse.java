package com.plim.kati_app.kati.domain.old.search.search.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 서버에서 받아온 음식 검색 결과 데이터 JSON과 똑같이 만든 클래스.
 */
@Getter
@Setter
public class FoodResponse {
    private Long foodId;
    private String foodName;
    private String category;
    private String manufacturerName;
    private String foodImageAddress;
    private String foodMeteImageAddress;
    private int reviewCount;
    private String reviewRate;
}
