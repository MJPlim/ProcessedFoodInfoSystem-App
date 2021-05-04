package com.plim.kati_app.domain2.view.user.account.signIn;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.RegisterActivityViewModel;
import com.plim.kati_app.domain.model.SignUpResponse;
import com.plim.kati_app.domain.tech.RestAPIClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNameFragment extends AbstractFragment1 {

    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.loadingDialog= new LoadingDialog(this.getContext());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeView() {
        this.mainTextView.setText(getString(R.string.register_name_maintext));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(getString(R.string.register_name_hint));
        this.button.setText(getString(R.string.button_ok));
    }

    @Override
    protected void buttonClicked() {
        this.loadingDialog.show();
        RegisterActivityViewModel registerActivityViewModel = new ViewModelProvider(this.requireActivity()).get(RegisterActivityViewModel.class);
        registerActivityViewModel.getUser().setName(this.editText.getText().toString());

        this.signUp();
    }
    private void signUp() {
        RegisterActivityViewModel registerActivityViewModel = new ViewModelProvider(this.requireActivity()).get(RegisterActivityViewModel.class);
        Call<SignUpResponse> call = RestAPIClient.getApiService().signUp(registerActivityViewModel.getUser());
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(!response.isSuccessful()){
                    try {
                        loadingDialog.hide();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) { Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show(); }
                }else{
                    loadingDialog.hide();
                    Navigation.findNavController(getView()).navigate(R.id.action_register3Fragment_to_registerFinishedFragment); }
            }
            @Override public void onFailure(Call<SignUpResponse> call, Throwable t) {
                loadingDialog.hide();
                Log.d("회원가입 실패! : 인터넷 연결을 확인해 주세요", t.getMessage()); }
        });
    }
}