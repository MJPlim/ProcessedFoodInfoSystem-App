package com.plim.kati_app.domain.view.user.setId;

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.ModifyPasswordResponse;
import com.plim.kati_app.domain.model.SetSecondEmailRequest;
import com.plim.kati_app.domain.model.SetSecondEmailResponse;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.tech.RestAPIClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.constants.Constant_yun.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;

public class SetSecondEmailStart extends AbstractFragment1 {
    KatiDatabase database = KatiDatabase.getAppDatabase(getContext());

    @Override
    protected void initializeView() {
        this.mainTextView.setText(getString(R.string.register_second_email_maintext));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(getString(R.string.register_email_hint));
        this.button.setText(getString(R.string.button_ok));
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
            SetSecondEmailRequest request = new SetSecondEmailRequest();
            request.setSecondEmail(this.editText.getText().toString());

        Thread thread = new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

            Call<SetSecondEmailResponse> listCall = RestAPIClient.getApiService2(token).setSecondEmail(request);

            listCall.enqueue(new Callback<SetSecondEmailResponse>() {
                @Override
                public void onResponse(Call<SetSecondEmailResponse> call, Response<SetSecondEmailResponse> response) {
                    if (!response.isSuccessful()) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        getActivity().runOnUiThread(() -> {
                            Navigation.findNavController(getView()).navigate(R.id.action_setSecondEmailStart_to_setSecondEmailResult);
                        });
                    }
                }

                @Override
                public void onFailure(Call<SetSecondEmailResponse> call, Throwable t) {
                    Log.d("Test", t.getMessage());
                }
            });
        });
        thread.start();
    }
}
