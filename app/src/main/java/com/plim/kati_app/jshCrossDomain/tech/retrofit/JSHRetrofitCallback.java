package com.plim.kati_app.jshCrossDomain.tech.retrofit;

import retrofit2.Response;

public interface JSHRetrofitCallback<T> {
    void onSuccessResponse(Response<T> response);
    void onFailResponse(Response<T> response);
    void onConnectionFail(Throwable t);
}
