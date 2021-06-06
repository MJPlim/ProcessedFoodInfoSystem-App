package com.plim.kati_app.kati.domain.nnew.findId;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiHasTitleActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.findId.model.FindEmailRequest;
import com.plim.kati_app.kati.domain.nnew.findId.model.FindEmailResponse;
import com.plim.kati_app.kati.domain.nnew.main.MainActivity;

import retrofit2.Response;


public class FindIdActivity extends KatiHasTitleActivity {

    Button setRestoreEmailAddressButton;
    EditText editTextRestoreEmail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_find;
    }

    @Override
    protected void associateView() {
        this.setRestoreEmailAddressButton = findViewById(R.id.setRestoreEmailAddressButton);
        this.editTextRestoreEmail = findViewById(R.id.restoreEmailActivity_restoreEmailEditText);

    }

    @Override
    protected void initializeView() {
        this.setRestoreEmailAddressButton.setOnClickListener(v -> this.findEmail());
    }

    @Override
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    private void findEmail () {
        FindEmailRequest request = new FindEmailRequest();
        request.setSecondEmail(this.editTextRestoreEmail.getText().toString());
        KatiRetrofitTool.getAPI().findEmail(request).enqueue(JSHRetrofitTool.getCallback(new FindEmailRequestCallback()));
    }

    private class FindEmailRequestCallback implements JSHRetrofitCallback<FindEmailResponse> {
        @Override
        public void onSuccessResponse(Response<FindEmailResponse> response) {
            showCompletedDialog();
        }

        @Override
        public void onFailResponse(Response<FindEmailResponse> response) {
            showNoUserDialog();
            Log.d("Test", response.message());
        }

        @Override
        public void onConnectionFail(Throwable t) {
            Log.d("연결 실패", t.getMessage());
        }
    }

    private void showNoUserDialog () {
        KatiDialog.simplerAlertDialog(this,
                Constant.NO_USER_DIALOG_TITLE,
                Constant.NO_USER_DIALOG_MESSAGE,
                (dialog, which) -> this.editTextRestoreEmail.setText("")
        );
    }

    private void showCompletedDialog () {
        KatiDialog.simplerAlertDialog(this,
                getString(R.string.find_user_email_dialog_title), getString(R.string.find_user_email_dialog_message),
                (dialog, which) -> this.startMainActivity()
        );
    }

    public void startMainActivity () {  this.startActivity(new Intent(this, MainActivity.class)); }
    }