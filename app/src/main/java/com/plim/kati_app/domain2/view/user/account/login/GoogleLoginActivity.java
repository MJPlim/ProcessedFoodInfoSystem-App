package com.plim.kati_app.domain2.view.user.account.login;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain2.view.TempMainActivity;
import com.plim.kati_app.jshCrossDomain.tech.JSHGoogleLogin;

public class GoogleLoginActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) { this.signOut(); } else {this.signIn();}
    }

    // Sign Out
    private void signOut() { JSHGoogleLogin.signOut(this, task -> this.showSignOutCompleteDialog()); }
    private void showSignOutCompleteDialog() {
        KatiDialog.simpleAlertDialog(this,
                this.getString(R.string.google_logout_maintext),
                this.getString(R.string.google_logout_subtext),
                (dialog, which) -> { this.startMainActivity(); },
                this.getResources().getColor(R.color.kati_coral, getTheme())
        ).showDialog();
    }

    // Sign In
    private void signIn() { JSHGoogleLogin.signIn(this); }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleSignInResult(data);
    }
    private void handleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(this, account.getEmail() + account.getGivenName(), Toast.LENGTH_SHORT).show();
            this.startMainActivity();
        } catch (ApiException e) { Log.w("로그인", "signInResult:failed code=" + e.getStatusCode()); }
    }

    // Method
    public void startMainActivity() { this.startActivity(new Intent(this, TempMainActivity.class)); }
}
