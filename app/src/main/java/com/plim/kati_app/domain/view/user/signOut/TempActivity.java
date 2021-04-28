package com.plim.kati_app.domain.view.user.signOut;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.view.user.logOut.LogOutActivity;

import static com.plim.kati_app.constants.Constant_jung.ROOM_AUTHORIZATION_KEY;

public class TempActivity extends AppCompatActivity {

    // Associate
        // View
        private Button signOutButton;

        private Button logOutButton;

        private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        // Associate View
        this.signOutButton = this.findViewById(R.id.tempActivity_withdrawalButton);
        this.logOutButton= this.findViewById(R.id.tempActivity_logOutButton);
        this.mapButton=this.findViewById(R.id.tempActivity_mapButton);

        // Initialize View
        this.signOutButton.setOnClickListener(v->{
           this.checkSignOut();
        });

        this.logOutButton.setOnClickListener(v->{
            startActivity(new Intent(TempActivity.this, LogOutActivity.class));
        });

        this.mapButton.setOnClickListener(v->{

            // Search for restaurants nearby
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=convenience_store");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

//            startActivity(new Intent(TempActivity.this, MapServiceActivity.class));
        });
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }, getResources().getColor(R.color.kati_coral,this.getTheme())).showDialog();
    }
}