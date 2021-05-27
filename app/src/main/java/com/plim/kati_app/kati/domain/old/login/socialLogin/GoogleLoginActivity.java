package com.plim.kati_app.kati.domain.old.login.socialLogin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;

public class GoogleLoginActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 구글 로그인 여부 확인
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if(account != null){
//            // 로그인 된 상태
//            signOut();
//        }else{
//            signIn();
//        }
    }

    public void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//
//        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            Toast.makeText(this, account.getEmail() + account.getGivenName(), Toast.LENGTH_SHORT).show();
//            // Signed in successfully, show authenticated UI.
//            startMainActivity();
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w("로그인", "signInResult:failed code=" + e.getStatusCode());
//        }
    }

    public void signOut() {
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        KatiDialog.simpleAlertDialog(com.plim.kati_app.domain.view.user.login.GoogleLoginActivity.this,
//                                getString(R.string.google_logout_maintext),
//                                getString(R.string.google_logout_subtext),
//                                (dialog, which) -> {
//                                    mGoogleSignInClient.revokeAccess();
//                                    startMainActivity();
//                                }
//                                , getResources().getColor(R.color.kati_coral, getTheme())).showDialog();
//                    }
//                });
    }

    public void startMainActivity() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }
}
