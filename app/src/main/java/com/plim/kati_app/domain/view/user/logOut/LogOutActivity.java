package com.plim.kati_app.domain.view.user.logOut;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.asset.KatiDialog;

public class LogOutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("디버그","다이얼로그 표시");
        KatiDialog katiDialog = new KatiDialog(this);
        katiDialog.setTitle("성공적으로 로그아웃하였습니다.");
        katiDialog.setPositiveButton("확인",(dialog,which)->{
           Intent intent = new Intent(LogOutActivity.this, MainActivity.class);
           startActivity(intent);
            KatiDatabase database = KatiDatabase.getAppDatabase(this);
            database.katiDataDao().delete("Authorization");
        });
        katiDialog.setColor(this.getResources().getColor(R.color.kati_coral,this.getTheme()));
        katiDialog.showDialog();
    }
}