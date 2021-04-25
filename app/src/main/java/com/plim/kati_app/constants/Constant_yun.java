package com.plim.kati_app.constants;

import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Constant_yun {

    public static final String KATI_DIALOG_YES = "확인";
    public static final String KATI_DIALOG_NO = "취소";

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
    public static final String LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE = "로그인 되어 있지 않습니다.";


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

    public static final String FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE="연결 실패";
    @AllArgsConstructor
    @Getter
    public enum ECategory {
        tea("차", ETeaCategory.values()), drink("음료", EDrinkCategory.values()), simple("간편식", ESimpleCategory.values());
        private String name;
        private EChildCategory[] childCategories;

        public Vector<String> getChildNames() {
            Vector<String> childNames = new Vector<>();
            for (EChildCategory childCategory : childCategories)
                childNames.add(childCategory.name());
            return childNames;
        }
    }
    public interface EChildCategory {
        String name();
    }

    public enum ETeaCategory implements EChildCategory {
        커피, 핫초코, 아이스티, 녹차, 홍차, 보이차, 꽃차;

    }

    public enum EDrinkCategory implements EChildCategory {
        생수, 콜라, 사이다, 기타_탄산음료, 보리차, 두유, 과채주스, 전통주, 어린이음료;
    }

    public enum ESimpleCategory implements EChildCategory {
        라면, 즉석밥, 즉석국, 통조림, 카레, 짜장, 밀키트;
    }


    @Getter
    public enum ESearchMode {
        제품("foodName"), 회사("manufacturerName");
        private String mappingName;

        ESearchMode(String string) {
            this.mappingName = string;
        }
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

}
