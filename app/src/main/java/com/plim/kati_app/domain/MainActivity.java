package com.plim.kati_app.domain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.user.login.LoginActivity;
import com.plim.kati_app.domain.user.register.RegisterActivity;
import com.plim.kati_app.domain.user.signOut.TempActivity;

public class MainActivity extends AppCompatActivity { // a

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.registerTest).setOnClickListener(v-> this.startActivity(new Intent(this, RegisterActivity.class)));
        this.findViewById(R.id.signOutTest).setOnClickListener(v-> this.startActivity(new Intent(this, TempActivity.class)));
        this.findViewById(R.id.loginTest).setOnClickListener(v -> this.startActivity(new Intent(this, LoginActivity.class)));
    }
}