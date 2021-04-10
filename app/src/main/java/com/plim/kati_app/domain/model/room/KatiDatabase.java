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
//    @VisibleForTesting
//    public static final String DATABASE_NAME = "kati_data";
    public abstract KatiDataDao katiDataDao();

    private static KatiDatabase INSTANCE;


    //디비객체생성 가져오기
    public static KatiDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, KatiDatabase.class , "todo-db")
                    .build();

            /*INSTANCE = Room.databaseBuilder(context, TodoDatabase.class , "todo-db")
                    .allowMainThreadQueries() =>이걸 추가해서 AsyncTask를 사용안하고 간편하게할수있지만 오류가많아 실제 앱을 만들때 사용하면 안된다고한다.
                    .build();*/
        }
        return  INSTANCE;
    }

    //디비객체제거
    public static void destroyInstance() {
        INSTANCE = null;
    }

//    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();
//
//    public static KatiDatabase getInstance(final Context context, final AppExecutors executors) {
//        if (katiDatabaseInstance == null) {
//            synchronized (KatiDatabase.class) {
//                if (katiDatabaseInstance == null) {
//                    katiDatabaseInstance = buildDatabase(context.getApplicationContext(), executors);
//                    katiDatabaseInstance.updateDatabaseCreated(context.getApplicationContext());
//                }
//            }
//        }
//        return katiDatabaseInstance;
//    }
//
//    /**
//     * Build the database. {@link Builder#build()} only sets up the database configuration and
//     * creates a new instance of the database.
//     * The SQLite database is only created when it's accessed for the first time.
//     */
//    private static KatiDatabase buildDatabase(final Context appContext,
//                                              final AppExecutors executors) {
//        return Room.databaseBuilder(appContext, KatiDatabase.class, DATABASE_NAME)
//                .addCallback(new Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//                        executors.diskIO().execute(() -> {
//                            // Add a delay to simulate a long-running operation
//                            addDelay();
//                            // Generate the data for pre-population
//                            KatiDatabase database = KatiDatabase.getInstance(appContext, executors);
//                            List<ProductEntity> products = DataGenerator.generateProducts();
//                            List<CommentEntity> comments =
//                                    DataGenerator.generateCommentsForProducts(products);
//
//                            insertData(database, products, comments);
//                            // notify that the database was created and it's ready to be used
//                            database.setDatabaseCreated();
//                        });
//                    }
//                })
//                .addMigrations(MIGRATION_1_2)
//                .build();
//    }
//
//    /**
//     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
//     */
//    private void updateDatabaseCreated(final Context context) {
//        if (context.getDatabasePath(DATABASE_NAME).exists()) {
//            setDatabaseCreated();
//        }
//    }
//
//    private void setDatabaseCreated() {
//        isDatabaseCreated.postValue(true);
//    }
//
//    private static void insertData(final KatiDatabase database, final List<KatiData> katiDatas) {
//        database.runInTransaction(() -> {
//            database.katiDataDao().insertAll(katiDatas);
//        });
//    }
//
//    private static void addDelay() {
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException ignored) {
//        }
//    }
//
//    public LiveData<Boolean> getDatabaseCreated() {
//        return isDatabaseCreated;
//    }
}
