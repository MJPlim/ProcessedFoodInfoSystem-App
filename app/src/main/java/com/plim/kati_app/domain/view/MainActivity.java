package com.plim.kati_app.domain.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.search.FoodSearchActivity;
import com.plim.kati_app.domain.view.user.detail.DetailActivity;
import com.plim.kati_app.domain.view.user.logOut.LogOutActivity;
import com.plim.kati_app.domain.view.user.login.LoginActivity;
import com.plim.kati_app.domain.view.user.login.LoginRequest;
import com.plim.kati_app.domain.view.user.login.RetrofitClient;
import com.plim.kati_app.domain.view.user.register.RegisterActivity;
import com.plim.kati_app.domain.view.user.signOut.TempActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity { // a test

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.registerTest).setOnClickListener(v -> this.startActivity(new Intent(this, RegisterActivity.class)));
        this.findViewById(R.id.signOutTest).setOnClickListener(v -> this.startActivity(new Intent(this, TempActivity.class)));
        this.findViewById(R.id.loginTest).setOnClickListener(v -> this.startActivity(new Intent(this, LoginActivity.class)));
        this.findViewById(R.id.searchTest).setOnClickListener(v -> this.startActivity(new Intent(this, FoodSearchActivity.class)));
        this.findViewById(R.id.logOutTest).setOnClickListener(v -> this.startActivity(new Intent(this, LogOutActivity.class)));
        this.findViewById(R.id.detailTest).setOnClickListener(v -> this.startActivity(new Intent(this, DetailActivity.class)));

        this.intent=new Intent(this,AutoLoginService.class);
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        this.finish();
        Log.d("디버그","끝냄");
        this.finishAffinity();
        stopService(intent);



    }

}