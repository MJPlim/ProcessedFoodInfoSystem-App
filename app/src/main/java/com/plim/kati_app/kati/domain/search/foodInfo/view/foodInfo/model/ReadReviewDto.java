package com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadReviewDto {
    private List<ReadReviewResponse> readReviewResponse;
    private ReadSummaryResponse readSummaryResponse;
}


