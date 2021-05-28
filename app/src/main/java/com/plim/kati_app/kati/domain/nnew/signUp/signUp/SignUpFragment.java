package com.plim.kati_app.kati.domain.nnew.signUp.signUp;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.old.login.signIn.model.SignUpRequest;
import com.plim.kati_app.kati.domain.old.login.signIn.model.SignUpResponse;
import com.plim.kati_app.kati.domain.old.login.signIn.view.RegisterNameFragmentEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Response;


public class SignUpFragment extends KatiViewModelFragment {

    private LoadingDialog loadingDialog;
    private EditText emailEditText, passwordEditText, nickNameEditText;
    private Button submitButton;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    protected void associateView(View view) {
        this.emailEditText = view.findViewById(R.id.signUpFragment_emailEditText);
        this.passwordEditText = view.findViewById(R.id.signUpFragment_passwordEditText);
        this.nickNameEditText = view.findViewById(R.id.signUpFragment_nickNameEditText);

        this.submitButton = view.findViewById(R.id.signUpFragment_submitButton);
    }

    @Override
    protected void initializeView() {
        this.submitButton.setOnClickListener(v -> this.signUp());
    }

    @Override
    protected void katiEntityUpdated() {

    }

    private class SignUpRequestCallback extends SimpleRetrofitCallBackImpl<SignUpResponse> {
        public SignUpRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<SignUpResponse> response) {
            showDialog(
                    "어서 오세요 " + nickNameEditText.getText().toString() + " 님!",
                    "회원 가입에 성공하였습니다.",
                    (dialog, which) -> Navigation.findNavController(getView()).navigate(R.id.action_register3Fragment_to_registerFinishedFragment)
            );
        }

        @Override
        public void onResponse(Response<SignUpResponse> response) {
            super.onResponse(response);
            loadingDialog.hide();
        }

        @Override
        public void onConnectionFail(Throwable t) {
            super.onConnectionFail(t);
            loadingDialog.hide();
        }
    }


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