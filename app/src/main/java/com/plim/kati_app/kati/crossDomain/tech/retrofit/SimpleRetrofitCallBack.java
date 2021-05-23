package com.plim.kati_app.kati.crossDomain.tech.retrofit;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Response;

public interface SimpleRetrofitCallBack<T> {

    void onSuccessResponse(Response<T> response);//성공시에 할 일

    void onFailResponse(Response<T> response) throws IOException, JSONException;//실패시 할 일

    void onResponse(Response<T> response);//성공 실패 상관 없이 할 일

    void onConnectionFail(Throwable t);//연결 실패할 때 할 일
}
