package com.plim.kati_app.kati.domain.signOut;

import android.app.Activity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiHasTitleActivity;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.main.MainActivity;
import com.plim.kati_app.kati.domain.signOut.model.WithdrawRequest;
import com.plim.kati_app.kati.domain.signOut.model.WithdrawResponse;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SIGN_OUT_SUCCESS_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SIGN_OUT_SUCCESS_DIALOG_TITLE;


public class SignOutActivity extends KatiHasTitleActivity {

    //associate
    //view
    private TextView userEmailTextView;
    private CheckBox acceptAnnounceCheckBox;
    private Button signOutButton, cancelButton;
    private EditText passwordEditText;

    //working variable
    private boolean loginNeed = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_out;
    }

    @Override
    protected void associateView() {
        super.associateView();
        this.userEmailTextView = findViewById(R.id.signOutActivity_userEmailTextView);
        this.acceptAnnounceCheckBox = findViewById(R.id.signOutActivity_acceptAnnounceCheckBox);
        this.signOutButton = findViewById(R.id.signOutActivity_signOutButton);
        this.cancelButton = findViewById(R.id.signOutActivity_cancelButton);
        this.passwordEditText = findViewById(R.id.signOutActivity_passwordEditText);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        this.signOutButton.setEnabled(false);
        this.acceptAnnounceCheckBox.setEnabled(false);
        this.passwordEditText.setOnKeyListener((v, keyCode, event) -> {
            acceptAnnounceCheckBox.setEnabled((passwordEditText.length() >= 7));
            return false;
        });

        this.signOutButton.setOnClickListener(v -> this.signOut());
        this.acceptAnnounceCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> this.signOutButton.setEnabled(isChecked));
        this.cancelButton.setOnClickListener(v -> this.onBackPressed());
    }

    @Override
    protected boolean isLoginNeeded() {
        return this.loginNeed;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        this.userEmailTextView.setText(this.dataset.get(KatiEntity.EKatiData.EMAIL));
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    /**
     * callback
     */
    private class SignOutRequestCallback extends SimpleLoginRetrofitCallBack<WithdrawResponse> {
        public SignOutRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<WithdrawResponse> response) {
            removeLoginData();
            showDialog(SIGN_OUT_SUCCESS_DIALOG_TITLE, SIGN_OUT_SUCCESS_DIALOG_MESSAGE, (dialog, which) -> startActivity(MainActivity.class));
        }
    }

    /**
     * Method
     */
    private void signOut() {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setPassword(this.passwordEditText.getText().toString());
        String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).withdraw(withdrawRequest).enqueue(JSHRetrofitTool.getCallback(new SignOutRequestCallback(this)));
    }

    private void removeLoginData() {
        this.loginNeed = false;
        this.dataset.put(KatiEntity.EKatiData.EMAIL, KatiEntity.EKatiData.NULL.name());
        this.dataset.put(KatiEntity.EKatiData.PASSWORD, KatiEntity.EKatiData.NULL.name());
        this.dataset.put(KatiEntity.EKatiData.NAME, KatiEntity.EKatiData.NULL.name());
        this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN, KatiEntity.EKatiData.FALSE.name());
        this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, KatiEntity.EKatiData.NULL.name());
        this.save();
    }


}