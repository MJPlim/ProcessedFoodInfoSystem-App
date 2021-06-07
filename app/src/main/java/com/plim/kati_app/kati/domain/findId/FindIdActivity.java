package com.plim.kati_app.kati.domain.findId;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiHasTitleActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.findId.model.FindEmailRequest;
import com.plim.kati_app.kati.domain.findId.model.FindEmailResponse;
import com.plim.kati_app.kati.domain.nnew.main.MainActivity;

import retrofit2.Response;


public class FindIdActivity extends KatiHasTitleActivity {

    private LoadingDialog loadingDialog;
    private Button setRestoreEmailAddressButton;
    private EditText editTextRestoreEmail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_find;
    }

    @Override
    protected void associateView() {
        super.associateView();
        this.setRestoreEmailAddressButton = findViewById(R.id.idFindActivity_sumbitButton);
        this.editTextRestoreEmail = findViewById(R.id.idFindActivity_emailEditText);

    }

    @Override
    protected void initializeView() {
        super.initializeView();
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

    /**
     * callback
     */
    private class FindEmailRequestCallback extends SimpleRetrofitCallBackImpl<FindEmailResponse> {
        public FindEmailRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<FindEmailResponse> response) {
            showCompletedDialog();
        }

        @Override
        public void onResponse(Response<FindEmailResponse> response) {
            super.onResponse(response);
            loadingDialog.hide();
            loadingDialog.dismiss();
        }

        @Override
        public void onConnectionFail(Throwable t) {
            super.onConnectionFail(t);
            loadingDialog.hide();
            loadingDialog.dismiss();
        }
    }

    /**
     * method
     */
    private void findEmail () {
        this.loadingDialog=new LoadingDialog(this);
        this.loadingDialog.show();
        FindEmailRequest request = new FindEmailRequest();
        request.setSecondEmail(this.editTextRestoreEmail.getText().toString());
        KatiRetrofitTool.getAPI().findEmail(request).enqueue(JSHRetrofitTool.getCallback(new FindEmailRequestCallback(this)));
    }

    private void showCompletedDialog () {
        KatiDialog.simplerAlertDialog(this,
                getString(R.string.find_user_email_dialog_title), getString(R.string.find_user_email_dialog_message),
                (dialog, which) -> this.startMainActivity()
        );
    }

    public void startMainActivity () {  this.startActivity(new Intent(this, MainActivity.class)); }
    }