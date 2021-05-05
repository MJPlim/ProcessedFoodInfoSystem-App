package com.plim.kati_app.jshCrossDomain.domain.model.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.plim.kati_app.jshCrossDomain.domain.model.room.converter.JSHConverter;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity(tableName = "jsh_table")
public class JSHEntity {

    // Attributes
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "entityString")
    private String entityString;
}
