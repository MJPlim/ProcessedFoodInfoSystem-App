package com.plim.kati_app.domain2.view.userAccount.signOut;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.retrofit.dto.Password;
import com.plim.kati_app.retrofit.dto.WithdrawResponse;
import com.plim.kati_app.retrofit.RestAPIClient;
import com.plim.kati_app.domain2.model.KatiEntity;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.domain.constant.Constant_jung.JSONOBJECT_ERROR_MESSAGE;

public class SignOutPasswordInputFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() { // 뷰 초기화
        super.initializeView();
        this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.mainTextView.setText(this.getStringOfId(R.string.withdrawalPasswordInputFragment_mainTextView_inputPassword));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(this.getStringOfId(R.string.withdrawalPasswordInputFragment_editText_password));
        this.button.setText(this.getStringOfId(R.string.common_ok));
    }

    @Override
    protected void buttonClicked() {
        this.signOut();
    } // 버튼 눌리면 사인 아웃 실행

    private void signOut() { // 서버로 사인 아웃 요청
        Thread thread = new Thread(() -> {
            // THIS IS TEST! Get Data From View Model
            Password password = new Password();
            password.setPassword(this.editText.getText().toString());

            String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

            Call<WithdrawResponse> call = RestAPIClient.getApiService2(token).withdraw(password);
            call.enqueue(new Callback<WithdrawResponse>() {
                @Override
                public void onResponse(Call<WithdrawResponse> call, Response<WithdrawResponse> response) {
                    if (!response.isSuccessful()) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getContext(), jObjError.getString(JSONOBJECT_ERROR_MESSAGE), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        getActivity().runOnUiThread(() ->showSignOutCompleteDialog());
                        dataset.remove(KatiEntity.EKatiData.AUTHORIZATION);
                    }
                }

                @Override
                public void onFailure(Call<WithdrawResponse> call, Throwable t) {
                    Log.d(getStringOfId(R.string.withdrawalPasswordInputFragment_log_pleaseCheckInternet), t.getMessage());
                }
            });
        });
        thread.start();
    }

    /**
     * show Sign Out Complete Dialog
     */
    private void showSignOutCompleteDialog() {
        this.navigateTo(R.id.action_withdrawalActivity_withdrawalfragment1_to_withdrawalActivity_withdrawalfragment2);
    }
}