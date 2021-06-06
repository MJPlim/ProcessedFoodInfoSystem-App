package com.plim.kati_app.kati.crossDomain.domain.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.navigation.Navigation;

import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;
import com.plim.kati_app.jshCrossDomain.domain.view.JSHViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntityTool;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBack;
import com.plim.kati_app.kati.domain.nnew.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import lombok.AllArgsConstructor;
import retrofit2.Response;

public abstract class KatiLoginCheckViewModelActivity extends KatiViewModelActivity {
    @Override
    public void katiEntityUpdated() {
        if (!this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION).equals(KatiEntity.EKatiData.NULL.name()))
            this.katiEntityUpdatedAndLogin();
        else {
            if (this.isLoginNeeded()) {
                this.notLoginDialog();
            } else
                this.katiEntityUpdatedAndNoLogin();
        }
    }


    @AllArgsConstructor
    public abstract class SimpleLoginRetrofitCallBack<T> implements SimpleRetrofitCallBack<T> {
        private Activity activity;


        public void onFailResponse(Response<T> response) throws IOException, JSONException {
            JSONObject object = new JSONObject(response.errorBody().string());
            String message = object.has("error-message") ? object.getString("error-message") : object.toString();

            if (message.contains("로그인을 해주세요.")) {
                this.removeToken();
                ;
                KatiDialog.simplerTwoOptionAlertDialog(
                        this.activity,
                        "로그인 만료",
                        "로그인 후 시간이 지나 만료되었습니다. 다시 로그인 하시겠습니까?",
                        (dialog, which) -> this.activity.startActivity(new Intent(this.activity, LoginActivity.class)),
                        this.getCancelListener()
                );
            } else
                KatiDialog.RetrofitNotSuccessDialog(
                        this.activity,
                        message,
                        response.code(),
                        null
                );
        }

        @Override
        public void onResponse(Response<T> response) {
            this.refreshToken(response.headers().get("Authorization"));
        }

        public void onConnectionFail(Throwable t) {
            KatiDialog.RetrofitFailDialog(this.activity, null);
        }

        public DialogInterface.OnClickListener getCancelListener() {
            return null;
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
        KatiDialog.NotLogInDialog(this, (dialog, which) -> this.startActivity(LoginActivity.class), (dialog, which) -> this.onBackPressed());
    }

    protected void putToken(String authorization) {
        this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, authorization);
    }

    protected void deleteToken() {
        this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, KatiEntity.EKatiData.NULL.name());
    }


}
