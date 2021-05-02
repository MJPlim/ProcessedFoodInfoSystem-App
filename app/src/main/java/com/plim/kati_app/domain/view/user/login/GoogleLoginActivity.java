package com.plim.kati_app.domain.view.user.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.view.user.logOut.LogOutActivity;

public class GoogleLoginActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("로그인", "On Create");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 구글 로그인 여부 확인
        Log.d("로그인", "구글 로그인 여부 확인");
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            // 로그인 된 상태
            signOut();
        }else{
            signIn();
        }
    }

    public void signIn() {
        Log.d("로그인", "signIn 시작");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("로그인", "result 확인");
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, account.getEmail() + account.getGivenName(), Toast.LENGTH_SHORT).show();
            // Signed in successfully, show authenticated UI.
            Log.d("로그인", "메인 액티비티 돌아가기" + account.getEmail());
            startMainActivity();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("로그인", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void signOut() {
        Log.d("로그인", "구글 로그아웃");

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        KatiDialog.simpleAlertDialog(GoogleLoginActivity.this,
                                getString(R.string.google_logout_maintext),
                                getString(R.string.google_logout_subtext),
                                (dialog, which) -> {
                                    mGoogleSignInClient.revokeAccess();
                                    startMainActivity();
                                }
                                , getResources().getColor(R.color.kati_coral, getTheme())).showDialog();
                    }
                });
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
