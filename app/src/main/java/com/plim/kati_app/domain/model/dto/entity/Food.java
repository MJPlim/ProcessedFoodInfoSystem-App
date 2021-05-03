package com.plim.kati_app.domain.model.dto.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

//@Table(name = "food")
@Setter
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
public class Food {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "food_id")
    private Long id;

//    @Column(name = "food_name", nullable = false)
    private String foodName;

//    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true) // 식품 데이터가 지워질일은 거의 없겠지만 지워진다면 리뷰도 삭제
    private List<Review> reviewList = new ArrayList<>();

//    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favoriteList = new ArrayList<>();

//    @Column(name = "report_no")
    private String reportNumber;

//    @Column(name = "category")
    private String category; // 분류해서 이넘값으로 받아올수도?

//    @Column(name = "manufacturer_name")
    private String manufacturerName;

//    @Embedded
    private FoodDetail foodDetail; // 너무많아서 세부사항은 임베디드타입으로 설정

//    @Embedded
    private FoodImage foodImage;

//    @Column(name = "allergy_materials")
    private String allergyMaterials;

//    @Column(name = "barcode_no")
    private String barcodeNumber;

//    @Column(name = "view_count")
//    @ColumnDefault("0")
    private Long viewCount;

//    @Builder
//    public Food(String foodName, String reportNumber, String category, String manufacturerName,
//                String allergyMaterials, FoodDetail foodDetail, FoodImage foodImage, String barcodeNumber) {
//        this.foodName = foodName;
//        this.reportNumber = reportNumber;
//        this.category = category;
//        this.manufacturerName = manufacturerName;
//        this.foodDetail = foodDetail;
//        this.allergyMaterials = allergyMaterials;
//        this.foodImage = foodImage;
//        this.barcodeNumber = barcodeNumber;
//    }

//    @PrePersist						//default값 설정을 위함
//    public void prePersistFoodView() {
//        this.viewCount = this.viewCount == null ? 0: this.viewCount;
//    }

}
