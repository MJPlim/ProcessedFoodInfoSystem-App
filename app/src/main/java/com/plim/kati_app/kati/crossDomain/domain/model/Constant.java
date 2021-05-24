package com.plim.kati_app.kati.crossDomain.domain.model;

import com.plim.kati_app.R;

import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Constant { // ㅎㅎ Resource 로 옮겨조...

    public static final String JSONOBJECT_ERROR_MESSAGE = "error-message";
    public static final String URL = "http://13.124.55.59:8080/";

    public static final String KATI_DIALOG_CONFIRM = "확인";
    public static final String KATI_DIALOG_CANCEL = "취소";
    public static final String RETROFIT_FAIL_CONNECTION_TITLE = "연결오류";
    public static final String RETROFIT_FAIL_CONNECTION_MESSAGE = "인터넷 연결을 확인해주세요!";
    public static final String RETROFIT_NOT_SUCCESS_TITLE_PREFIX = "비성공적";

    public static final String KATI_DIALOG_YES = "예";
    public static final String KATI_DIALOG_NO = "아니오";

    public static final String BASIC_DATE_FORMAT = "YYYY-MM-dd";

    public static final String ABSTRACT_TABLE_FRAGMENT_LARGE = "펼치기>";
    public static final String ABSTRACT_TABLE_FRAGMENT_SMALL = "접기>";

    //mainActivity
    public static final String MAIN_ACTIVITY_FINISH_DIALOG_TITLE = "정말 종료하시겠습니까?";
    public static final String MAIN_ACTIVITY_FINISH_DIALOG_MESSAGE = "확인을 누르시면 어플리케이션을 종료합니다.";

    //autoLoginService
    public static final String AUTO_LOGIN_SERVICE_FAIL_DIALOG_TITLE = "자동 로그인 실패";
    public static final String AUTO_LOGIN_SERVICE_FAIL_DIALOG_MESSAGE = "자동 로그인에 실패하였습니다.";

    //logoutActivity
    public static final String LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_TITLE = "성공적으로 로그아웃하였습니다.";
    public static final String LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_MESSAGE = "성공적으로 로그아웃하였습니다.";
    public static final String LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE = "로그인 되어 있지 않습니다.";
    public static final String LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE = "로그인하고 다시 시도해 주세요";
    public static final String FIND_USER_PASSWORD_DIALOG_TITLE="임시 비밀번호가 발급되었습니다.";
    public static final String FIND_USER_PASSWORD_DIALOG_MESSAGE="메일함에서 임시 비밀번호 메일을 확인해 주세요.";

    //findPasswordActivity
    public static final String LOGINED_DIALOG_TITLE="이미 로그인 된 상태입니다.";
    public static final String NO_USER_DIALOG_TITLE="해당하는 유저가 없습니다.";
    public static final String NO_USER_DIALOG_MESSAGE="잘못 입력하였거나 해당하는 유저를 찾을 수 없습니다.";
    public static final String DIALOG_CONFIRM="확인";
    public static final String EMAIL_INPUT_MESSAGE="이메일을 입력해주세요";
    public static final String EMAIL_INPUT_HINT="example@plim.com";


    //changePasswordActivity
    public static final String CHANGE_PASSWORD_TITLE="비밀번호 변경";
    public static final String CHANGE_PASSWORD_DIFF_ERROR="새 비밀번호를 동일하게 입력해 주세요.";


    public static final String COMPLETE_CHANGE_PASSWORD_TITLE="비밀번호 변경이 완료되었습니다.";
    public static final String COMPLETE_CHANGE_PASSWORD_MESSAGE="변경된 비밀번호로 다시 로그인 해주세요.";
    public static final String BEFORE_PASSWORD_HINT="현재 비밀번호";
    public static final String AFTER_PASSWORD_HINT="새 비밀번호";
    public static final String AFTER_PASSWORD_HINT2="새 비밀번호 확인";


    //allergyViewFragment
    public static final int ALLERGY_VIEW_FRAGMENT_BUTTON_ITEM_HEIGHT = 80;
    public static final int ALLERGY_VIEW_FRAGMENT_BUTTON_ITEM_WIDTH = 150;
    public static final int ALLERGY_VIEW_FRAGMENT_BUTTON_PADDING = 30;


    //foodSearchFieldFragment
    public static final String FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY = "result";
    public static final String FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX = "index";
    public static final String FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE = "mode";
    public static final String FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT = "text";

    public static final String FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY="text";

    public enum EAllergyList{
        새우,게,우유,아몬드,잣,호두,땅콩,대두,밀,메밀,메추리알,난류,계란,소고기,닭고기,쇠고기,돼지고기,오징어,조개류,복숭아,토마토,아황산류;
    }



    public static final String ADD_FAVORITE_RESULT_DIALOG_TITLE="즐겨찾기 설정 하였습니다.";
    public static final String ADD_FAVORITE_RESULT_DIALOG_MESSAGE="즐겨찾기 목록에 해당 제품이 추가되었습니다.";


    public static final String DELETE_FAVORITE_RESULT_DIALOG_TITLE="즐겨찾기를 해제하였습니다.";
    public static final String DELETE_FAVORITE_RESULT_DIALOG_MESSAGE="즐겨찾기 목록에서 해당 제품이 삭제되었습니다.";

public static final String ALLERGY_EXPANDABLE_LIST_TITLE="알레르기";
    public static final String ALLERGY_MODIFY_SUCCESS_DIALOG_TITLE="알레르기 정보 저장 성공";
    public static final String ALLERGY_MODIFY_SUCCESS_DIALOG_MESSAGE="알레르기 정보를 성공적으로 저장하였습니다.";

    public static final String USER_MODIFY_SUCCESS_DIALOG_TITLE="사용자 정보 저장 성공";
    public static final String USER_MODIFY_SUCCESS_DIALOG_MESSAGE="사용자 정보를 성공적으로 저장하였습니다.";

    public static final String ALLERGY_LIST_NO_SUCH_ITEM_TITLE="해당하는 아이템 없음";
    public static final String ALLERGY_LIST_NO_SUCH_ITEM_MESSAGE_SUFFIX="(이)라는 아이템이 존재하지 않습니다.";

    //product detail Activity
    public static final String DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA="foodId";
    public static final String DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_BARCODE="foodId";
    public static final String DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_="https://msearch.shopping.naver.com/search/all?query=";

    public static final String ALLERGY_FILTER_INTENT_DIALOG_TITLE="알레르기 필터를 수정하시겠습니까?";
    public static final String ALLERGY_FILTER_INTENT_DIALOG_MESSAGE="수정하기 위해 수정 페이지로 이동합니다.";

    public static final String DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_KEY="detailImage";
    public static final String DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_FRONT_IMAGE="frontImage";
    public static final String DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_BACK_IMAGE="backImage";

    public static final String ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_NAME="name";
    public static final String ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_HASH_MAP="hashMap";

    public static final String DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_BUNDLE_KEY="detailProductInfo";


    public static final String SAVE_ALLERGY_LIST_FRAGMENT_BUNDLE_KEY="saveAllergyList";
    public static final String SAVE_ALLERGY_LIST_RESULT_FRAGMENT_BUNDLE_KEY="AllergyListSaved";

    public static final String DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_TABLE_NAME="제품정보";
    public static final String DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_PRODUCT_NAME="제품명";
    public static final String DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_MANUFACTURER_NAME="회사명";
    public static final String DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_EXPIRATION_DATE="유통기한";


    public static final String DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_BUNDLE_KEY="detailProductMaterial";
    public static final String DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_TABLE_NAME="성분 및 원재료";
    public static final String DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_MATERIAL_NAME="영양성분";
    public static final String DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_INGREDIENT_NAME="원재료";

    public static final String NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID="foodId";
    public static final String NEW_DETAIL_ACTIVITY_EXTRA_IS_AD="isAd";



    public static final String SEARCH_WORD_DELETE_ALL_DIALOG_TITLE="검색어 이력을 모두 지우시겠습니까?";
    public static final String SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE="저장된 최근 검색어들을 모두 삭제합니다.";
    public static final String SEARCH_WORD_DELETE_ONE_DIALOG_TITLE="해당 검색어 이력을 정말 지우시겠습니까?";
    public static final String SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD="검색어 '";
    public static final String SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL="'를 삭제합니다.";


    public enum ESortMode{
        오름차순,내림차순,세부
        ;
    }

    @AllArgsConstructor
    @Getter
    public enum ESortModeBig{
        rank("랭킹 순"),
        price("가격 순"),
        star("별점 순"),
        review("리뷰 개수")
        ;
        public String name;
    }

 //foodSearchResultListFragment

    @Getter
    @RequiredArgsConstructor
    public enum SortElement {

        RANK("ranking"),
        MANUFACTURER("manufacturer"),
        REVIEW_COUNT("reviewCount");

        private final String message;
    }

    public static final String FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE="연결 실패";
    @AllArgsConstructor
    @Getter
    public enum ECategory {
        snack("간식",ESnackCategory.values()),
        dairy("유제품", EDairyCategory.values()),
        drink("음료/차", EDrinkCategory.values()),
        condiment("조미료", ECondimentCategoty.values()),
        meat("육류", EMeatCategory.values()),
        farm("농수산물",EFarmCategory.values()),
        kimchi("김치",EKimchiCategory.values()),
        mealkit("즉석조리",EMealkitCategory.values()),
        etcmaterial("식재료",EMaterialCategory.values()),
        etc("기타",EEtcCategory.values()),
        ;
        private String name;
        private EChildCategory[] childCategories;

        public Vector<String> getChildNames() {
            Vector<String> childNames = new Vector<>();
            for (EChildCategory childCategory : childCategories)
                childNames.add(childCategory.getName());
            return childNames;
        }
    }

    public interface EChildCategory {
        String getName();
    }

    @AllArgsConstructor
    @Getter
    public enum EDairyCategory implements EChildCategory {
        dairy("유제품");
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public enum EDrinkCategory implements EChildCategory {
        drink("음료"), coffee("커피"), tea("커피/차");
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public enum ECondimentCategoty implements EChildCategory {
        sugar("설탕"), salt("소금"), sauce("소스"), jang("장류");
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public enum EMeatCategory implements EChildCategory {
        meat("육류"), ham("햄/소시지");
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public enum ESnackCategory implements EChildCategory {
        snack("과자"), tteok("떡"), bread("빵"),candy("사탕/껌/젤리"), icecream("아이스크림"),chocolate("초콜릿");
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public enum EFarmCategory implements EChildCategory {
        egg("계란"), fruit("과일/채소"), gim("김"),fish("수산물"),peanut("견과"),grain("곡류");
        private String name;
    }


    @AllArgsConstructor
    @Getter
    public enum EKimchiCategory implements EChildCategory {
        kimchi("김치"), jeokgal("젓갈");
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public enum EMealkitCategory implements EChildCategory {
        mealkit("즉석조리식품");
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public enum EMaterialCategory implements EChildCategory {
        noodle("국수"),
        tofu("두부"),
        oil("식용유"),
        fishcake("어묵"),
        ;
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public enum EEtcCategory implements EChildCategory {
        etc("기타가공품")
        ;
        private String name;
    }

    @Getter
    public enum ESearchMode {
        제품("foodName"), 회사("manufacturerName");
        private String mappingName;

        ESearchMode(String string) {
            this.mappingName = string;
        }
    }

    @AllArgsConstructor
    @Getter
    public enum EReviewCateGory {
        rank("인기순"), latest("최신순"), like("좋아요순"), five("5점"), four("4점"), three("3점"), two("2점"), one("1점");
        private String name;
    }


    //exception
    public static final String LOGIN_DATA_WRONG_EXCEPTION_MESSAGE = "로그인 정보가 틀렸습니다.";
    public static final int LOGIN_DATA_WRONG_EXCEPTION_CODE = 401;

    public static final String SERVER_DOWN_EXCEPTION_MESSAGE = "현재 서버 서비스를 사용할 수 없습니다.";
    public static final int SERVER_DOWN_EXCEPTION_CODE = 503;

    public static final String SERVER_FORBIDDEN_EXCEPTION_MESSAGE = "요청이 거부되었습니다.";
    public static final int SERVER_FORBIDDEN_EXCEPTION_CODE = 403;

    public static final String SERVER_WRONG_METHOD_EXCEPTION_MESSAGE = "요청 방법이 잘못되었습니다.";
    public static final int SERVER_WRONG_METHOD_EXCEPTION_CODE = 405;

    public enum ERankImage {
        first(R.drawable.first),
        second(R.drawable.second),
        third(R.drawable.third),
        fourth(R.drawable.four),
        fifth(R.drawable.five),
        sixth(R.drawable.six),
        seventh(R.drawable.seven),
        eighth(R.drawable.eight),
        ninth(R.drawable.nine),
        tenth(R.drawable.ten)
        ;

        private int image;

        private ERankImage(int image) {
            this.image = image;

        }
        public int getImage() {
            return this.image;
        }
    }



}
