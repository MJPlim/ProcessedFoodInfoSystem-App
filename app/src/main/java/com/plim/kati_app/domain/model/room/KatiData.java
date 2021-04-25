package com.plim.kati_app.domain.model.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 데이터 베이스 내의 테이블, kati_data.
 */
@Entity(tableName = "kati_data")
@AllArgsConstructor
@Getter
@Setter
public class KatiData {
    @NonNull
    @PrimaryKey
    private String name;
    @ColumnInfo(name = "value")
    private String value;

}
