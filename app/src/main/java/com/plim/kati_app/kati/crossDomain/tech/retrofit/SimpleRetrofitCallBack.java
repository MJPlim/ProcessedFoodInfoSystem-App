package com.plim.kati_app.kati.crossDomain.tech.retrofit;

import android.app.Activity;
import android.content.Intent;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.domain.TempMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lombok.AllArgsConstructor;
import retrofit2.Response;

@AllArgsConstructor
public abstract class SimpleRetrofitCallBack<T> {
    private Activity activity;

    public abstract void onSuccessResponse(Response<T> response);

    public void onFailResponse(Response<T> response) throws IOException, JSONException {
        JSONObject object = new JSONObject(response.errorBody().string());
        KatiDialog.RetrofitNotSuccessDialog(this.activity, object.getString("error-message") + "", response.code(), null);
    }

    public void onConnectionFail(Throwable t) {
        KatiDialog.RetrofitFailDialog(this.activity, (dialog, which) -> this.activity.startActivity(new Intent(this.activity, TempMainActivity.class)));
    }

    public abstract void refreshToken(KatiEntity.EKatiData eKatiData, String authorization);
}
