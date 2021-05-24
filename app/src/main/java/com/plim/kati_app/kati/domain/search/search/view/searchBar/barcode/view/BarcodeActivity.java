package com.plim.kati_app.kati.domain.search.search.view.searchBar.barcode.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.search.foodInfo.view.FoodInfoActivity;

public class BarcodeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);//바코드 인식시 소리
        intentIntegrator.initiateScan(); //바코드 인식 시작
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) { Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show(); }
            else {
                Intent intent = new Intent(this, FoodInfoActivity.class);
                intent.putExtra("barcode",result.getContents());
                Log.d("디버그",result.getContents());
                startActivity(intent);
            }
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}