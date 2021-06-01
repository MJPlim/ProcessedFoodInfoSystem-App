package com.plim.kati_app.kati.domain.nnew.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.plim.kati_app.R;

import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;

import com.plim.kati_app.jshCrossDomain.tech.google.JSHGoogleMap;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;

import com.plim.kati_app.kati.domain.nnew.map.MapServiceActivity;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private KatiDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        if (this.findViewById(R.id.mainFragment_bottomNavigation) != null) {
            BottomNavigationView bottomNavigationView = this.findViewById(R.id.mainFragment_bottomNavigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                switch (item.getItemId()) {
                    case R.id.action_home:
                        navController.navigate(R.id.action_global_mainFragment);
                        break;
                    case R.id.action_favorite:
                        navController.navigate(R.id.action_global_favoriteFragment);
                        break;
                    case R.id.action_search:
                        navController.navigate(R.id.action_global_searchFragment);
                        break;
                    case R.id.action_find_market:
                        JSHGoogleMap.openGoogleMapMyPositionAndSearch(this, "Convenience");
                        break;
                    case R.id.action_mykati:
                        navController.navigate(R.id.action_global_myKatiFragment);
                        break;
                }
                return true;
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.dialog.dismiss();
    }
//
//    @Override
//    public void onBackPressed() {
//        this.showSystemOffCheckDialog();
//    }

    public void showSystemOffCheckDialog() {
        this.dialog = KatiDialog.simplerAlertDialog(this,
                Constant.MAIN_ACTIVITY_FINISH_DIALOG_TITLE, Constant.MAIN_ACTIVITY_FINISH_DIALOG_MESSAGE,
                (dialog, which) -> {
                    this.finish();
                    this.finishAffinity();
                }
        );
    }


}

