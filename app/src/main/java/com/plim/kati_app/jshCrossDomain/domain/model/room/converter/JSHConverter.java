package com.plim.kati_app.jshCrossDomain.domain.model.room.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Converter 를 Generic 하게 사용하면 Gson 에서 에러 발생.
 * 각 Domain 마다 만들어 사용할 것.
 * 이 Class 는 참고용.
 */
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
