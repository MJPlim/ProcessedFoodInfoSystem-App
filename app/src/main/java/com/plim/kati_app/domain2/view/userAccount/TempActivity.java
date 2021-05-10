package com.plim.kati_app.domain2.view.userAccount;

import android.os.Bundle;
import android.widget.Button;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain2.model.KatiEntity;
import com.plim.kati_app.domain2.view.KatiViewModelActivity;
import com.plim.kati_app.domain2.view.TempMainActivity;
import com.plim.kati_app.domain2.view.userAccount.logout.LogOutActivity;
import com.plim.kati_app.domain2.view.userAccount.signOut.SignOutActivity;
import com.plim.kati_app.jshCrossDomain.tech.JSHGoogleMap;

public class TempActivity extends KatiViewModelActivity {

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
    }
    @Override
    protected void associateView() {
        this.signOutButton = this.findViewById(R.id.tempActivity_withdrawalButton);
        this.logOutButton= this.findViewById(R.id.tempActivity_logOutButton);
        this.mapButton=this.findViewById(R.id.tempActivity_mapButton);
    }
    @Override
    protected void initializeView() {
        this.signOutButton.setOnClickListener(v->this.signOutPressed());
        this.logOutButton.setOnClickListener(v->this.logoutPressed());
        this.mapButton.setOnClickListener(v->this.mapPressed());
    }
    @Override
    public void katiEntityUpdated() {
    }

    /**
     * Callback
     */
    private void signOutPressed(){
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){ this.startActivity(SignOutActivity.class); }
        else {this.showNotLoginDialog();}
    }
    private void logoutPressed(){ this.startActivity(LogOutActivity.class); }
    private void mapPressed(){ JSHGoogleMap.openGoogleMapMyPositionAndSearch(this, "convenience store"); }

    private void showNotLoginDialog(){ // 로그인 안 되어 있으면 경고 띄우기
        KatiDialog.simpleAlertDialog(this,
            this.getResources().getString(R.string.tempActivity_dialog_noLoginUser),
            this.getResources().getString(R.string.tempActivity_dialog_noLoginUser),
            (dialog, which)->this.startActivity(TempMainActivity.class),
            this.getResources().getColor(R.color.kati_coral, this.getTheme())
        ).showDialog();
    }
}