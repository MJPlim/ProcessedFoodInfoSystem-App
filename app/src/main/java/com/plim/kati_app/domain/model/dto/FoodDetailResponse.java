package com.plim.kati_app.domain.model.dto;

import com.plim.kati_app.domain.model.dto.entity.Favorite;
import com.plim.kati_app.domain.model.dto.entity.Review;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FoodDetailResponse {

    //유통기한, 유형, 판매처

    private final Long foodId; //아이디
    private final String foodName; //제품명
    private final String category; //카테고리
    private final String manufacturerName; //회사명
    private final String foodImageAddress;  //이미지주소1
    private final String foodMeteImageAddress; //이미지주소2
    private final String materials; //원재료
    private final String nutrient; //영양성분
    private final String allergyMaterials; //알레르기 유발물질
    private final Long viewCount; //조회수
    private final List<Review> reviewList;
    private final List<Favorite> favoriteList;
}
