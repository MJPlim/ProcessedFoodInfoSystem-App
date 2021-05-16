package com.plim.kati_app.domain.view.changePW;

import android.view.View;
import android.widget.Toast;

import com.plim.kati_app.domain.katiCrossDomain.domain.view.AbstractFragment2;
import com.plim.kati_app.domain.katiCrossDomain.domain.view.KatiDialog;
import com.plim.kati_app.domain.katiCrossDomain.domain.view.LoadingDialog;
import com.plim.kati_app.domain.katiCrossDomain.domain.model.forBackend.userAccount.ModifyPasswordRequest;
import com.plim.kati_app.domain.katiCrossDomain.domain.model.forBackend.userAccount.ModifyPasswordResponse;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.domain.katiCrossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.domain.katiCrossDomain.domain.model.forFrontend.KatiEntity;
import com.plim.kati_app.domain.view.TempMainActivity;
import com.plim.kati_app.domain.view.temp.logout.LogOutActivity;

import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.AFTER_PASSWORD_HINT;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.AFTER_PASSWORD_HINT2;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.BEFORE_PASSWORD_HINT;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.CHANGE_PASSWORD_TITLE;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.COMPLETE_CHANGE_PASSWORD_MESSAGE;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.COMPLETE_CHANGE_PASSWORD_TITLE;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.DIALOG_CONFIRM;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;

public class ChangePasswordInputFragment extends AbstractFragment2 {

    // Component
        // View
        private LoadingDialog loadingDialog;

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText(CHANGE_PASSWORD_TITLE);
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(BEFORE_PASSWORD_HINT);
        this.editText2.setHint(AFTER_PASSWORD_HINT);
        this.editText3.setHint(AFTER_PASSWORD_HINT2);
        this.button.setText(DIALOG_CONFIRM);
    }
    @Override
    protected void katiEntityUpdated() {
        if(!this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){ this.showNotLoginedDialog(); }
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() {
        if (this.editText2.getText().toString().equals(this.editText3.getText().toString())) {
            this.loadingDialog = new LoadingDialog(this.getContext());
            this.loadingDialog.show();

            String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
            ModifyPasswordRequest request = new ModifyPasswordRequest();
            request.setBeforePassword(this.editText.getText().toString());
            request.setAfterPassword(this.editText2.getText().toString());
            KatiRetrofitTool.getAPIWithAuthorizationToken(token).modifyPassword(request).enqueue(JSHRetrofitTool.getCallback(new ChangePasswordRequestCallback()));
        } else {
            Toast.makeText(getContext(), "새 비밀번호를 동일하게 입력해 주세요.", Toast.LENGTH_LONG).show();
        }
    }
    private class ChangePasswordRequestCallback implements JSHRetrofitCallback<ModifyPasswordResponse> {
        @Override
        public void onSuccessResponse(Response<ModifyPasswordResponse> response) {
            loadingDialog.hide();
            showCompletedDialog();
        }
        @Override
        public void onFailResponse(Response<ModifyPasswordResponse> response) {
            loadingDialog.hide();
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onConnectionFail(Throwable t) {
            loadingDialog.hide();
        }
    }

    /**
     * Method
     */
    private void showNotLoginedDialog() {
        KatiDialog.simplerAlertDialog(this.getActivity(),
                LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE, LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
                (dialog, which) -> this.startActivity(TempMainActivity.class)
        );
    }
    private void showCompletedDialog() {
        KatiDialog.simplerAlertDialog(this.getActivity(),
                COMPLETE_CHANGE_PASSWORD_TITLE, COMPLETE_CHANGE_PASSWORD_MESSAGE,
                (dialog, which) -> this.startActivity(LogOutActivity.class)
        );
    }
}