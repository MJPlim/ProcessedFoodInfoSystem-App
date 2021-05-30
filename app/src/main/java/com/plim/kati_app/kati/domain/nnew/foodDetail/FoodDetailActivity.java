package com.plim.kati_app.kati.domain.nnew.foodDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.nnew.foodDetail.adapter.ViewPagerAdapter;

import java.util.Arrays;
import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        TabLayout tabLayout = findViewById(R.id.foodDetailActivity_tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.foodDetailActivity_viewPager2);

        ViewPagerAdapter fgAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(fgAdapter);

        final List<String> tabElement = Arrays.asList("제품","리뷰","기타");
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(FoodDetailActivity.this);
                textView.setText(tabElement.get(position));
                textView.setTextColor(Color.BLACK);
                textView.setHighlightColor(getResources().getColor(R.color.kati_red, getTheme()));
                textView.setGravity(Gravity.CENTER);
                tab.setCustomView(textView);
            }
        }).attach();
    }
}