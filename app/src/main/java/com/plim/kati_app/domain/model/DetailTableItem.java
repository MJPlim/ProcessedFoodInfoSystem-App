package com.plim.kati_app.domain.model;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Abstract Table Fragment ui를 위한 데이터.
 */
@Getter
public class DetailTableItem {
    private String name;//테이블 제목
    private HashMap<Integer, Data> valueMap; //테이블의 행번호와 행 데이터.

    public DetailTableItem(String name, HashMap<String, String> map) {
        this.name = name;
        this.valueMap = new HashMap<>();
        int index = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.valueMap.put(index++, new Data(entry.getKey(), entry.getValue()));
        }
    }

    @AllArgsConstructor
    @Getter
    public class Data {
        private String title, value;
    }
}
