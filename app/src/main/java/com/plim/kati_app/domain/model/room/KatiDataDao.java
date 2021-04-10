package com.plim.kati_app.domain.model.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface KatiDataDao {

    @Query("SELECT value FROM kati_data WHERE name = :name")
    String getValue(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(KatiData data);

    @Query("DELETE FROM kati_data WHERE name= :name")
    void delete(String name);

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    void insertAll(List<KatiData> katiDatas);
}
