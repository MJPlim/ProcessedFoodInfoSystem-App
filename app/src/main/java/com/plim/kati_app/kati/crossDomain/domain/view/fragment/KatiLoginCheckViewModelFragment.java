package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBack;
import com.plim.kati_app.kati.domain.login.LoginActivity;
import com.plim.kati_app.kati.domain.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lombok.AllArgsConstructor;
import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.AUTHORIZATION;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.END_REVIEW;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_ERROR_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.RETROFIT_FAIL_LOGIN_ERROR_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.RETROFIT_FAIL_LOGIN_TIME_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.RETROFIT_FAIL_LOGIN_TIME_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.START_REVIEW;

public abstract class KatiLoginCheckViewModelFragment extends KatiViewModelFragment {
    @Override
    protected void katiEntityUpdated() {
        if (!this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION).equals(KatiEntity.EKatiData.NULL.name()))
            this.katiEntityUpdatedAndLogin();
        else {
            if (this.isLoginNeeded())
                this.notLoginDialog();
            else
                this.katiEntityUpdatedAndNoLogin();
        }
    }


    @AllArgsConstructor
    public abstract class SimpleLoginRetrofitCallBack<T> implements SimpleRetrofitCallBack<T> {
        private Activity activity;


        public void onFailResponse(Response<T> response) throws IOException, JSONException {
            JSONObject object = new JSONObject(response.errorBody().string());
            String message = object.has(JSONOBJECT_ERROR_MESSAGE) ? object.getString(JSONOBJECT_ERROR_MESSAGE) : object.toString();

            if (message.contains(RETROFIT_FAIL_LOGIN_ERROR_MESSAGE)) {
                this.removeToken();
                ;
                KatiDialog.simplerTwoOptionAlertDialog(
                        this.activity,
                        RETROFIT_FAIL_LOGIN_TIME_TITLE,
                        RETROFIT_FAIL_LOGIN_TIME_MESSAGE,
                        (dialog, which) -> this.activity.startActivity(new Intent(this.activity, LoginActivity.class)),
                        this.getCancelListener()
                );
            } else
                Toast.makeText(activity, START_REVIEW+response.code()+END_REVIEW+" "+getFailMessage(object), Toast.LENGTH_SHORT).show();
        }

        public DialogInterface.OnClickListener getCancelListener() {
            return null;
        }

        @Override
        public void onResponse(Response<T> response) {
            this.refreshToken(response.headers().get(AUTHORIZATION)!=null? response.headers().get(AUTHORIZATION): KatiEntity.EKatiData.NULL.name());
        }

        public void onConnectionFail(Throwable t) {
            KatiDialog.RetrofitFailDialog(this.activity,this.getClass().getSimpleName(), null);
        }

        protected String getFailMessage(JSONObject object) throws JSONException {
            return object.has(JSONOBJECT_ERROR_MESSAGE) ? object.getString(JSONOBJECT_ERROR_MESSAGE) :
                    object.has(JSONOBJECT_MESSAGE) ? object.getString(JSONOBJECT_MESSAGE) :
                            object.toString();
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
        KatiDialog.NotLogInDialog(this.getActivity(), (dialog, which) -> this.startActivity(LoginActivity.class), (dialog, which) -> this.startActivity(MainActivity.class));
    }

    protected void putToken(String authorization) {
        this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, authorization);
    }

    protected void deleteToken() {
        this.dataset.put(KatiEntity.EKatiData.EMAIL, KatiEntity.EKatiData.NULL.name());
        this.dataset.put(KatiEntity.EKatiData.PASSWORD, KatiEntity.EKatiData.NULL.name());
        this.dataset.put(KatiEntity.EKatiData.NAME, KatiEntity.EKatiData.NULL.name());
        this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN, KatiEntity.EKatiData.FALSE.name());
        this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, KatiEntity.EKatiData.NULL.name());
        this.save();
    }

}
