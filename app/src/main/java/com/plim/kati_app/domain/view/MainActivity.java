package com.plim.kati_app.domain.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.service.AutoLoginService;
import com.plim.kati_app.domain.view.rank.RankingActivity;
import com.plim.kati_app.domain.view.search.food.list.FoodSearchActivity;
import com.plim.kati_app.domain.view.user.dataChange.UserDataChangeActivity;
import com.plim.kati_app.domain.view.user.login.LoginActivity;
import com.plim.kati_app.domain.view.user.myPage.UserMyPageActivity;
import com.plim.kati_app.domain.view.user.signOut.TempActivity;

/**
 * 임시 메인 액티비티로, 다른 액티비티들로 가는 버튼들을 여러 개 가지고 있다.
 */
public class MainActivity extends AppCompatActivity { // a test

    //service intent
    private Intent intent;
private KatiDialog katiDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set view
        this.findViewById(R.id.mainActivity_tempButton).setOnClickListener(v -> this.startActivity(new Intent(this, TempActivity.class)));
        this.findViewById(R.id.mainActivity_loginTestButton).setOnClickListener(v -> this.startActivity(new Intent(this, LoginActivity.class)));
        this.findViewById(R.id.mainActivity_searchTestButton).setOnClickListener(v -> this.startActivity(new Intent(this, FoodSearchActivity.class)));
        this.findViewById(R.id.mainActivity_myPageTestButton).setOnClickListener(v -> this.startActivity(new Intent(this, UserMyPageActivity.class)));
        this.findViewById(R.id.mainActivity_userDataChangeTestButton).setOnClickListener(v->this.startActivity(new Intent(this, UserDataChangeActivity.class)));
        this.findViewById(R.id.mainActivity_RankingTestButton).setOnClickListener(v -> this.startActivity(new Intent(this, RankingActivity.class)));

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
        this.katiDialog = new KatiDialog(this);
        katiDialog.setTitle(Constant_yun.MAIN_ACTIVITY_FINISH_DIALOG_TITLE);
        katiDialog.setMessage(Constant_yun.MAIN_ACTIVITY_FINISH_DIALOG_MESSAGE);
        katiDialog.setPositiveButton(Constant_yun.KATI_DIALOG_CONFIRM, (dialog, which) -> this.finishApp());
        katiDialog.setNegativeButton(Constant_yun.KATI_DIALOG_CANCEL, null);
        katiDialog.setColor(this.getResources().getColor(R.color.kati_coral,this.getTheme()));
        katiDialog.showDialog();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        katiDialog.dismiss();
    }



    /**
     * 애플리케이션과 서비스를 종료한다.
     */
    public void finishApp(){
        this.finish();
        this.finishAffinity();
        KatiDatabase.destroyInstance();
        stopService(intent);
    }
}