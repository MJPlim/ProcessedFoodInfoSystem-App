package com.plim.kati_app.kati.domain.nnew.findPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiLoginCheckViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.nnew.main.MainActivity;
import com.plim.kati_app.kati.domain.old.TempMainActivity;
import com.plim.kati_app.kati.domain.old.login.pwFind.model.FindPasswordRequest;
import com.plim.kati_app.kati.domain.old.login.pwFind.model.FindPasswordResponse;
import com.plim.kati_app.kati.domain.old.login.pwFind.view.FindPasswordEmailInputFragmentEditText;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOGINED_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NO_USER_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NO_USER_DIALOG_TITLE;


public class FindPasswordActivity extends KatiViewModelActivity {

    private EditText restoreEmailEditText;
    private Button submitButton;
    private TextView resultMessageTextView;

    /**
     * Callback
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_find;
    }

    @Override
    protected void associateView() {
        this.restoreEmailEditText = findViewById(R.id.passwordFindActivity_restoreEmailEditText);
        this.submitButton = findViewById(R.id.passwordFindActivity_submitButton);
        this.resultMessageTextView = findViewById(R.id.passwordFindActivity_resultMessageTextView);
    }

    @Override
    protected void initializeView() {
        this.submitButton.setOnClickListener(v -> this.buttonClicked());

    }

    @Override
    public void katiEntityUpdated() {
        if (this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION).equals(KatiEntity.EKatiData.NULL.name())) {
            this.showLoginedDialog();
        }
    }


    private class FindPasswordRequestCallback extends SimpleRetrofitCallBackImpl<FindPasswordResponse> {
        public FindPasswordRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<FindPasswordResponse> response) {
            KatiDialog.simplerAlertDialog(
                    this.activity,
                    "임시 비밀번호를 발급하였습니다.",
                    "메일함을 확인하여 임시 비밀번호로 로그인 해 주세요",
                    (dialog, which) -> startActivity(MainActivity.class));
        }
    }

    /**
     * Method
     */
    private void showLoginedDialog() {
        KatiDialog.simplerAlertDialog(this,
                LOGINED_DIALOG_TITLE, LOGINED_DIALOG_TITLE,
                (dialog, which) -> this.startActivity(TempMainActivity.class)
        );
    }

    private void buttonClicked() {
        FindPasswordRequest request = new FindPasswordRequest();
        request.setEmail(this.restoreEmailEditText.getText().toString());
        KatiRetrofitTool.getAPI().findPassword(request).enqueue(JSHRetrofitTool.getCallback(new FindPasswordRequestCallback(this)));
    }

}