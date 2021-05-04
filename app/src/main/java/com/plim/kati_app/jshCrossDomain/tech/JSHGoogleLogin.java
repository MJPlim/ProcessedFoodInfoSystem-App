package com.plim.kati_app.jshCrossDomain.tech;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;

public class JSHGoogleLogin {

    public static final int SignInRequestCode = JSHGoogleLogin.class.hashCode();

    public static void signOut(Activity activity, OnCompleteListener onCompleteListener) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(onCompleteListener);
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> mGoogleSignInClient.revokeAccess());
    }
    public static void signIn(Activity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        activity.startActivityForResult(mGoogleSignInClient.getSignInIntent(), SignInRequestCode);
    }
}
