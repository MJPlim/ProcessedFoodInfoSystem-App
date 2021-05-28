package com.plim.kati_app.kati.domain.old.temp;

import android.os.Bundle;
import android.widget.Button;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.domain.old.TempMainActivity;
import com.plim.kati_app.kati.domain.old.temp.signOut.view.SignOutActivity;
import com.plim.kati_app.jshCrossDomain.tech.google.JSHGoogleMap;
import com.plim.kati_app.kati.domain.old.dataChange.UserDataChangeActivity;

public class TempActivity extends KatiViewModelActivity {

    // Associate
        // View
        private Button signOutButton, logOutButton, mapButton, userDataEditButton, setSecondEmailButton;


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

        this.userDataEditButton=this.findViewById(R.id.tempActivity_userDataEditButton);

        this.setSecondEmailButton=this.findViewById(R.id.tempActivity_setSecondEmailButton);

    }
    @Override
    protected void initializeView() {
        this.signOutButton.setOnClickListener(v->this.signOutPressed());
//        this.logOutButton.setOnClickListener(v->this.logoutPressed());
        this.mapButton.setOnClickListener(v->this.mapPressed());

        this.userDataEditButton.setOnClickListener(v->this.userDataEditPressed());

       // this.setSecondEmailButton.setOnClickListener(v->this.setSecondEmailPressed());

    }



    @Override
    public void katiEntityUpdated() {
    }

    /**
     * Callback
     */
    private void signOutPressed(){
       this.startActivity(SignOutActivity.class);
    }
//    private void logoutPressed(){ this.startActivity(LogOutActivity.class); }
    private void mapPressed(){ JSHGoogleMap.openGoogleMapMyPositionAndSearch(this, "convenience store"); }

    private void userDataEditPressed() { this.startActivity(UserDataChangeActivity.class);}

   // private void setSecondEmailPressed(){ this.startActivity(SetSecondEmailActivity.class); }


    /**
     * Method
     */
    private void showNotLoginDialog(){
        KatiDialog.simplerAlertDialog(this,
            R.string.tempActivity_dialog_noLoginUser, R.string.tempActivity_dialog_noLoginUser,
            (dialog, which) -> this.startActivity(TempMainActivity.class)
        );
    }
}