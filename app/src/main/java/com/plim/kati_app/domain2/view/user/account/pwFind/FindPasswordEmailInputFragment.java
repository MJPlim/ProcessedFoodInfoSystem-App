package com.plim.kati_app.domain2.view.user.account.pwFind;

import android.content.Intent;
import android.view.View;

import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.constant.Constant;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.FindPasswordRequest;
import com.plim.kati_app.domain.model.FindPasswordResponse;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain2.view.TempMainActivity;
import com.plim.kati_app.domain.tech.RestAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.domain.constant.Constant_yun.DIALOG_CONFIRM;
import static com.plim.kati_app.domain.constant.Constant_yun.EMAIL_INPUT_HINT;
import static com.plim.kati_app.domain.constant.Constant_yun.EMAIL_INPUT_MESSAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.FIND_USER_PASSWORD_DIALOG_MESSAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.FIND_USER_PASSWORD_DIALOG_TITLE;
import static com.plim.kati_app.domain.constant.Constant_yun.LOGINED_DIALOG_TITLE;
import static com.plim.kati_app.domain.constant.Constant_yun.NO_USER_DIALOG_MESSAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.NO_USER_DIALOG_TITLE;

public class FindPasswordEmailInputFragment extends AbstractFragment1 {

    private LoadingDialog loadingDialog;

    @Override
    protected void initializeView() {
        this.mainTextView.setText(EMAIL_INPUT_MESSAGE);
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(EMAIL_INPUT_HINT);
        this.button.setText(DIALOG_CONFIRM);
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
                LOGINED_DIALOG_TITLE,
                LOGINED_DIALOG_TITLE,
                (dialog, which) -> { Intent intent = new Intent(getActivity(), TempMainActivity.class);startActivity(intent);
                }, getResources().getColor(R.color.kati_coral, getContext().getTheme())).showDialog();
    }

    @Override
    protected void buttonClicked() {
        if(this.loadingDialog==null)    this.loadingDialog=new LoadingDialog(this.getContext());
        Thread thread = new Thread(() -> {
            getActivity().runOnUiThread(()->{loadingDialog.show();});

            // THIS IS TEST! Get Data From View Model
            FindPasswordRequest request= new FindPasswordRequest();
            request.setEmail(this.editText.getText().toString());

            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constant.URL)
                    .build();
            RestAPI service = retrofit.create(RestAPI.class);
            Call<FindPasswordResponse> listCall=service.findPassword(request);
            listCall.enqueue(new Callback<FindPasswordResponse>() {
                @Override
                public void onResponse(Call<FindPasswordResponse> call, Response<FindPasswordResponse> response) {
                    getActivity().runOnUiThread(()->{loadingDialog.hide();});
                    if (!response.isSuccessful()) {

                            showNoUserDialog();
                    } else {
                        FindPasswordResponse result = response.body();
//                        Log.d("디버그","연결 성공"+result.getEmail());
                        getActivity().runOnUiThread(() ->showCompletedDialog());
                    }
                }

                @Override
                public void onFailure(Call<FindPasswordResponse> call, Throwable t) {
                    getActivity().runOnUiThread(()->{loadingDialog.hide();});
//                    Log.d(getStringOfId(R.string.withdrawalPasswordInputFragment_log_pleaseCheckInternet), t.getMessage());

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
        signOutAskDialog.setTitle(FIND_USER_PASSWORD_DIALOG_TITLE);
        signOutAskDialog.setMessage(FIND_USER_PASSWORD_DIALOG_MESSAGE);
        signOutAskDialog.setPositiveButton(DIALOG_CONFIRM, (dialog, which) -> Navigation.findNavController(this.getView()).navigate(R.id.action_findPasswordEmailInputFragment_to_findPasswordResultFragment));
        signOutAskDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getActivity().getTheme()));
        signOutAskDialog.showDialog();
    }
}