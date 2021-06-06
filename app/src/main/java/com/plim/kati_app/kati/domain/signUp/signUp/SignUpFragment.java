package com.plim.kati_app.kati.domain.signUp.signUp;

import android.app.Activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiHasTitleFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.signUp.model.SignUpRequest;
import com.plim.kati_app.kati.domain.signUp.model.SignUpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_EMAIL_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_ERROR_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_NAME_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_PASSWORD_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SIGN_UP_CONGRAT_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SIGN_UP_CONGRAT_TITLE_PREFIX;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SIGN_UP_CONGRAT_TITLE_SUFFIX;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SIGN_UP_TOOLBAR_TITLE_CONTENT;


public class SignUpFragment extends KatiHasTitleFragment {

    //associate
    //view
    private LoadingDialog loadingDialog;
    private EditText emailEditText, passwordEditText, nickNameEditText;
    private Button submitButton;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    protected void associateView(View view) {
        super.associateView(view);
        this.emailEditText = view.findViewById(R.id.signUpFragment_emailEditText);
        this.passwordEditText = view.findViewById(R.id.signUpFragment_passwordEditText);
        this.nickNameEditText = view.findViewById(R.id.signUpFragment_nickNameEditText);

        this.submitButton = view.findViewById(R.id.signUpFragment_submitButton);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        this.submitButton.setOnClickListener(v -> this.signUp());
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    @Override
    protected String getTitleContent() {
        return SIGN_UP_TOOLBAR_TITLE_CONTENT;
    }

    /**
     * callback
     */
    private class SignUpRequestCallback extends SimpleRetrofitCallBackImpl<SignUpResponse> {
        public SignUpRequestCallback(Activity activity) {
            super(activity);
        }


        @Override
        protected String getFailMessage(JSONObject object) throws JSONException {

            if (object.optJSONObject(JSONOBJECT_ERROR_MESSAGE) != null) {
                JSONObject smallObject = object.getJSONObject(JSONOBJECT_ERROR_MESSAGE);

            return smallObject.has(JSONOBJECT_EMAIL_MESSAGE) ? smallObject.getString(JSONOBJECT_EMAIL_MESSAGE) :
                    smallObject.has(JSONOBJECT_PASSWORD_MESSAGE) ? smallObject.getString(JSONOBJECT_PASSWORD_MESSAGE) :
                            smallObject.has(JSONOBJECT_NAME_MESSAGE) ? smallObject.getString(JSONOBJECT_NAME_MESSAGE) :
                                    object.toString();
            }else{
                return object.has(JSONOBJECT_ERROR_MESSAGE) ? object.getString(JSONOBJECT_ERROR_MESSAGE) :
                        object.has(JSONOBJECT_MESSAGE) ? object.getString(JSONOBJECT_MESSAGE) :
                                object.toString();
            }
        }

        @Override
        public void onSuccessResponse(Response<SignUpResponse> response) {
            showDialog(
                    SIGN_UP_CONGRAT_TITLE_PREFIX +
                            nickNameEditText.getText().toString() +
                            SIGN_UP_CONGRAT_TITLE_SUFFIX,
                    SIGN_UP_CONGRAT_MESSAGE,
                    (dialog, which) -> getActivity().onBackPressed()
            );
        }

        @Override
        public void onResponse(Response<SignUpResponse> response) {
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
    private void signUp() {
        this.loadingDialog = new LoadingDialog(this.getContext());
        this.loadingDialog.show();

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail(this.emailEditText.getText().toString());
        signUpRequest.setPassword(this.passwordEditText.getText().toString());
        signUpRequest.setName(this.nickNameEditText.getText().toString());

        KatiRetrofitTool.getAPI().signUp(signUpRequest).enqueue(JSHRetrofitTool.getCallback(new SignUpRequestCallback(getActivity())));
    }
}