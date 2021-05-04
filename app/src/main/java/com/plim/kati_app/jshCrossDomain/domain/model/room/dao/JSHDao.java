package com.plim.kati_app.jshCrossDomain.domain.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;

import java.util.List;

@Dao
public interface JSHDao {

    @Insert
    void insert(JSHEntity data);

    @Update
    void update(JSHEntity... KatiEntity);

    @Delete
    void delete(JSHEntity data);

    @Query("SELECT * FROM jsh_table")
    LiveData<List<JSHEntity>> getData();
}
