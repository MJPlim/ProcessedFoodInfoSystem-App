package com.plim.kati_app.kati.domain.mypage.myReview.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadReviewResponse {
    private Long reviewId;
    private String userName;
    private Long foodId;
    private float reviewRating;
    private String reviewDescription;
    private Timestamp reviewCreatedDate;
    private Timestamp reviewModifiedDate;
    private ReviewStateType state;
    private boolean userCheck;
    private boolean userLikeCheck;
    private Integer likeCount;
    private String foodName;

    public enum ReviewStateType {
        NORMAL, DELETED
    }
}
