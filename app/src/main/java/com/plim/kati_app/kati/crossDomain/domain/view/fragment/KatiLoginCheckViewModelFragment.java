package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBack;
import com.plim.kati_app.kati.domain.nnew.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lombok.AllArgsConstructor;
import retrofit2.Response;

public abstract class KatiLoginCheckViewModelFragment extends KatiViewModelFragment {
    @Override
    protected void katiEntityUpdated() {
        if (this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION))
            this.katiEntityUpdatedAndLogin();
        else {
            if (this.isLoginNeeded()){
                this.notLoginDialog();
            }
            else
                this.katiEntityUpdatedAndNoLogin();
        }
    }


    @AllArgsConstructor
    public abstract class SimpleLoginRetrofitCallBack<T> implements SimpleRetrofitCallBack<T> {
        private Activity activity;


        public void onFailResponse(Response<T> response) throws IOException, JSONException {
            if (response.code() == 400) {
                Log.d("여기?","리스폰스 "+dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
                this.removeToken();
                KatiDialog.NotLogInDialog(
                        this.activity,
                        (dialog, which) -> {
                            this.activity.startActivity(new Intent(this.activity, LoginActivity.class));
                        }
                );
            } else {
                JSONObject object = new JSONObject(response.errorBody().string());
                KatiDialog.RetrofitNotSuccessDialog(
                        this.activity,
                        object.has("error-message") ? object.getString("error-message") : object.toString(),
                        response.code(),
                        null
                );
            }
        }

        @Override
        public void onResponse(Response<T> response) {
            this.refreshToken(response.headers().get("Authorization"));
        }

        public void onConnectionFail(Throwable t) {
            KatiDialog.RetrofitFailDialog(this.activity, null);
        }

        private void refreshToken(String authorization) {
            putToken(authorization);
        }

        private void removeToken() {
            deleteToken();
        }
    }


    protected abstract boolean isLoginNeeded();

    protected abstract void katiEntityUpdatedAndLogin();

    protected abstract void katiEntityUpdatedAndNoLogin();

    protected void notLoginDialog() {
        KatiDialog.NotLogInDialog(this.getActivity(), (dialog, which) -> {
            this.startActivity(LoginActivity.class);
        });
    }

    protected void putToken(String authorization) { this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, authorization); }

    protected void deleteToken() { this.dataset.remove(KatiEntity.EKatiData.AUTHORIZATION); this.save(); }

}
