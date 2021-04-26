package com.plim.kati_app.domain.view.user.findPW;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.FindPasswordRequest;
import com.plim.kati_app.domain.model.FindPasswordResponse;
import com.plim.kati_app.domain.model.FoodResponse;
import com.plim.kati_app.domain.model.Password;
import com.plim.kati_app.domain.model.WithdrawResponse;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.tech.RestAPI;
import com.plim.kati_app.tech.RestAPIClient;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.constants.Constant_park.JSONOBJECT_ERROR_MESSAGE;
import static com.plim.kati_app.constants.Constant_park.ROOM_AUTHORIZATION_KEY;

public class FindPasswordEmailInputFragment extends AbstractFragment1 {

    private LoadingDialog loadingDialog;

    @Override
    protected void initializeView() {
        this.mainTextView.setText("이메일을 입력해주세요");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint("example@plim.com");
        this.button.setText("확인");
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
                "이미 로그인 된 상태입니다.",
                "이미 로그인 된 상태입니다.",
                (dialog, which) -> { Intent intent = new Intent(getActivity(), MainActivity.class);startActivity(intent);
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
                        Log.d("디버그","연결 성공"+result.getEmail());
                        getActivity().runOnUiThread(() ->showCompletedDialog());
                    }
                }

                @Override
                public void onFailure(Call<FindPasswordResponse> call, Throwable t) {
                    getActivity().runOnUiThread(()->{loadingDialog.hide();});
                    Log.d(getStringOfId(R.string.withdrawalPasswordInputFragment_log_pleaseCheckInternet), t.getMessage());

                }
            });
        });
        thread.start();
    }

    private void showNoUserDialog() {
        KatiDialog signOutAskDialog = new KatiDialog(this.getContext());
        signOutAskDialog.setTitle("해당하는 유저가 없습니다.");
        signOutAskDialog.setMessage("잘못 입력하였거나 해당하는 유저를 찾을 수 없습니다.");
        signOutAskDialog.setPositiveButton("확인", null);
        signOutAskDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getActivity().getTheme()));
        signOutAskDialog.showDialog();
    }

    private void showCompletedDialog() {
        KatiDialog signOutAskDialog = new KatiDialog(this.getContext());
        signOutAskDialog.setTitle("임시 비밀번호가 발급되었습니다.");
        signOutAskDialog.setMessage("메일함에서 임시 비밀번호 메일을 확인해 주세요.");
        signOutAskDialog.setPositiveButton("예", (dialog, which) -> Navigation.findNavController(this.getView()).navigate(R.id.action_findPasswordEmailInputFragment_to_findPasswordResultFragment));
        signOutAskDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getActivity().getTheme()));
        signOutAskDialog.showDialog();
    }
}