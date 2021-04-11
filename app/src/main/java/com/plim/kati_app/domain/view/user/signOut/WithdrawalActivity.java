package com.plim.kati_app.domain.view.user.signOut;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.plim.kati_app.R;

public class WithdrawalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.withdrawalActivity_frameLayout, new Withdrawal2Fragment()).commit();
    }
}