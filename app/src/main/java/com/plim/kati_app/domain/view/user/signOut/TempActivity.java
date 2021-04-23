package com.plim.kati_app.domain.view.user.signOut;

import android.content.DialogInterface;
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
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.tech.RestAPIClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        this.signOutButton = this.findViewById(R.id.button);

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