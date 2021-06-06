package com.plim.kati_app.kati.domain.setRestoreEmail;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiHasTitleActivity;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.setRestoreEmail.model.SetSecondEmailRequest;
import com.plim.kati_app.kati.domain.setRestoreEmail.model.SetSecondEmailResponse;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_ERROR_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_SECOND_EMAIL_MESSAGE;


public class SetRestoreEmailActivity extends KatiHasTitleActivity {

    Button setRestoreEmailAddressButton;
    EditText editTextRestoreEmail;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_restore_email;
    }

    @Override
    protected void associateView() {
        super.associateView();
        this.setRestoreEmailAddressButton = findViewById(R.id.signUpFragment_submitButton);
        this.editTextRestoreEmail = findViewById(R.id.restoreEmailActivity_restoreEmailEditText);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        if(this.setRestoreEmailAddressButton!=null)
        this.setRestoreEmailAddressButton.setOnClickListener(v->this.setSecondEmail());
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
    private class SetSecondEmailRequestCallback extends SimpleLoginRetrofitCallBack<SetSecondEmailResponse> {
        public SetSecondEmailRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<SetSecondEmailResponse> response) {
            onBackPressed();
        }

        @Override
        protected String getFailMessage(JSONObject object) throws JSONException {
            if(object.optJSONObject(JSONOBJECT_ERROR_MESSAGE)!=null){
                return object.optJSONObject(JSONOBJECT_ERROR_MESSAGE).getString(JSONOBJECT_SECOND_EMAIL_MESSAGE);
            }
            return super.getFailMessage(object);
        }

    }


    /**
     * method
     */
    private void setSecondEmail(){
        SetSecondEmailRequest request = new SetSecondEmailRequest();
        request.setSecondEmail(this.editTextRestoreEmail.getText().toString());
        String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).setSecondEmail(request).enqueue(JSHRetrofitTool.getCallback(new SetSecondEmailRequestCallback(this)));
    }

}