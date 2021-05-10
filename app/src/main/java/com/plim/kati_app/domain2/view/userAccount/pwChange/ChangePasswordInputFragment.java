package com.plim.kati_app.domain2.view.userAccount.pwChange;

import android.view.View;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment2;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.constant.Constant;
import com.plim.kati_app.retrofit.dto.ModifyPasswordRequest;
import com.plim.kati_app.retrofit.dto.ModifyPasswordResponse;
import com.plim.kati_app.retrofit.RestAPI;
import com.plim.kati_app.retrofit.RestAPIClient;
import com.plim.kati_app.domain2.model.KatiEntity;
import com.plim.kati_app.domain2.view.TempMainActivity;
import com.plim.kati_app.domain2.view.userAccount.logout.LogOutActivity;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.domain.constant.Constant_yun.AFTER_PASSWORD_HINT;
import static com.plim.kati_app.domain.constant.Constant_yun.AFTER_PASSWORD_HINT2;
import static com.plim.kati_app.domain.constant.Constant_yun.BEFORE_PASSWORD_HINT;
import static com.plim.kati_app.domain.constant.Constant_yun.CHANGE_PASSWORD_TITLE;
import static com.plim.kati_app.domain.constant.Constant_yun.COMPLETE_CHANGE_PASSWORD_MESSAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.COMPLETE_CHANGE_PASSWORD_TITLE;
import static com.plim.kati_app.domain.constant.Constant_yun.DIALOG_CONFIRM;
import static com.plim.kati_app.domain.constant.Constant_yun.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;

public class ChangePasswordInputFragment extends AbstractFragment2 {

    private LoadingDialog loadingDialog;

    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText(CHANGE_PASSWORD_TITLE);
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(BEFORE_PASSWORD_HINT);
        this.editText2.setHint(AFTER_PASSWORD_HINT);
        this.editText3.setHint(AFTER_PASSWORD_HINT2);
        this.button.setText(DIALOG_CONFIRM);
    }

    @Override
    protected void katiEntityUpdated() {
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){
            this.showNotLoginedDialog();
        }
    }

    private void showNotLoginedDialog() {
        KatiDialog.simpleAlertDialog(getContext(),
            LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
            LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
            (dialog, which) -> this.startActivity(TempMainActivity.class),
            getResources().getColor(R.color.kati_coral, getContext().getTheme())
        ).showDialog();
    }

    @Override
    protected void buttonClicked() {
        if (this.editText2.getText().toString().equals(this.editText3.getText().toString())) {
            if (this.loadingDialog == null)
                this.loadingDialog = new LoadingDialog(this.getContext());
            Thread thread = new Thread(() -> {
                getActivity().runOnUiThread(() -> {
                    loadingDialog.show();
                });


                // THIS IS TEST! Get Data From View Model
                ModifyPasswordRequest request = new ModifyPasswordRequest();
                request.setBeforePassword(this.editText.getText().toString());
                request.setAfterPassword(this.editText2.getText().toString());

                String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Constant.URL)
                        .build();
                RestAPI service = retrofit.create(RestAPI.class);
                Call<ModifyPasswordResponse> listCall = RestAPIClient.getApiService2(token).modifyPassword(request);
                listCall.enqueue(new Callback<ModifyPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ModifyPasswordResponse> call, Response<ModifyPasswordResponse> response) {
                        getActivity().runOnUiThread(() -> {
                            loadingDialog.hide();
                        });
                        ModifyPasswordResponse result = response.body();
                        if (!response.isSuccessful()) {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        } else {
                            getActivity().runOnUiThread(() -> {
                                showCompletedDialog();

                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<ModifyPasswordResponse> call, Throwable t) {
                        getActivity().runOnUiThread(() -> {
                            loadingDialog.hide();
                        });
//                    Log.d(getStringOfId(R.string.withdrawalPasswordInputFragment_log_pleaseCheckInternet), t.getMessage());
                    }
                });
            });
            thread.start();
        } else {
            Toast.makeText(getContext(), "새 비밀번호를 동일하게 입력해 주세요.", Toast.LENGTH_LONG).show();
        }
    }

    private void showCompletedDialog() {
        KatiDialog.simpleAlertDialog(getContext(),
                COMPLETE_CHANGE_PASSWORD_TITLE,
                COMPLETE_CHANGE_PASSWORD_MESSAGE,
                (dialog, which) -> this.startActivity(LogOutActivity.class),
                getResources().getColor(R.color.kati_coral, getContext().getTheme())
        ).showDialog();
    }

}