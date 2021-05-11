package com.plim.kati_app.domain.view.user.setId;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.model.SetSecondEmailRequest;
import com.plim.kati_app.domain.model.SetSecondEmailResponse;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.tech.RestAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetSecondEmailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_second_email);
    }
}
