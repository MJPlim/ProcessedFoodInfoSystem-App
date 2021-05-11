package com.plim.kati_app.domain.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadReviewDto {
    private List<ReadReviewResponse> readReviewResponse;
    private ReviewCount reviewCount;


    @Getter
    @Setter
    public class ReviewCount {
        private int findReviewPageCount;
        private int findReviewCount;

    }
}


