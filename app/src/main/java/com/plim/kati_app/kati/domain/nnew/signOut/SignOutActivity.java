package com.plim.kati_app.kati.domain.nnew.signOut;

import android.app.Activity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiLoginCheckViewModelActivity;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.main.MainActivity;
import com.plim.kati_app.kati.domain.nnew.signOut.model.WithdrawRequest;
import com.plim.kati_app.kati.domain.nnew.signOut.model.WithdrawResponse;

import retrofit2.Response;


public class SignOutActivity extends KatiLoginCheckViewModelActivity {


    private TextView userEmailTextView;
    private CheckBox acceptAnnounceCheckBox;
    private Button signOutButton, cancelButton;
    private EditText passwordEditText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_out;
    }

    @Override
    protected void associateView() {
        this.userEmailTextView = findViewById(R.id.signOutActivity_userEmailTextView);
        this.acceptAnnounceCheckBox = findViewById(R.id.signOutActivity_acceptAnnounceCheckBox);
        this.signOutButton = findViewById(R.id.signOutActivity_signOutButton);
        this.cancelButton = findViewById(R.id.signOutActivity_cancelButton);
        this.passwordEditText = findViewById(R.id.signOutActivity_passwordEditText);
    }

    @Override
    protected void initializeView() {
        this.signOutButton.setEnabled(false);
        this.acceptAnnounceCheckBox.setEnabled(false);
        this.passwordEditText.setOnKeyListener((v, keyCode, event) -> {
            acceptAnnounceCheckBox.setEnabled((passwordEditText.length() >= 7));
            return false;
        });

        this.signOutButton.setOnClickListener(v -> this.signOut());
        this.acceptAnnounceCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            this.signOutButton.setEnabled(isChecked);
        });
    }

    @Override
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        this.userEmailTextView.setText(this.dataset.get(KatiEntity.EKatiData.EMAIL));
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }


    private class SignOutRequestCallback extends SimpleLoginRetrofitCallBack<WithdrawResponse> {
        public SignOutRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<WithdrawResponse> response) {
            dataset.put(KatiEntity.EKatiData.AUTHORIZATION, KatiEntity.EKatiData.NULL.name());
            showDialog("회원 탈퇴 성공", "성공적으로 회원 탈퇴하였습니다.", (dialog, which) -> startActivity(MainActivity.class));
        }
    }

    /**
     * Method
     */
    private void signOut() {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setPassword(this.passwordEditText.getText().toString());
        String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
        Log.d("TEST123", "PW: " + this.passwordEditText.getText().toString());
        Log.d("TEST123", "AUTHORIZATION: " + token);
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).withdraw(withdrawRequest).enqueue(JSHRetrofitTool.getCallback(new SignOutRequestCallback(this)));
    }
}