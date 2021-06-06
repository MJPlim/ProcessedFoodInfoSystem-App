package com.plim.kati_app.kati.crossDomain.tech.retrofit;

import android.app.Activity;
import android.widget.Toast;

import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lombok.AllArgsConstructor;
import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_ERROR_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_MESSAGE;

@AllArgsConstructor
public abstract class SimpleRetrofitCallBackImpl<T> implements SimpleRetrofitCallBack<T> {
    protected Activity activity;

    @Override
    public void onFailResponse(Response<T> response) throws IOException, JSONException {
        JSONObject object = new JSONObject(response.errorBody().string());
        Toast.makeText(activity, "("+response.code()+") "+getFailMessage(object), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Response<T> response) {
        return;
    }

    @Override
    public void onConnectionFail(Throwable t) {
        KatiDialog.RetrofitFailDialog(this.activity, null);
    }

    protected String getFailMessage(JSONObject object) throws JSONException {
        return object.has(JSONOBJECT_ERROR_MESSAGE) ? object.getString(JSONOBJECT_ERROR_MESSAGE) :
                object.has(JSONOBJECT_MESSAGE) ? object.getString(JSONOBJECT_MESSAGE) :
                        object.toString();
    }

    ;
}
