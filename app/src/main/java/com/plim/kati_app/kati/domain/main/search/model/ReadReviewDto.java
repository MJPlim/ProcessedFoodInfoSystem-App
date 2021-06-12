package com.plim.kati_app.kati.domain.main.search.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadReviewDto {
    private List<ReadReviewResponse> readReviewResponse;
    private ReadSummaryResponse readSummaryResponse;
}


