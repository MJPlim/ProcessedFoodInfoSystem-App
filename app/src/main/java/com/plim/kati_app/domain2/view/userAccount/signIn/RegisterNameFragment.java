package com.plim.kati_app.domain2.view.userAccount.signIn;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.retrofit.dto.SignUpResponse;
import com.plim.kati_app.retrofit.dto.User;
import com.plim.kati_app.retrofit.RestAPIClient;
import com.plim.kati_app.domain2.model.KatiEntity;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNameFragment extends AbstractFragment1 {

    private LoadingDialog loadingDialog;

    @Override
    protected void initializeView() {
        super.initializeView();
        this.loadingDialog= new LoadingDialog(this.getContext());
        this.mainTextView.setText(getString(R.string.register_name_maintext));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(getString(R.string.register_name_hint));
        this.button.setText(getString(R.string.button_ok));
    }

    @Override
    protected void buttonClicked() {
        this.loadingDialog.show();
        this.dataset.put(KatiEntity.EKatiData.NAME, this.editText.getText().toString());
        this.signUp();
    }
    private void signUp() {
        User user = new User();
        user.setEmail(this.dataset.get(KatiEntity.EKatiData.EMAIL));
        user.setPassword(this.dataset.get(KatiEntity.EKatiData.PASSWORD));
        user.setName(this.dataset.get(KatiEntity.EKatiData.NAME));
        Call<SignUpResponse> call = RestAPIClient.getApiService().signUp(user);
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