package com.plim.kati_app.domain.view.user.logOut;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.view.user.login.GoogleLoginActivity;

/**
 * 로그아웃하는 액티비티.
 */
public class LogOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // 로그인 된 상태
            startActivity(new Intent(this, GoogleLoginActivity.class));
        }

        //저장되어있는 사용자 토큰 존재 여부로 로그인 여부를 확인
        Thread thread = new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(this);
            //존재하면 사용자 토큰을 버린다.
            if (database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION) != null) {
                database.katiDataDao().delete(KatiDatabase.AUTHORIZATION);
                database.katiDataDao().delete(KatiDatabase.EMAIL);
                database.katiDataDao().delete(KatiDatabase.PASSWORD);
                database.katiDataDao().insert(new KatiData(KatiDatabase.AUTO_LOGIN, KatiDatabase.FALSE));

                runOnUiThread(() -> showOkDialog());
                //존재하지 않으면 로그인 되어 있지 않다고 알린다.
            } else {
                runOnUiThread(() -> showNoDialog());
            }
        });
        thread.start();
    }

    /**
     * 로그인 후 확인 다이얼로그.
     */
    public void showOkDialog() {
        KatiDialog.simpleAlertDialog(this,
                Constant_yun.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_TITLE,
                Constant_yun.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_MESSAGE,
                (dialog, which) -> startActivity(new Intent(LogOutActivity.this, MainActivity.class)),
                this.getResources().getColor(R.color.kati_coral, this.getTheme())
        ).showDialog();
    }

    /**
     * 로그인 되어 있지 않음 경고 다이얼로그
     */
    public void showNoDialog() {
        KatiDialog.simpleAlertDialog(this,
                Constant_yun.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_TITLE,
                Constant_yun.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_MESSAGE,
                (dialog, which) -> {
                    Intent intent = new Intent(LogOutActivity.this, MainActivity.class);
                    startActivity(intent);
                },
                this.getResources().getColor(R.color.kati_coral, this.getTheme())
        ).showDialog();
    }
}