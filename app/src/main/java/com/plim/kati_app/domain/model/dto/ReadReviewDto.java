package com.plim.kati_app.domain.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadReviewDto {
    private List<ReadReviewResponse> readReviewResponse;
    private ReadSummaryResponse readSummaryResponse;

    @Getter
    public class ReadSummaryResponse{
        private Long foodId;
        private int oneCount;
        private int twoCount;
        private int threeCount;
        private int fourCount;
        private int fiveCount;
        private long sumRating;
        private float avgRating;
        private int reviewCount;
        private int reviewPageCount;
    }
}


