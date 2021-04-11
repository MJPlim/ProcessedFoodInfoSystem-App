package com.plim.kati_app.domain.view.user.signOut;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.Password;
import com.plim.kati_app.domain.model.RegisterActivityViewModel;
import com.plim.kati_app.domain.model.SignUpResponse;
import com.plim.kati_app.domain.model.WithdrawResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.tech.RestAPIClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TempActivity extends AppCompatActivity {

    // Associate
        // View
        private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        // Associate View
        this.signOutButton = this.findViewById(R.id.button);

        // Initialize View
        this.signOutButton.setOnClickListener(v->this.showSignOutAskDialog());
    }

    /**
     * show Sign Out Ask Dialog
     */
    private void showSignOutAskDialog(){
        KatiDialog signOutAskDialog = new KatiDialog(this);
        signOutAskDialog.setTitle("정말 탈퇴하시겠습니까?");
        signOutAskDialog.setMessage("회원을 탈퇴하시면 그동안 쌓여왔던 모든 자료와 데이터가 삭제됩니다.");
        signOutAskDialog.setPositiveButton("예", (dialog, which) -> this.signOut());
        signOutAskDialog.setNegativeButton("아니오", null);
        signOutAskDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getTheme()));
        signOutAskDialog.showDialog();
    }

    private void signOut() {
        Thread thread = new Thread(() -> {
            // THIS IS TEST! Get Data From View Model
            Password password = new Password();
            password.setPassword("asdf");

            KatiDatabase database = KatiDatabase.getAppDatabase(this);
            String token = database.katiDataDao().getValue("Authorization");

            Call<WithdrawResponse> call = RestAPIClient.getApiService2(token).withdraw(password);
            call.enqueue(new Callback<WithdrawResponse>() {
                @Override
                public void onResponse(Call<WithdrawResponse> call, Response<WithdrawResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d("TEST123", response.code() + "");
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(TempActivity.this, jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(TempActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d("TEST123", response.code() + "Success");
                        showSignOutCompleteDialog();
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
    private void showSignOutCompleteDialog(){
        KatiDialog signOutCompleteDialog = new KatiDialog(this);
        signOutCompleteDialog.setTitle("회원 탈퇴가 완료되었습니다.");
        signOutCompleteDialog.setPositiveButton("확인", (dialog, which) -> this.startActivity(new Intent(this, WithdrawalActivity.class)));
        signOutCompleteDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getTheme()));
        signOutCompleteDialog.showDialog();
    }
}