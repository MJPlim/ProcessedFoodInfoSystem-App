//package com.plim.kati_app.domain.model.room;
//
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//
//import java.util.List;
//
///**
// * 데이터베이스에 쿼리를 하기 위해 정의한 DAO 인터페이스.
// */
//@Dao
//public interface KatiSearchWordDao {
//
//    /**
//     * 값을 찾는다.
//     * @param value 기본키.
//     * @return 기본키에 해당하는 데이터.
//     */
//    @Query("SELECT value FROM kati_search_word WHERE value = :value")
//    String getValue(String value);
//
//    /**
//     * 값을 찾는다.
//     * @return 기본키에 해당하는 데이터.
//     */
//    @Query("SELECT value FROM kati_search_word")
//    List<String> getValues();
//
//    /**
//     * 값을 추가하는데, 만약 이미 있으면 업데이트한다.
//     * @param data 추가할 데이터.
//     */
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insert(KatiSearchWord data);
//
//    /**
//     * 기본키에 해당하는 값을 지운다.
//     * @param value 기본키.
//     */
//    @Query("DELETE FROM kati_search_word WHERE value= :value")
//    void delete(String value);
//
//    /**
//     * 여러 개의 값을 추가한다.
//     * @param datas 추가할 여러 개의 데이터.
//     */
//    @Insert(onConflict =OnConflictStrategy.REPLACE)
//    void insertAll(List<KatiSearchWord> datas);
//
//    /**
//     *모든 값을 지운다.
//     */
//    @Query("DELETE FROM kati_search_word")
//    void deleteAll();
//}
