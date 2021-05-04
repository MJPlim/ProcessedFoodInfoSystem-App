package com.plim.kati_app.domain2.view.user.account.signOut;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain2.view.TempMainActivity;
import com.plim.kati_app.domain2.view.user.account.logout.LogOutActivity;
import com.plim.kati_app.jshCrossDomain.tech.JSHGoogleMap;

import static com.plim.kati_app.domain.constant.Constant_jung.ROOM_AUTHORIZATION_KEY;

public class TempActivity extends AppCompatActivity {

    // Associate
        // View
        private Button signOutButton, logOutButton, mapButton;

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        // Associate View
        this.signOutButton = this.findViewById(R.id.tempActivity_withdrawalButton);
        this.logOutButton= this.findViewById(R.id.tempActivity_logOutButton);
        this.mapButton=this.findViewById(R.id.tempActivity_mapButton);

        // Initialize View
        this.signOutButton.setOnClickListener(v->this.signOutPressed());
        this.logOutButton.setOnClickListener(v->this.logoutPressed());
        this.mapButton.setOnClickListener(v->this.mapPressed());
    }

    /**
     * Callback
     */
    private void signOutPressed(){
        this.checkSignOut();
    }
    private void logoutPressed(){
        this.startActivity(new Intent(this, LogOutActivity.class));
    }
    private void mapPressed(){
        JSHGoogleMap.openGoogleMapMyPositionAndSearch(this, "convenience store");
    }

    private void checkSignOut(){
        new Thread(()->{
            KatiDatabase database= KatiDatabase.getAppDatabase(this);
            if(database.katiDataDao().getValue(ROOM_AUTHORIZATION_KEY)!=null) {
                Intent intent = new Intent(TempActivity.this, NewWithdrawalActivity.class);
                startActivity(intent);
            }else{
                runOnUiThread(()-> showNotLoginDialog());
            }
        }).start();
    }

    private void showNotLoginDialog(){ // 로그인 안 되어 있으면 경고 띄우기
        String noLoginUser = this.getResources().getString(R.string.tempActivity_dialog_noLoginUser);
        KatiDialog.simpleAlertDialog(this,
                noLoginUser,
                noLoginUser,
                (dialog, which)->{
            Intent intent = new Intent(this, TempMainActivity.class);
            startActivity(intent);
        }, getResources().getColor(R.color.kati_coral,this.getTheme())).showDialog();
    }
}