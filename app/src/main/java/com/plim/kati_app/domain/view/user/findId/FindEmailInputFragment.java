package com.plim.kati_app.domain.view.user.findId;

import android.content.Intent;
import android.util.Log;

import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.FindEmailRequest;
import com.plim.kati_app.domain.model.FindEmailResponse;
import com.plim.kati_app.domain.model.FindPasswordResponse;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.tech.RestAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.constants.Constant_yun.DIALOG_CONFIRM;
import static com.plim.kati_app.constants.Constant_yun.FIND_USER_PASSWORD_DIALOG_MESSAGE;
import static com.plim.kati_app.constants.Constant_yun.FIND_USER_PASSWORD_DIALOG_TITLE;
import static com.plim.kati_app.constants.Constant_yun.NO_USER_DIALOG_MESSAGE;
import static com.plim.kati_app.constants.Constant_yun.NO_USER_DIALOG_TITLE;

public class FindEmailInputFragment extends AbstractFragment1 {

    private LoadingDialog loadingDialog;

    @Override
    protected void initializeView() {
        this.mainTextView.setText(getString(R.string.find_id_maintext));
        this.subTextView.setText(getString(R.string.find_id_subtext));
        this.editText.setHint(getString(R.string.register_email_hint));
        this.button.setText(getString(R.string.button_ok));
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
            if (database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION) != null) {
                getActivity().runOnUiThread(() -> showNotLoginedDialog());
            }
        }).start();
    }

    private void showNotLoginedDialog() {
        KatiDialog.simpleAlertDialog(getContext(),
                getString(R.string.login_already_signed_dialog),
                getString(R.string.login_already_signed_content_dialog),
                (dialog, which) -> { Intent intent = new Intent(getActivity(), MainActivity.class);startActivity(intent);
                }, getResources().getColor(R.color.kati_coral, getContext().getTheme())).showDialog();
    }

    @Override
    protected void buttonClicked() {
        if(this.loadingDialog==null)    this.loadingDialog=new LoadingDialog(this.getContext());
        Thread thread = new Thread(() -> {
            getActivity().runOnUiThread(()->{loadingDialog.show();});

            FindEmailRequest request= new FindEmailRequest();
            request.setSecondEmail(this.editText.getText().toString());

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constant.URL)
                    .build();
            RestAPI service = retrofit.create(RestAPI.class);
            Call<FindEmailResponse> listCall=service.findEmail(request);
            listCall.enqueue(new Callback<FindEmailResponse>() {
                @Override
                public void onResponse(Call<FindEmailResponse> call, Response<FindEmailResponse> response) {
                    getActivity().runOnUiThread(()->{loadingDialog.hide();});
                    if (!response.isSuccessful()) {
                            showNoUserDialog();
                        Log.d("Test", response.message());
                        System.out.println("으아악!" + response.message() + response.code());
                    } else {
                        getActivity().runOnUiThread(() ->showCompletedDialog());
                    }
                }

                @Override
                public void onFailure(Call<FindEmailResponse> call, Throwable t) {
                    getActivity().runOnUiThread(()->{loadingDialog.hide();});
                }
            });
        });
        thread.start();
    }

    private void showNoUserDialog() {
        KatiDialog signOutAskDialog = new KatiDialog(this.getContext());
        signOutAskDialog.setTitle(NO_USER_DIALOG_TITLE);
        signOutAskDialog.setMessage(NO_USER_DIALOG_MESSAGE);
        signOutAskDialog.setPositiveButton(DIALOG_CONFIRM, null);
        signOutAskDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getActivity().getTheme()));
        signOutAskDialog.showDialog();
    }

    private void showCompletedDialog() {
        KatiDialog signOutAskDialog = new KatiDialog(this.getContext());
        signOutAskDialog.setTitle(getString(R.string.find_user_email_dialog_title));
        signOutAskDialog.setMessage(getString(R.string.find_user_email_dialog_message));
        signOutAskDialog.setPositiveButton(getString(R.string.button_ok), (dialog, which) -> Navigation.findNavController(getView()).navigate(R.id.action_findEmailInputFragment_to_findEmailResultFragment));
        signOutAskDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getActivity().getTheme()));
        signOutAskDialog.showDialog();
    }
}