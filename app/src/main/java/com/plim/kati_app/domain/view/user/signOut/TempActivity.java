package com.plim.kati_app.domain.view.user.signOut;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;

import static com.plim.kati_app.constants.Constant_park.ROOM_AUTHORIZATION_KEY;

public class TempActivity extends AppCompatActivity {

    // Associate
        // View
        private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        // Associate View
        this.signOutButton = this.findViewById(R.id.tempActivity_withdrawalButton);

        // Initialize View
        this.signOutButton.setOnClickListener(v->{
            new Thread(()->{
                KatiDatabase database= KatiDatabase.getAppDatabase(this);
                if(database.katiDataDao().getValue(ROOM_AUTHORIZATION_KEY)!=null) {
                    Intent intent = new Intent(TempActivity.this, NewWithdrawalActivity.class);
                    startActivity(intent);
                }else{
                    runOnUiThread(()->showNotLoginedDialog());
                }
            }).start();
        });
    }

    private void showNotLoginedDialog(){ // 로그인 안 되어 있으면 경고 띄우기
        String noLoginUser = this.getResources().getString(R.string.tempActivity_dialog_noLoginUser);
        KatiDialog.simpleAlertDialog(this,
                noLoginUser,
                noLoginUser,
                (dialog, which)->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }, getResources().getColor(R.color.kati_coral,this.getTheme())).showDialog();
    }
}