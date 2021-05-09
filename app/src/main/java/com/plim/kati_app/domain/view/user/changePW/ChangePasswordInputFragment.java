package com.plim.kati_app.domain.view.user.changePW;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.domain.asset.AbstractFragment2;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.ModifyPasswordRequest;
import com.plim.kati_app.domain.model.ModifyPasswordResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.view.user.logOut.LogOutActivity;
import com.plim.kati_app.tech.RestAPI;
import com.plim.kati_app.tech.RestAPIClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.constants.Constant_yun.*;

public class ChangePasswordInputFragment extends AbstractFragment2 {

    private LoadingDialog loadingDialog;
    KatiDatabase database = KatiDatabase.getAppDatabase(getContext());

    @Override
    protected void initializeView() {
        this.mainTextView.setText(CHANGE_PASSWORD_TITLE);
        this.subTextView.setVisibility(View.INVISIBLE);
        this.Present_Password_editText.setHint(BEFORE_PASSWORD_HINT);
        this.New_Password_editText.setHint(AFTER_PASSWORD_HINT);
        this.New_Password_Verify_editText.setHint(AFTER_PASSWORD_VERIFY_HINT);
        this.button.setText(DIALOG_CONFIRM);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(() -> {

            if (database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION) == null) {
                getActivity().runOnUiThread(() -> showNotLoginedDialog());
            }
        }).start();

    }

    private void showNotLoginedDialog() {
        KatiDialog.simpleAlertDialog(getContext(),
                LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
                LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
                (dialog, which) -> {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }, getResources().getColor(R.color.kati_coral, getContext().getTheme())).showDialog();
    }

    @Override
    protected void buttonClicked() {
        if(this.New_Password_editText.getText().toString().equals(this.New_Password_Verify_editText.getText().toString())) {
            if (this.loadingDialog == null)
                this.loadingDialog = new LoadingDialog(this.getContext());
            Thread thread = new Thread(() -> {
                getActivity().runOnUiThread(() -> loadingDialog.show());


                // THIS IS TEST! Get Data From View Model
                ModifyPasswordRequest request = new ModifyPasswordRequest();
                request.setBeforePassword(this.Present_Password_editText.getText().toString());
                request.setAfterPassword(this.New_Password_editText.getText().toString());

                KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

                Call<ModifyPasswordResponse> listCall = RestAPIClient.getApiService2(token).ChangePassword(request);
                listCall.enqueue(new Callback<ModifyPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ModifyPasswordResponse> call, Response<ModifyPasswordResponse> response) {
                        getActivity().runOnUiThread(() -> {
                            loadingDialog.hide();
                        });
                        ModifyPasswordResponse result = response.body();
                        if (!response.isSuccessful()) {
                            KatiDialog.showRetrofitNotSuccessDialog(getContext(),response.code()+"",null).showDialog();
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
                        new Thread(() -> {
                            String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                            database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
                        }).start();
                    }

                    @Override
                    public void onFailure(Call<ModifyPasswordResponse> call, Throwable t) {
                        getActivity().runOnUiThread(() -> {
                            loadingDialog.hide();
                        });
//
                    }
                });
            });
            thread.start();
        }else{

            Toast.makeText(getContext(), PASSWORD_ISNOT_SAME, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.loadingDialog.dismiss();
    }

    private void moveToLogOutActivity() {
        Intent intent = new Intent(this.getActivity(), LogOutActivity.class);
        startActivity(intent);
    }


    private void showCompletedDialog() {
        KatiDialog signOutAskDialog = new KatiDialog(this.getContext());
        signOutAskDialog.setTitle(COMPLETE_CHANGE_PASSWORD_TITLE);
        signOutAskDialog.setMessage(COMPLETE_CHANGE_PASSWORD_MESSAGE);
        signOutAskDialog.setPositiveButton(DIALOG_CONFIRM, (dialog, which) ->  moveToLogOutActivity());
        signOutAskDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getActivity().getTheme()));
        signOutAskDialog.showDialog();
    }

}