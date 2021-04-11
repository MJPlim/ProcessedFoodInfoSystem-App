package com.plim.kati_app.domain.view.user.signOut;

import android.content.Intent;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.Password;
import com.plim.kati_app.domain.model.RegisterActivityViewModel;
import com.plim.kati_app.domain.model.WithdrawResponse;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.tech.RestAPIClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Withdrawal1Fragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.mainTextView.setText("비밀번호를 입력해주세요");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint("비밀번호");
        this.button.setText("확인");

    }

    @Override
    protected void buttonClicked() {
        this.signOut();
    }

    private void signOut() {
        Thread thread = new Thread(() -> {
            // THIS IS TEST! Get Data From View Model
            Password password = new Password();
            password.setPassword(this.editText.getText().toString());

            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

            Call<WithdrawResponse> call = RestAPIClient.getApiService2(token).withdraw(password);
            call.enqueue(new Callback<WithdrawResponse>() {
                @Override
                public void onResponse(Call<WithdrawResponse> call, Response<WithdrawResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d("TEST123", response.code() + "");
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d("TEST123", response.code() + "Success");
                        getActivity().runOnUiThread(() ->showSignOutCompleteDialog());
                       new Thread(()-> database.katiDataDao().delete("Authorization")).start();
                    }
                }

                @Override
                public void onFailure(Call<WithdrawResponse> call, Throwable t) {
                    Log.d("회원탈퇴 실패! : 인터넷 연결을 확인해 주세요", t.getMessage());
                }
            });
        });
        thread.start();
    }

    /**
     * show Sign Out Complete Dialog
     */
    private void showSignOutCompleteDialog() {
        KatiDialog signOutCompleteDialog = new KatiDialog(getContext());
        signOutCompleteDialog.setTitle("회원 탈퇴가 완료되었습니다.");
        signOutCompleteDialog.setPositiveButton("확인", (dialog, which) -> {
            RegisterActivityViewModel registerActivityViewModel = new ViewModelProvider(this.requireActivity()).get(RegisterActivityViewModel.class);
            registerActivityViewModel.getUser().setPassword(this.editText.getText().toString());
            Navigation.findNavController(this.getView()).navigate(R.id.action_withdrawalActivity_withdrawalfragment1_to_withdrawalActivity_withdrawalfragment2);
        });
        signOutCompleteDialog.setColor(this.getResources().getColor(R.color.kati_coral, getContext().getTheme()));
        signOutCompleteDialog.showDialog();
    }
}