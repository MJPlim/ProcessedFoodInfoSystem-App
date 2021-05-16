package com.plim.kati_app.kati.domain.login.signIn.view;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.AbstractFragment_1EditText;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.domain.login.signIn.model.SignUpResponse;
import com.plim.kati_app.kati.domain.login.signIn.model.SignUpRequest;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;

import org.json.JSONObject;

import retrofit2.Response;

public class RegisterNameFragmentEditText extends AbstractFragment_1EditText {

    // Component
        // View
        private LoadingDialog loadingDialog;

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText(getString(R.string.register_name_maintext));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(getString(R.string.register_name_hint));
        this.button.setText(getString(R.string.button_ok));
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() { this.signUp(); }
    private class SignUpRequestCallback implements JSHRetrofitCallback<SignUpResponse> {
        @Override
        public void onSuccessResponse(Response<SignUpResponse> response) {
            loadingDialog.hide();
            Navigation.findNavController(getView()).navigate(R.id.action_register3Fragment_to_registerFinishedFragment);
        }
        @Override
        public void onFailResponse(Response<SignUpResponse> response) {
            try {
                loadingDialog.hide();
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
            } catch (Exception e) { Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show(); }
        }
        @Override
        public void onConnectionFail(Throwable t) {
            loadingDialog.hide();
            Log.d("회원가입 실패! : 인터넷 연결을 확인해 주세요", t.getMessage());
        }
    }

    /**
     * Method
     */
    private void signUp() {
        this.dataset.put(KatiEntity.EKatiData.NAME, this.editText.getText().toString());
        this.loadingDialog= new LoadingDialog(this.getContext());
        this.loadingDialog.show();

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail(this.dataset.get(KatiEntity.EKatiData.EMAIL));
        signUpRequest.setPassword(this.dataset.get(KatiEntity.EKatiData.PASSWORD));
        signUpRequest.setName(this.dataset.get(KatiEntity.EKatiData.NAME));
        KatiRetrofitTool.getAPI().signUp(signUpRequest).enqueue(JSHRetrofitTool.getCallback(new SignUpRequestCallback()));
    }
}