package com.plim.kati_app.domain2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.constant.Constant_yun;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.service.AutoLoginService;
import com.plim.kati_app.domain2.search.food.list.FoodSearchActivity;
import com.plim.kati_app.domain2.search.food.detail.DetailActivity;
import com.plim.kati_app.domain2.user.account.pwChange.ChangePasswordActivity;
import com.plim.kati_app.domain2.user.account.login.view.LoginActivity;
import com.plim.kati_app.domain2.user.account.signOut.TempActivity;

import java.util.ArrayList;

/**
 * 임시 메인 액티비티로, 다른 액티비티들로 가는 버튼들을 여러 개 가지고 있다.
 */
public class MainActivity extends AppCompatActivity { // a test

    // TEST

    //service intent
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set view
        this.findViewById(R.id.mainActivity_tempButton).setOnClickListener(v -> this.startActivity(new Intent(this, TempActivity.class)));
        this.findViewById(R.id.mainActivity_loginTestButton).setOnClickListener(v -> this.startActivity(new Intent(this, LoginActivity.class)));
        this.findViewById(R.id.mainActivity_searchTestButton).setOnClickListener(v -> this.startActivity(new Intent(this, FoodSearchActivity.class)));
        this.findViewById(R.id.mainActivity_changePWButton).setOnClickListener(v -> this.startActivity(new Intent(this, ChangePasswordActivity.class)));
        this.findViewById(R.id.mainActivity_detailTestButton).setOnClickListener(v -> this.startActivity(new Intent(this, DetailActivity.class)));

        //start AutoLogin service
        this.intent=new Intent(this, AutoLoginService.class);
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.showDialog();
    }

    /**
     * 종료를 할 지 확인하는 다이얼로그를 표시한다.
     */
    public void showDialog(){
        KatiDialog katiDialog = new KatiDialog(this);
        katiDialog.setTitle(Constant_yun.MAIN_ACTIVITY_FINISH_DIALOG_TITLE);
        katiDialog.setMessage(Constant_yun.MAIN_ACTIVITY_FINISH_DIALOG_MESSAGE);
        katiDialog.setPositiveButton(Constant_yun.KATI_DIALOG_CONFIRM, (dialog, which) ->{
                this.finishApp();
            });
        katiDialog.setNegativeButton(Constant_yun.KATI_DIALOG_CANCEL, null);
        katiDialog.setColor(this.getResources().getColor(R.color.kati_coral,this.getTheme()));
        katiDialog.showDialog();
    }


    /**
     * 애플리케이션과 서비스를 종료한다.
     */
    public void finishApp(){
        this.finish();
        this.finishAffinity();
        stopService(intent);
    }
}