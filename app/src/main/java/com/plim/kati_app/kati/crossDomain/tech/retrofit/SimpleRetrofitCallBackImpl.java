package com.plim.kati_app.kati.crossDomain.tech.retrofit;

import android.app.Activity;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lombok.AllArgsConstructor;
import retrofit2.Response;

@AllArgsConstructor
public abstract class SimpleRetrofitCallBackImpl<T> implements SimpleRetrofitCallBack<T> {
    private Activity activity;

    @Override
    public void onFailResponse(Response<T> response) throws IOException, JSONException {
        JSONObject object = new JSONObject(response.errorBody().string());
        KatiDialog.RetrofitNotSuccessDialog(
                this.activity,
                object.has("error-message")?object.getString("error-message"):object.toString(),
                response.code(),
                null);
    }

    @Override
    public void onResponse(Response<T> response) {return;}

    @Override
    public void onConnectionFail(Throwable t) {
        KatiDialog.RetrofitFailDialog(this.activity, null);
    }

}
