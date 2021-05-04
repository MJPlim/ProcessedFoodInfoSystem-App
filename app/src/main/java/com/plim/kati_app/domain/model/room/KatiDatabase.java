//package com.plim.kati_app.domain.model.room;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.room.migration.Migration;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//
///**
// * RoomDatabase를 상속받아 만든 데이터베이스 클래스.
// */
//@Database(entities = {KatiData.class, KatiSearchWord.class}, version = 2)
//public abstract class KatiDatabase extends RoomDatabase {
//
//    //static 기본키 이름들.
//    public static final String AUTHORIZATION = "Authorization";
//    public static final String AUTO_LOGIN = "AutoLogin";
//    public static final String EMAIL = "email";
//    public static final String PASSWORD = "Password";
//
//    public static final String TRUE="1";
//    public static final String FALSE="0";
//
//    //database 인스턴스 (실행하는 동안 하나만 유지한다.)
//    private static KatiDatabase INSTANCE;
//    //인스턴스 생성 시 주는 이름.
//    private static final String INSTANCE_NAME="todo-db";
//
//    /**
//     * 룸 디비의 버전이 바뀔 때 안전하게 버전 이동. 1버전->2버전
//     */
//    private static final Migration MIGRATION_1_2= new Migration(1,2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE 'kati_search_word' ('value' TEXT NOT NULL, PRIMARY KEY ('value'))");
//        }
//    };
//
//
//    /**
//     * DAO 쉽게 말해 디비에 쿼리 해주는 역할.
//     * @return DAO 인스턴스
//     */
//    public abstract KatiDataDao katiDataDao();
//
//    /**
//     * DAO 쉽게 말해 디비에 쿼리 해주는 역할.
//     * @return DAO 인스턴스
//     */
//    public abstract KatiSearchWordDao katiSearchWordDao();
//
//    /**
//     * 디비 만드는 메소드.
//     * @param context 부르는 쪽의 컨텍스트
//     * @return 만들어진 디비 인스턴스.
//     */
//    public static KatiDatabase getAppDatabase(Context context){
//        if(INSTANCE == null){
//            INSTANCE = Room.databaseBuilder(context, KatiDatabase.class , INSTANCE_NAME).addMigrations(MIGRATION_1_2)
//                    .build();
//        }
//        return  INSTANCE;
//    }
//
//    /**
//     * 디비 인스턴스를 제거하는 메소드.
//     */
//    public static void destroyInstance() {
//        INSTANCE = null;
//    }
//
//
//
//}
