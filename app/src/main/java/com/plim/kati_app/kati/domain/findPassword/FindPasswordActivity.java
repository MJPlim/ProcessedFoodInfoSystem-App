package com.plim.kati_app.kati.domain.findPassword;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiHasTitleActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.nnew.main.MainActivity;
import com.plim.kati_app.kati.domain.findPassword.model.FindPasswordRequest;
import com.plim.kati_app.kati.domain.findPassword.model.FindPasswordResponse;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FIND_PASSWORD_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FIND_PASSWORD_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_EMAIL_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_ERROR_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOGINED_DIALOG_TITLE;


public class FindPasswordActivity extends KatiHasTitleActivity {

    //associate
    //view
    private EditText restoreEmailEditText;
    private Button submitButton;

    /**
     * Callback
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_find;
    }

    @Override
    protected void associateView() {
        super.associateView();
        this.restoreEmailEditText = findViewById(R.id.passwordFindActivity_restoreEmailEditText);
        this.submitButton = findViewById(R.id.passwordFindActivity_submitButton);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        this.submitButton.setOnClickListener(v -> this.buttonClicked());
    }

    @Override
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {}

    /**
     * callback
     */
    private class FindPasswordRequestCallback extends SimpleRetrofitCallBackImpl<FindPasswordResponse> {
        public FindPasswordRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        protected String getFailMessage(JSONObject object) throws JSONException {
            if (object.optJSONObject(JSONOBJECT_ERROR_MESSAGE) != null) {
                JSONObject miniObject = object.getJSONObject(JSONOBJECT_ERROR_MESSAGE);
                return miniObject.getString(JSONOBJECT_EMAIL_MESSAGE);
            }
            return super.getFailMessage(object);
        }

        @Override
        public void onSuccessResponse(Response<FindPasswordResponse> response) {
            showSuccessDialog(this.activity);
        }
    }

    /**
     * Method
     */
    private void showSuccessDialog(Activity activity) {
        this.dialogVector.add(KatiDialog.simplerAlertDialog(
                activity,
                FIND_PASSWORD_DIALOG_TITLE,
                FIND_PASSWORD_DIALOG_MESSAGE,
                (dialog, which) -> startActivity(MainActivity.class)));
    }

    private void showLoginedDialog() {
        this.dialogVector.add(KatiDialog.simplerAlertDialog(this,
                LOGINED_DIALOG_TITLE, LOGINED_DIALOG_TITLE,
                (dialog, which) -> this.startActivity(MainActivity.class)
        ));
    }

    private void buttonClicked() {
        FindPasswordRequest request = new FindPasswordRequest();
        request.setEmail(this.restoreEmailEditText.getText().toString());
        KatiRetrofitTool.getAPI().findPassword(request).enqueue(JSHRetrofitTool.getCallback(new FindPasswordRequestCallback(this)));
    }

}