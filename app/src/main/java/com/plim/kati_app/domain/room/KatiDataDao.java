package com.plim.kati_app.domain.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * 데이터베이스에 쿼리를 하기 위해 정의한 DAO 인터페이스.
 */
@Dao
public interface KatiDataDao {

    /**
     * 값을 찾는다.
     * @param name 기본키.
     * @return 기본키에 해당하는 데이터.
     */
    @Query("SELECT value FROM kati_data WHERE name = :name")
    String getValue(String name);

    /**
     * 값을 추가하는데, 만약 이미 있으면 업데이트한다.
     * @param data 추가할 데이터.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(KatiData data);

    /**
     * 기본키에 해당하는 값을 지운다.
     * @param name 기본키.
     */
    @Query("DELETE FROM kati_data WHERE name= :name")
    void delete(String name);

    /**
     * 여러 개의 값을 추가한다.
     * @param katiDatas 추가할 여러 개의 데이터.
     */
    @Insert(onConflict =OnConflictStrategy.REPLACE)
    void insertAll(List<KatiData> katiDatas);
}
