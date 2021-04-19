package com.plim.kati_app.domain.view.user.register;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.model.RegisterActivityViewModel;
import com.plim.kati_app.domain.model.SignUpResponse;
import com.plim.kati_app.tech.RestAPIClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNameFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        this.mainTextView.setText("이름을 알려주세요");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint("당신의 닉네임을 알려주세요!");
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
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
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) { Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show(); }
                }else{ Navigation.findNavController(getView()).navigate(R.id.action_register3Fragment_to_registerFinishedFragment); }
            }
            @Override public void onFailure(Call<SignUpResponse> call, Throwable t) { Log.d("회원가입 실패! : 인터넷 연결을 확인해 주세요", t.getMessage()); }
        });
    }
}