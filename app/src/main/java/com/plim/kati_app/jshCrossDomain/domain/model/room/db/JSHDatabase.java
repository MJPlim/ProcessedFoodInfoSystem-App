package com.plim.kati_app.jshCrossDomain.domain.model.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.plim.kati_app.jshCrossDomain.domain.model.room.dao.JSHDao;
import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Database(entities = {JSHEntity.class}, version = 3, exportSchema = false)
public abstract class JSHDatabase extends androidx.room.RoomDatabase {

    // Constant
    private static final int NUMBER_OF_THREADS = 4;
    private static volatile JSHDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static JSHDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (JSHDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), JSHDatabase.class, "jsh_database")
                            .fallbackToDestructiveMigration() // 버전 바꾸면 리셋
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract JSHDao jshDao();
}
