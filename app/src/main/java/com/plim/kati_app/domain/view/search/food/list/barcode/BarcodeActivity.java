package com.plim.kati_app.domain.view.search.food.list.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.view.search.food.detail.NewDetailActivity;


public class BarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);//바코드 인식시 소리
        intentIntegrator.initiateScan(); //바코드 인식 시작


    }
    // 바코드 스캔 결과 sb.toString() 가져다 쓰면 됩니다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {

                String string= result.getContents();

                Toast.makeText(this, "Scanned: " + string, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, NewDetailActivity.class);
                intent.putExtra("barcode",string);
                Log.d(string,"바코드 스캔");
                startActivity(intent);
            }

            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}