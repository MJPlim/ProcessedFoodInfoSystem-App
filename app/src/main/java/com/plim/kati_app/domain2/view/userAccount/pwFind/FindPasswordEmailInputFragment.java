package com.plim.kati_app.domain2.view.userAccount.pwFind;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.constant.Constant;
import com.plim.kati_app.retrofit.dto.FindPasswordRequest;
import com.plim.kati_app.retrofit.dto.FindPasswordResponse;
import com.plim.kati_app.retrofit.RestAPI;
import com.plim.kati_app.domain2.model.KatiEntity;
import com.plim.kati_app.domain2.view.TempMainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.domain.constant.Constant_yun.DIALOG_CONFIRM;
import static com.plim.kati_app.domain.constant.Constant_yun.EMAIL_INPUT_HINT;
import static com.plim.kati_app.domain.constant.Constant_yun.EMAIL_INPUT_MESSAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.LOGINED_DIALOG_TITLE;
import static com.plim.kati_app.domain.constant.Constant_yun.NO_USER_DIALOG_MESSAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.NO_USER_DIALOG_TITLE;

public class FindPasswordEmailInputFragment extends AbstractFragment1 {

    private LoadingDialog loadingDialog;

    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText(EMAIL_INPUT_MESSAGE);
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(EMAIL_INPUT_HINT);
        this.button.setText(DIALOG_CONFIRM);
    }

    @Override
    protected void katiEntityUpdated() {
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){
            showLoginedDialog();
        }
    }

    private void showLoginedDialog() {
        KatiDialog.simpleAlertDialog(this.getContext(),
            LOGINED_DIALOG_TITLE,
            LOGINED_DIALOG_TITLE,
            (dialog, which) -> this.startActivity(TempMainActivity.class),
            getResources().getColor(R.color.kati_coral, getContext().getTheme())
        ).showDialog();
    }

    @Override
    protected void buttonClicked() {
        if(this.loadingDialog==null)    this.loadingDialog=new LoadingDialog(this.getContext());
        Thread thread = new Thread(() -> {
            getActivity().runOnUiThread(()->{loadingDialog.show();});

            // THIS IS TEST! Get Data From View Model
            FindPasswordRequest request= new FindPasswordRequest();
            request.setEmail(this.editText.getText().toString());

            String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

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
        KatiDialog.simpleAlertDialog(this.getContext(),
            NO_USER_DIALOG_TITLE,
            NO_USER_DIALOG_MESSAGE,
            null,
            getResources().getColor(R.color.kati_coral, getContext().getTheme())
        ).showDialog();
    }

    private void showCompletedDialog() {
        this.navigateTo(R.id.action_findPasswordEmailInputFragment_to_findPasswordResultFragment);
    }
}