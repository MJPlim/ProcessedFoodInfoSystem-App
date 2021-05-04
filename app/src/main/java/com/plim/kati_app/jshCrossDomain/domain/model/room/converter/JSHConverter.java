package com.plim.kati_app.jshCrossDomain.domain.model.room.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class JSHConverter {
    public static <T> T fromStringToType(String value) {
        Type listType = new TypeToken<T>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    public static <T> String fromTypeToString(T type) {
        Gson gson = new Gson();
        String json = gson.toJson(type);
        return json;
    }
}
