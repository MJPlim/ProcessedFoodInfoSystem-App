package com.plim.kati_app.kati.crossDomain.domain.model;

import com.plim.kati_app.R;

import java.util.Arrays;
import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Constant { // ㅎㅎ Resource 로 옮겨조...

    //회원가입
    public static final String AUTHORIZATION ="Authorization";

    public static final String GOOGLE_MAP_KEY="AIzaSyBDRm1MCwKjUtvIWCFQsoXjd8dNHWjRIVg";

    public static final String SIGN_UP_TOOLBAR_TITLE_CONTENT= "회원가입";
    public static final String SIGN_UP_CONGRAT_TITLE_PREFIX= "어서 오세요 ";
    public static final String SIGN_UP_CONGRAT_TITLE_SUFFIX= " 님!";
    public static final String SIGN_UP_CONGRAT_MESSAGE= "회원 가입에 성공하였습니다. 메일함에서 인증을 진행해 주세요!";

    public static final String FAVORITE_ITEM_SIZE_PREFIX="총 ";
    public static final String FAVORITE_ITEM_SIZE_SUFFIX="개";
    public static final String REVIEW_ITEN_COLOR_STRING="#E53154";

    public static final String FOOD_DETAIL_ACTIVITY_TAB_PRODUCT="제품";
    public static final String FOOD_DETAIL_ACTIVITY_TAB_REVIEW="리뷰";
    public static final String FOOD_DETAIL_ACTIVITY_TAB_ETC="기타";

    //비번 찾기
    public static final String FIND_PASSWORD_DIALOG_TITLE= "임시 비밀번호를 발급하였습니다.";
    public static final String FIND_PASSWORD_DIALOG_MESSAGE= "메일함을 확인하여 임시 비밀번호로 로그인 해 주세요";


    //정보 수정
    public static final String EDIT_SINGLE_DATA_TITLE_SUFFIX=" 변경";
    public static final String EDIT_SINGLE_DATA_BUTTON_TEXT_SUFFIX=" 입력";
    public static final String EDIT_SINGLE_DATA_EXTRA_NAME="name";
    public static final String EDIT_SINGLE_DATA_EXTRA_ADDRESS="address";
    public static final String EDIT_SINGLE_DATA_EXTRA_BIRTH="birth";
    public static final String EDIT_PASSWORD_ACTIVITY_LOG_OUT_DIALOG_TITLE = "비밀번호를 바꿀까요?";
    public static final String EDIT_PASSWORD_ACTIVITY_LOG_OUT_DIALOG_MESSAGE = "비밀번호를 바꾸고 나서 로그아웃합니다.";



    @Getter
    @AllArgsConstructor
    public enum EEditSingleMode {
        name("닉네임"), address("주소"), birth("생일");
        private String text;
    }

    public static final String JSONOBJECT_ERROR_MESSAGE = "error-message";
    public static final String JSONOBJECT_MESSAGE = "message";
    public static final String JSONOBJECT_PASSWORD_MESSAGE = "password";
    public static final String JSONOBJECT_NAME_MESSAGE = "name";
    public static final String JSONOBJECT_EMAIL_MESSAGE = "email";
    public static final String JSONOBJECT_SECOND_EMAIL_MESSAGE = "secondEmail";
    public static final String JSONOBJECT_AFTER_PASSWORD_MESSAGE = "afterPassword";
    public static final String JSONOBJECT_BEFORE_PASSWORD_MESSAGE = "beforePassword";
    public static final String URL = "http://13.124.55.59:8080/";

    public static final String KATI_DIALOG_CONFIRM = "확인";
    public static final String KATI_DIALOG_CANCEL = "취소";
    public static final String RETROFIT_FAIL_CONNECTION_TITLE = "연결오류";
    public static final String RETROFIT_FAIL_CONNECTION_MESSAGE = "인터넷 연결을 확인해주세요!";
    public static final String RETROFIT_FAIL_LOGIN_ERROR_MESSAGE = "로그인을 해주세요.";
    public static final String RETROFIT_FAIL_LOGIN_TIME_TITLE = "로그인 만료";
    public static final String RETROFIT_FAIL_LOGIN_TIME_MESSAGE = "로그인 후 시간이 지나 만료되었습니다. 다시 로그인 하시겠습니까?";
    public static final String RETROFIT_NOT_SUCCESS_TITLE_PREFIX = "비성공적";

    public static final String KATI_DIALOG_YES = "예";
    public static final String KATI_DIALOG_NO = "아니오";

    public static final String BASIC_DATE_FORMAT = "YYYY-MM-dd";
    public static final String NO_BIRTH_DATA = "아직 설정되지 않았습니다.";
    public static final String NO_ADDRESS_DATA = "아직 설정되지 않았습니다.";
    public static final String NO_RESTORE_DATA = "아직 설정되지 않았습니다.";
//    public static final String NO = "아직 생일이 설정되지 않았습니다.";

    public static final String ABSTRACT_TABLE_FRAGMENT_LARGE = "펼치기>";
    public static final String ABSTRACT_TABLE_FRAGMENT_SMALL = "접기>";

    //mainActivity
    public static final String MAIN_ACTIVITY_FINISH_DIALOG_TITLE = "정말 종료하시겠습니까?";
    public static final String MAIN_ACTIVITY_FINISH_DIALOG_MESSAGE = "확인을 누르시면 어플리케이션을 종료합니다.";

    //autoLoginService
    public static final String AUTO_LOGIN_SERVICE_FAIL_DIALOG_TITLE = "자동 로그인 실패";
    public static final String AUTO_LOGIN_SERVICE_FAIL_DIALOG_MESSAGE = "자동 로그인에 실패하였습니다.";

    public static final String START_REVIEW="(";
    public static final String END_REVIEW=")";

    //logoutActivity
    public static final String LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_TITLE = "로그아웃 하시겠습니까?";
    public static final String LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_MESSAGE = "로그아웃 하고 홈으로 이동합니다.";
    public static final String LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE = "로그인 되어 있지 않습니다.";
    public static final String LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE = "로그인하러 이동 하시겠습니까?";
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
    public static final String COMPLETE_CHANGE_PASSWORD_MESSAGE="비밀번호 변경이 완료되었습니다.";
    public static final String BEFORE_PASSWORD_HINT="현재 비밀번호";
    public static final String AFTER_PASSWORD_HINT="새 비밀번호";
    public static final String AFTER_PASSWORD_HINT2="새 비밀번호 확인";


    //allergyViewFragment
    public static final int ALLERGY_VIEW_FRAGMENT_BUTTON_ITEM_HEIGHT = 80;
    public static final int ALLERGY_VIEW_FRAGMENT_BUTTON_ITEM_WIDTH = 150;
    public static final int ALLERGY_VIEW_FRAGMENT_BUTTON_PADDING = 30;


    //foodSearchFieldFragment
    public static final String HOME_FRAGMENT_BUNDLE_KEY = "category";
    public static final String FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY = "result";
    public static final String FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX = "index";
    public static final String FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE = "mode";
    public static final String FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT = "text";

    public static final String FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY="text";

    public enum EAllergyList{
        우유,아몬드,잣,호두,땅콩,대두,밀,메밀,메추리알,난류,계란,소고기,닭고기,쇠고기,돼지고기,오징어,조개류,새우,게,복숭아,토마토,아황산류;
    }



    public static final String ADD_FAVORITE_RESULT_DIALOG_TITLE="즐겨찾기 설정 하였습니다.";
    public static final String ADD_FAVORITE_RESULT_DIALOG_MESSAGE="즐겨찾기 목록에 해당 제품이 추가되었습니다.";


    public static final String DELETE_FAVORITE_RESULT_DIALOG_TITLE="즐겨찾기를 해제하였습니다.";
    public static final String DELETE_FAVORITE_RESULT_DIALOG_MESSAGE="즐겨찾기 목록에서 해당 제품이 삭제되었습니다.";

    public static final String DELETE_REVIEW_RESULT_DIALOG_MESSAGE="해당 리뷰가 삭제되었습니다.";

public static final String ALLERGY_EXPANDABLE_LIST_TITLE="알레르기";
    public static final String ALLERGY_MODIFY_SUCCESS_DIALOG_TITLE="알레르기 정보 저장 성공";
    public static final String ALLERGY_MODIFY_SUCCESS_DIALOG_MESSAGE="알레르기 정보를 성공적으로 저장하였습니다.";

    public static final String USER_MODIFY_SUCCESS_DIALOG_TITLE="사용자 정보 저장 성공";
    public static final String USER_MODIFY_SUCCESS_DIALOG_MESSAGE="사용자 정보를 성공적으로 저장하였습니다.";

    public static final String ALLERGY_LIST_NO_SUCH_ITEM_TITLE="해당하는 아이템 없음";
    public static final String ALLERGY_LIST_NO_SUCH_ITEM_MESSAGE_SUFFIX="(이)라는 아이템이 존재하지 않습니다.";

    //product detail Activity
    public static final String DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA="foodId";
    public static final String DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_BARCODE="barcode";
    public static final String DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_="https://msearch.shopping.naver.com/search/all?query=";
    public static final String DETAIL_PRODUCT_INFO_FRAGMENT_ISSUE_LINK="https://m.search.naver.com/search.naver?where=m_news&sm=mtb_jum&query=";
    public static final String DETAIL_PRODUCT_INFO_START_NULL="null";
    public static final String DETAIL_PRODUCT_INFO_START_NONE="알수없음";
    public static final String DETAIL_PRODUCT_INFO_START_NO="No";

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


    public static final String REVIEW_ACTIVITY_EXTRA_REVIEW_ID="reviewId";
    public static final String REVIEW_ACTIVITY_EXTRA_FOOD_ID="foodId";
    public static final String REVIEW_ACTIVITY_EXTRA_FOOD_NAME="foodName";
    public static final String REVIEW_ACTIVITY_EXTRA_UPDATE="isUpdate";
    public static final String REVIEW_ACTIVITY_EXTRA_RATING="ratingScore";
    public static final String REVIEW_ACTIVITY_EXTRA_REVIEW_TEXT="reviewText";

    public static final String REVIEW_ACTIVITY_TOO_LONG_MESSAGE="글자 수 제한 500을 초과하였습니다.";


    public static final String SEARCH_WORD_DELETE_ALL_DIALOG_TITLE="검색어 이력을 모두 지우시겠습니까?";
    public static final String SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE="저장된 최근 검색어들을 모두 삭제합니다.";
    public static final String SEARCH_WORD_DELETE_ONE_DIALOG_TITLE="해당 검색어 이력을 정말 지우시겠습니까?";
    public static final String SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD="검색어 '";
    public static final String SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL="'를 삭제합니다.";


    public static final String SIGN_OUT_SUCCESS_DIALOG_TITLE="회원 탈퇴 성공";
    public static final String SIGN_OUT_SUCCESS_DIALOG_MESSAGE="성공적으로 회원 탈퇴하였습니다.";

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

    @Getter
    @RequiredArgsConstructor
    public enum SortOrder {

        asc("asc"),
        desc("desc");

        private final String message;
    }

    public enum ECompany{
        남원혼불부각,참살이푸드원,쏙쏙이식품,더원푸드,
        예그린,올마루,평창후레쉬푸드,리투,캔디원,
        영주한우,대상,바다채움,청해에스앤에프,청림,
        그린로드,그린팜푸드,축복의열매,단미푸드,비앤비코리아,
        탭스푸드서비스,아이스팩토리,농산에프디,푸드앤푸드,
        더브레드블루,에스디푸드,해운관광농원,해담온,새한푸드,
        알수없음,만포농산,솔트앤그린푸드,번영영어조합법인,
        조이시푸드,조이씨푸드,와이푸드테크,아리푸드,휘둘찬푸드,
        완주로컬푸드협동조합,공덕농협농산물가공사업소,KKMC,동이식품,
        참바다영어조합법인,광천수산,영수식품,한보식품,err,
        해나루한돈영농조합법인,한국델몬트후레쉬프로듀스,내일식품,
        전원푸드락,참좋은콩,풀잎새,우리밀,데어리젠,해태가루비,백합음료,
        정스팜,파프리카푸드,오트리푸드빌리지,프로엠,부일,제이앤비식품,
        가원,우듬지,케이씨에프앤비,항진축산,엔피베버리지,성신식품,
        코리아푸드,동일종합식품,한림수협,가우정푸드,씨엔에프,에스엘푸드,
        에스제이에프,대륙식품,한길웰바이오,경산복숭아영농조합법인,원앙에프엔비,
        동산식품,나비원,케이푸드,한영식품,푸드원,유레카영농조합법인,청밀원,
        다도유영농조합법인,태송,하늘선남포영농조합법인,여유온,풍년제과,이앤지푸드,
        오곡백과,농협,우양냉동식품,한생,한미식품,사조오양,신세계푸드,엘가,동원홈푸드,
        동학식품,애간장,한울타리식품,이든또르띠아,날파람,달성농산,태연,행복담기,
        자연애벗,해마로푸드서비스,드림바이오,한국인삼공사,사군자식품,청오건강농업사회법인,
        아인츠푸드,농업회사법인한살림안성마춤식품유한회사,네떼,엠디에스코리아,미르마로푸드시스템,
        이델리에프씨,대조에프앤비,찬솔푸드,제이웰푸드,하성에프앤비,차오름푸드,푸드장,토토미트원,
        한가네웰푸드,오즈푸드,대원푸드,진원에프엠,마음푸드,삼선식품,승인식품,성덕에프에스,
        태성축산유통,롯데,풀무원,농업회사법인돈박,진원무역,동산엘엔비,평강푸드,상하식품,
        케이솔트,현대푸드,자연식품,짱죽,쿠키아,라벨리,해든마당,데이월,데이웰,성신비에스티,
        씨제이제일제당,성신비에스,천년미소전통식품,꽃과목장,광천우리맛김,광천다솔김,소이미푸드,
        엘빈즈에프디,산들본가,새동네,간월도어리굴젓사업단,간월도어리굴젓,송원식품,굴다리영어조합법인,
        더팩,국일에프엔비,장스푸드,미가온농업회사법인,아워홈,대하식품,농업회사법인오르피아,
        오드레미영농조합법인,세영푸드,씨밀레,산과들에프앤씨,
        하담푸드,하담푸두,미찌푸드,케이프라이드,횡성축협육가공장,거성푸드,정진에스피씨,효성코퍼레이션,
        효성푸드,웅비식품,델토리,에스피팩토리,바스락,그린에코팜,삼진씨앤에프,티엔푸드,진성그린식품,
        지엔에프앤씨,텃골영농조합법인,제이에프엔비,숲풀림식품,성일식품,소들녁에프앤디,소들녘,
        프레쥬퀸,하늘푸드,채담,스토리푸드,한울푸드,견우푸드,하나햄,신우식품주식회사,동서식품,
        신선식품,신광에코팜,황보마을,도야지식품,달구지푸드,세화씨푸드,삼진식품,유티비커피,올인원랩,
        길호수산,에버그린,제이크리에이션,청현,홍스랜드,윤진푸드,다참,예천보호작업장,비엘에프씨,
        대호영광굴비,해남고구마식품주식회사,다솔,거문도해풍영농조합법인,거문도해풍쑥영농조합법인,
        티아이지푸드,다함,용정목장,참프레,시아스,임실산양유,김노리,백제,명품김,보령수산맛김,제이팜스,
        선영푸드,계림농장,마루카네코리아,진성푸드,떡안애,선명농수산,금성,씨제이씨푸드,그린에프엠,성보,
        하늘뜰식품,서흥,에쓰푸드,에버프레쉬,봄내푸드,월송농산,녹차원,햇살푸드시스템,올레,드림엘푸드,
        드림엘,고래미,씨포스트,고은푸드,푸드월드네트워크,수내츄럴,슈퍼내츄럴,일광에프앤에스,이음푸드시스템,
        우래식품,우리식품,맑은농산,맑은,인정식품주식회사,다사다,보승식품,오뚜기냉동식품주식회사,미드림,디오푸드,
        토자연,제주유기농,자연누리,다우린,농도원목장,한국축산,밀토리,광주식품,덕성푸드,혜성,하림,선봉식품,
        아우노,달구벌명가식품,쓰리비네트웍스,비앤피월드,팜스토리한냉,서울장수주식회사,충북소주,제주막걸리,
        월야주조장,금복주,인천탁주합동,울산탁주공동제조장,부산합동양조장림제조장,부산합동양조,무학,
        배상면주가,서울탁주,제주홍암가,아림농산영농조합법인,합천식품,신광식품,휴먼앤푸드,원미푸드,
        대진수산,삼민목장,아리바이오,예천어무이,우리농산,한울영농조합,하회물산,임꺽정푸드시스템,
        임꺽정푸드시스템새뜸원,새뜸원,정심푸드,팔도,한국야쿠르트,남양,여수시돌산갓영농조합법인,
        생물산업진흥재단,농업회사법인한국절임,농업회사법인한국절임봉동댁,봉동댁,은성푸드,아리울수산,
        아리울수산임실샘고을영농조합,임실샘고을영농조합,임실샘고을영농조합밸리애,밸리애,우리에프엔비,
        우리에프앤비,보령천연식품,다농식품,으뜸엘엔에스,꿈꾸는콩,한미에프앤에프,이든푸드영농조합법인,
        시즈너,단골식품,엠플러스에프엔씨,서울우유협동조합,금돈,세진식품,맛찬들백미식품,
        류시네,류씨네,올가니카데이,오,오뗄,서해글로벌,에코호유,이레팜,다모아식품,유원식품,태광웰푸드,
        바른들식품,일이축산푸드,청화식품,맛뜰안식품,제주탐나씨푸드,올래씨푸드,우포의,서초농산,늘그린,
        e방앗간,방깐에프앤비,장흥무산김,죽암에프엔씨,명성제분,태산,한원푸드시스템,바다의향기,진미순창식품,
        코엔에프,춘향식품,신우,바다향,흥양산업,효송그린푸드,무진장흑돼지푸드,무진장,자연의,
        무지개영농조합법인,청화농산,대선제분,대천청정맛김,옐로우팜농업회사법인,사조동아원,신미씨앤에프,
        노랑오리농업회사법인,토담,이든밥상,옐로우팜계룡공장,은진식품,사조화인코리아,흥국에프엔비,서신식품,
        진미식품,진미식품경천식품,경천식품,석보유통,올계농업회사법인,푸른촌,풀잎라인,금부식품,태진푸드,
        하늘재보호작업장,농업법인린,순수,일미식품,산과들에,대향유통,다들림푸드,라움메이드주식회사,신화종합식품,
        별가식품,제이푸드서비스,연꽃마을영농조합,참조아,성림쓰리에이통상,한맥식품,고양미트,김포축산,풍전나이스제과,
        리치코리아,솔밭골,푸드업,테일러팜스,세윤식품,비에스푸드,대진식품,제키스,다인제주,성마리오농장,정우식품,
        천호엔케어,천호식품,대보식품,국심,미보부산어묵,선산식품,오케이에프,해태에이치티비,OKF,콩고물영농조합법인,
        나주수산,보림목장, 부안수협수산물가공공장,사조산업,에스아이지,박씨네누룽지,
        미들채푸드,미들채,팜덕,비엔푸드,비앤푸드,대한목장,천일식품,관천원김,광천원김,가연식품,사조대림,서산명가,
        제이앤이,제이엔이,크로바유업,태공식품,장원식품,이킴,신화엠에스,샘표식품주식회사,산골농장,
        씨월드,세린식품,춘천그린식품,진양,청우식품,대산후드,금상푸드,성진식품,누들트리,한스푸드테크,야미코리아,
        아로마빌커피,현대에프앤비,세움,예맛상사,미래원,퀸즈푸드,수라당,파리그라상,태경농산,지엔티,더고기,진푸드시스템,
        애심뜰,설정숙김치본가,바오로일터,다이식품,이유푸드,선진FS,이가,참솔식품,바비조아,지산푸드시스템,소미노,
        우송푸드,신세계조선호텔김치,행운식품,청산바다,코코허브,체리부로수옥지점,청암식품,봄푸드,
        징코푸드시스템,만선영어조합법인,푸드월드,가락식품,꿈엔들잊힐리야,화산,다산푸드시스템,세진산업,
        매일유업,농업회사법인데미샘목장,플러스원,해저식품,광천김,서천한식식품,서천한산식품,서천한식품,
        어머니김,연우당,한국매널티,한국맥널티,충남대,정성식품,에스앤푸드,건영제과,명진유통,
        힐링바이오,동화,킹스코,신영에이치에스,장충동왕족발,교동식품,철원,자미원,아리아리떡사랑,
        봉평메밀특산단지영농조합법인,안흥식품,강동퓨어푸드,명성식품,하마씨앤티,제이디코리아,나라통상,
        두라푸드,선홍수산식품,상원수산,브레드가든,고메베이글,하네뜨,진한식품,부농,오래식품,나린코퍼레이션,
        에스알인터내셔널,Zuger,조일푸드,성경식품,건아푸드,장수식품,피자코리아,새롬식품,비젼푸드,샌드팜,옵스,
        부산축산,맘스쌀과자,하몽푸드,시루에담은꿈,영어조합법인정품수산,정품수산,하늘바이오,하얀햇살,부림떡전문점,
        사조남부햄,희창유업,사조,뚜또,천연식품,안동식혜영농조합법인,솜모바이오,도울바이오푸드,세종공장,정담원,
        총체보리한우유통센터,한우물,목장의아침,산중의_아침,천본,영푸드영농조합법인,백인관광농원,웃담요구르드,
        홍성풀무,광천삼원식품,가온앤푸드,심천식품,대복식품,온새미로,에스앤비푸드,애스앤비푸드,사옹원,강식품,쌍인,
        고추명가참식품,씨알푸드,올가니카,휴럼,이목원,영준목장,에그앤씨드,횡성축산업협동조합,서울에프엔비,
        서울에프앤비,이롬,일동후디스,현농,요타미트,아미아이스,미가인에프앤비,서정쿠킹,서청쿠킹,
        카파아이엔티,신흥식품,팜드림,디에프씨푸드,선양,신승식품,자인,창조식품,디에스푸드시스템,고향식품,벌집패밀리,
        두둑한_행복,채선당,해태제과식품,푸른식품,팔공산김치,부산등대식품,부광푸드,세진에프엔에스,눌천,대왕,코끼리푸드,
        안동간고등어,장보고FOOD,현성바이탈,다원영농조합,다원에프에스,에이치엔지에프,옹고집영농조합법인,안터원목장,안터원_목장,
        동그라미_식품,동그라미플러스,우리면,웰빙랜드,논산딸기랜드,해오름식품,미래식품,다영푸드,남연FNG,남연FnG,모닝후르츠,모닝후루츠,
        남미에스앤에프,효성,삼양패키징,아셉시스글로벌,다원,돈마루,태경식품,메디오젠,옻가네,영양제과,푸르샨식품,케이씨푸드,레드앤그린푸드,
        천년취떡,로뎀푸드,씨알살림축산,뉴트리바이오텍,다경,황소식품,토스크,떡다움,조은유통,조이푸드,엔젤식품,선농생활,케이엔씨푸드,
        송림식품,다담,아우레이트,농심,삼다,거창공장,지산식품,아이스올리,신선에프앤브이,해드림푸드,해드림한우,미소담은,다미원,
        씨지엠,영양고추유통공사,해림후코이단,천지해영어조합법인,산들촌,푸드웰,성가정식품,세인식품,광천별식품,해맞이식품,복음자리,
        건강마을,수암,정풍,상신종합식품,신선나또,금호식품,한일팜스,모란식품,이담,초이푸드,청솔식품,영진에프에스,풍림푸드,대웅생명과학,
        위드강산,정이푸드빌,범산목장,리뉴얼라이프,꽃샘식품,오션플러스,자양에프앤비,마크로드제과,조은제과,영화식품주식회사,영화식품,
        에스지푸드,티젠,대양식품,오병이어식품,온누리식품,NDK식품,농업회사법인세양,대한푸드,로젠식품,삼립식품,크라운제과,
        농업회사법인,밥상푸드,궁정방,궁전방,신라식품,그대로팜스,굿앤푸드,유림수산유통,성진수산,베델농산,국순당,영농조합법인,성심마스타푸드,
        나이스푸드,청아띠농업사회법인,자연친구,동서제약웰빙,강동오케익,삼우냉동,농민식품,대천김,대천맛김,종근당건강,아하식품,
        선진햄,동원,신진물산,선호식품,견우마을,그린힐,진흥식품,늘푸른,해우촌,도미솔식품,푸디스,지비오,정탑농산,유씨네,아이탐푸드,
        상도푸드,한아담식품,맛잽이식품,미농식품,우영수산,에스앤에스식품,그이름,일해,유성물산교역,빅마마씨푸드,올품,
        올폼,라온에프에스,미가식품영농조합,선혜청,해태음료,동우,청양군장애인재활근로센터,으뜸농산,우일식품,신세계에스브이엔,
        후래쉬애그푸드,대일,체리부로,주원산오리,후두원,홍천남산식품, 세준에프앤비,대영지에프,
        한강씨엠,청산식품,렉스팜,해맛,CJ씨푸드,CJ_씨푸드,현대그린푸드,동서,마니커,영의정,고궁,동해식품,파머스,영진식품,정인유통,
        청미식품,떡파는_사람들,떡파는사람들,세원피앤피,영남제분주식회사,대한제분,신앙촌식품,평화의마을,젠푸드,엠에스씨,선인,
        경북대학교포도마을,비스트로시스,니껴바이오,소디프비앤에프,주그릭슈바인,소디프,청아냉동식품,행남식품,한비즌,
        참식품,참고을,노고단식품,전북대햄,명광식품,선제,다농원,삼아아시아,웰팜,송림푸드,조은식품,산돌식품,해오름주식회사,
        해오름_주식회사,해오름,피피이씨춘천,세창식품,성원냉동식품,왕궁병과,리빙라이프,유림식품,제이와이에프엔비,
        제이와이에프앤비,성원식품,꿀맛나는세상,한주,씨튼장애인직업재활센터,씨튼베이커리,우리찬,우리찬에이치제이에프,
        에이치제이에프,HJF,디딤,한솔식품,한솔식품웰본,웰본,풍조식품,나누리식품,삼양씨푸드,영진엠앰에프,v퓨어플러스,퓨어플러스,
        v홈플러스,홈플러스,거제사슴영농조합,인덕식품,해뜨락,두메식품,성원물산,예다손,해송식품,코카_콜라,한국음료,
        다기원식품,번영식품,로만,서천맛김,금풍제과,씨제이_제일제당,CJ제일제당,디케이냉동식품,DK냉동식품,삼조쎌텍,씨엔에스푸드시스템,
        늘찬,리치푸드시스템,우일음료,스포츠바이오텍,엄마사랑,코쿠엔스,청해농수산,팜텍코리아,진어전가마보꼬,진어전,
        眞어전가마보꼬,디핀다트코리아,옥천식품,주식회사동양아이스,동양아이스,고을,세인플러스,이우스푸드,대흥푸드,오뚜기,
        새아침,강화섬김치,참푸드,풍년식품,제주오렌지,제주특별자치도개발공사,두레방,강림오가닉,광동종합식품,동심코칠리,미정,
        영산홍어,유미원,선일물산,엄지식품,청정태안식품,영신식품,동진제약,하이세븐,제이엠푸드,상미식품주식회사,들빛식품,국제제과,
        양양오색한과,신성한식품,신한성식품,코웰식품,v녹선,녹선,v한영엠에스,한영엠에스,청양식품,청양식품대신농산,대신농산대신농산,
        대신농산,대산농산,코주부_씨앤에프,코주부,v양지마을,양지마을,위캔센터,위캔쿠키,위캔쿠키화경물산,화경물산,우향우,
        삼양사,바이오포트코리아,v영진그린식품,영진그린식품,화과방,v쟈뎅,쟈뎅,원일식품,홍영식품,홍영식품금영식품,금영식품,
        꿈터종합식품,서울식품,알찬식품,명가랑,청기와집식품,v고려식품,고려식품,엠씨푸드,이씨네,영풍,새부산식품,모심푸드,그린푸드,
        송심수산,송림수산,다농,고추나라,려강,해청식품,오성푸드,오성제과,푸르온,그린촌,어촌수산식품,애디드팩토리,교동씨엠,자연애,
        신한에프앤비,이마트,우리콩식품,진성에프엠,대산인터내셔널,화인푸드라인,미트원,서울제과,대광에프앤씨,대광에프엔씨,
        대경에프앤비,참손푸드,광화,빙그레,소이빈네이처,푸르밀,씨에이치음료,그릭슈바인,알프스식품,미가온푸드,진주햄,청보무역,대호양행,
        도드람푸드,한미메디케어,한미메디컬케어,한미미디어케어,대하종합식품,태림에프웰,일미농수산,미트뱅크,조아제약,부산우유,
        코카코라음료,코카콜라음료,경북과학대학,홍도식품,담양한과,영신수산,한밭식품,보고신약,삼정인터내셔널,세준푸드,일품,칠갑농산,
        조흥,가나유통,서도물산,참마음식품,동양오츠카,동아오츠카,부산어묵,부경식품,한창물산,환공식품,완도물산,나래식품,파이닉스푸드,
        삼육식품,해락원영어조합법인,한국인산공사,웅진식품,웅직식품,신송식품,삼아씨에프,비락,보은대추한과,해마,동그린주식회사,
        동그린,상일식품,네추럴웨이,아담스팜코리아,광동제약,동아제약,피피이씨의령,한성수산식품,지엠에프,
        신흥제과,한국야구르트,괴산군조합공동사업법인,에스피씨삼립,SPC삼립,에스피씨식품,샤니,굿모닝서울,남극인푸드,남극냉동,훼미리식품,
        동아원,영심,대광에프앤지,에이원식품,제이웰,서강유업,제일물산,성지,세진햇김,샬롬산업,대한식품,맛가마식품,
        삼양밀맥스,삼육수산,이노바,현복식품,화성한과,한성한과,삼진지디에프,삼진GDF,홍선,동아제과,현대약품,신라명과,
        한성기업,참가득,남부식품,신생활식품,청호식품,한울,신도안종합식품,연세우유,
        면사랑,대영식품,비알코리아,한양식품,금촌베이커리,한샘식품,대웅,야미푸드,야미,세계식품주식회사,세계식품,상일,대두식품,
        덕산식품,썬브레이크푸드,오천식품,삼포식품,움트리,태광식품,동남,대창식품,신라에스지,해양식품,서안,삼해상사,심해상사,
        영우냉동식품,세미기업,삼아인터내셔날,삼아인터네셔날,동방푸드마스타,동방푸드,삼진푸드,삼아벤처,아그라나프루트코리아,
        아그라나_푸르트_코리아,동림푸드,우천식품,한보제과,세우,유창성업,코롬방제과,크롬방제과,코롱방제과,진미농산,한성식품,
        삼영수산,성찬식품,참맛,씨에이치,청학에프엔씨,청학에프엔씨선진식품,선진식품선진식품,선진식품,진현식품,보해양조,보혜양조,
        파이닉스,파이닉스두솔,두솔,두솔태웅식품,태웅식품,한일후드,고려은단,대경햄,참들애,산송식품,피피이씨음성생면,삼양식품,
        원주공장,삼양,파리크라상,몽고식품주식회사,정화식품,오천산업,오리온,델로스에프앤비,에스트라,한일식품,씨제이푸드,덕화푸드,
        해태제과,도들샘,광일식품,후덕물산,네이처텍,네이처텍파스퇴르유업,파스퇴르유업,삼성식품,진유원,샘표식품,동성식품,평화식품,
        광천조양식품,의남식품주식회사,일양약품,피엔에프에스,피엔에프에스태원식품산업,태원식품산업,태원식품,고려수산,고려인삼제조,
        양주공장,동양종합식품주식회사,동양종합식품,하이앤드푸드,정식품,정남식품,광주공장,삼경물산,강릉초당두부ㅣ강원,
        강릉초당두부,태화식품,태성물산,한국네슬레주식회사,네슬레,창성,아이푸드,일화,동아오스카,제주우유,
        대한제당,동보식품,합동산업,유맥,황가네푸드,오복식품,쌍계명차,쌍계제다,삼진GF,한국제다,청수식품주식회사,
        삼광식품,로얄제과,금한산업,백암식품,오빌식품,개미식품,풍국면,기장특산물영어조합,부산세광식품,대림선,
        네이처셀,담터,부산아이스크림,만구,한국에스비식품,미도식품,매일식품주식회사,매일식품;

    }

    public static final String FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE="연결 실패";
    @AllArgsConstructor
    @Getter
    public enum ECategory {
        snack("간식",ESnackCategory.values(),R.drawable.item_gansik),
        dairy("유제품", EDairyCategory.values(),R.drawable.item_dairy),
        drink("음료/차", EDrinkCategory.values(),R.drawable.item_beverage),
        condiment("조미료", ECondimentCategoty.values(),R.drawable.item_salt),
        meat("육류", EMeatCategory.values(),R.drawable.item_meat),
        farm("농수산물",EFarmCategory.values(),R.drawable.item_fruit),
        kimchi("김치",EKimchiCategory.values(),R.drawable.item_kimchi),
        mealkit("즉석조리",EMealkitCategory.values(),R.drawable.item_mealkit),
        etcmaterial("식재료",EMaterialCategory.values(),R.drawable.item_material),
        etc("기타",EEtcCategory.values(),R.drawable.item_etc)
        ;
        private String name;
        private EChildCategory[] childCategories;
        private int drawable;

        public Vector<String> getChildNames() {
            Vector<String> childNames = new Vector(Arrays.asList(childCategories));
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
        제품("foodName"), 회사("manufacturerName"), 카테고리("categoryName");
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
