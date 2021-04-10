package com.plim.kati_app.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 서버에서 받아온 음식 검색 결과 데이터 JSON과 똑같이 만든 클래스.
 */
@Getter
@Setter
public class FoodSearchListItem {
    private Long productLicenseNumber; // 인허가번호
    private String companyName; // 업소명
    private Long productReportNumber; // 품목제조번호
    private Long productConfirmDate; // 허가일자
    private String productName; // 품목명
    private String productCategoryName; // 유형
    private String rawMaterialName; // 원재료
}
