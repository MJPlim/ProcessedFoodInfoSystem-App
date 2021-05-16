package com.plim.kati_app.domain.katiCrossDomain.domain.view.abstractTableFragment;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TableInfo {

    private String name;
    private HashMap<Integer, Data> valueMap;

    // Constructor
    public TableInfo(String name, HashMap<String, String> map) {
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
