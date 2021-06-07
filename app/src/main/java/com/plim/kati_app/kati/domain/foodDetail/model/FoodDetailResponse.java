package com.plim.kati_app.kati.domain.foodDetail.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class FoodDetailResponse {

    private Long foodId; //아이디
    private String foodName; //제품명
    private String category; //카테고리
    private String manufacturerName; //회사명
    private String foodImageAddress;  //이미지주소1
    private String foodMeteImageAddress; //이미지주소2
    private String materials; //원재료
    private String nutrient; //영양성분
    private String allergyMaterials; //알레르기 유발물질
    private Long viewCount; //조회수
    private Integer reviewCount; // 리뷰수
    private String reviewRate; // 별점

//    private final List<Review> reviewList;
//    private final List<Favorite> favoriteList;
}
