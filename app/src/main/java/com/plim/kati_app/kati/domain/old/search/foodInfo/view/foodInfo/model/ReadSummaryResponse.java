package com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model;

import lombok.Getter;

@Getter
public class ReadSummaryResponse{
    private Long foodId;
    private int oneCount;
    private int twoCount;
    private int threeCount;
    private int fourCount;
    private int fiveCount;
    private long sumRating;
    private float avgRating=0f;
    private int reviewCount;
    private int reviewPageCount;
}