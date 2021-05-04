package com.plim.kati_app.jshCrossDomain.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.plim.kati_app.jshCrossDomain.model.room.dao.JSHDao;
import com.plim.kati_app.jshCrossDomain.model.room.db.JSHDatabase;
import com.plim.kati_app.jshCrossDomain.model.room.entity.JSHEntity;

import java.util.List;

public class JSHRepository {

    // Association
    private JSHDao jshDao;
    private LiveData<List<JSHEntity>> dataset;

    // Constructor
    public JSHRepository(Application application) {
        JSHDatabase db = JSHDatabase.getDatabase(application);
        this.jshDao = db.jshDao();
        this.dataset = this.jshDao.getData();
    }

    public void insert(JSHEntity data) { JSHDatabase.databaseWriteExecutor.execute(() -> { this.jshDao.insert(data); }); }
    public void update(JSHEntity... dataset) { JSHDatabase.databaseWriteExecutor.execute(() -> { this.jshDao.update(dataset); }); }
    public void delete(JSHEntity data) { JSHDatabase.databaseWriteExecutor.execute(() -> { this.jshDao.delete(data); }); }
    public LiveData<List<JSHEntity>> getDataset() { return this.dataset; }
}
