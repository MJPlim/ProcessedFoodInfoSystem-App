package com.plim.kati_app.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JSHRetrofitTool {

    public static <T>Callback<T> getCallback(JSHRetrofitCallback<T> callback){
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(response.isSuccessful()){ callback.onSuccessResponse(response); }
                else{ callback.onFailResponse(response); }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) { callback.onConnectionFail(t); }
        };
    }
}
