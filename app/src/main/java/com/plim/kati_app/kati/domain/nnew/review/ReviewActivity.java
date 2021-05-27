package com.plim.kati_app.kati.domain.nnew.review;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.RatingBar;

import com.plim.kati_app.R;


public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        RatingBar ratingBar = findViewById(R.id.ratingBar2);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        int unfilledColor = getResources().getColor(R.color.kati_middle_gray, this.getTheme());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stars.getDrawable(0).setTint(unfilledColor);
        } else {
            stars.getDrawable(0).setColorFilter(unfilledColor, PorterDuff.Mode.SRC_ATOP);
        }
    }
}