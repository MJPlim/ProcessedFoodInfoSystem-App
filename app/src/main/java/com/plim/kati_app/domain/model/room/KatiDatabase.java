package com.plim.kati_app.domain.model.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {KatiData.class}, version = 1)
public abstract class KatiDatabase extends RoomDatabase {

    public static final String AUTHORIZATION = "Authorization";
    public static final String AUTO_LOGIN = "AutoLogin";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "Password";
    private static KatiDatabase INSTANCE;

    /**
     * DAO 쉽게 말해 디비에 쿼리 해주는 역할.
     * @return DAO 인스턴스
     */
    public abstract KatiDataDao katiDataDao();

    /**
     * 디비 만드는 메소드.
     * @param context 부르는 쪽의 컨텍스트
     * @return 만들어진 디비 인스턴스.
     */
    public static KatiDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, KatiDatabase.class , "todo-db")
                    .build();
        }
        return  INSTANCE;
    }

    /**
     * 디비 인스턴스를 제거하는 메소드.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
