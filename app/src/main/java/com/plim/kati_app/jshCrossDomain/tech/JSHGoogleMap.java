package com.plim.kati_app.jshCrossDomain.tech;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

public class JSHGoogleMap {

    public static void openGoogleMapMyPositionAndSearch(Activity activity, String search){
        try{
            Uri gmmIntentUri = Uri.parse("geo:0,0?q="+search);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            activity.startActivity(mapIntent);
        }catch(Exception e){
            Toast.makeText(activity, "Turn On Google Map", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + "com.google.android.apps.maps"));
            activity.startActivity(intent);
        }
    }
}
