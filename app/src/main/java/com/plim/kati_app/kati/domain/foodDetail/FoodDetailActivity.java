package com.plim.kati_app.kati.domain.foodDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHToolBar;
import com.plim.kati_app.kati.domain.foodDetail.adapter.ViewPagerAdapter;

import java.util.Arrays;
import java.util.List;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_DETAIL_ACTIVITY_TAB_ETC;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_DETAIL_ACTIVITY_TAB_PRODUCT;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_DETAIL_ACTIVITY_TAB_REVIEW;

public class FoodDetailActivity extends AppCompatActivity {

    //associate
    //view
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private JSHToolBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        this.tabLayout = findViewById(R.id.foodDetailActivity_tabLayout);
        this.viewPager2 = findViewById(R.id.foodDetailActivity_viewPager2);
        this.toolBar = findViewById(R.id.toolbar);

        ViewPagerAdapter fgAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(fgAdapter);

        final List<String> tabElement = Arrays.asList(FOOD_DETAIL_ACTIVITY_TAB_PRODUCT,FOOD_DETAIL_ACTIVITY_TAB_REVIEW,FOOD_DETAIL_ACTIVITY_TAB_ETC);
        new TabLayoutMediator(this.tabLayout, this.viewPager2, (tab, position) -> {
            TextView textView = new TextView(FoodDetailActivity.this);
            textView.setText(tabElement.get(position));
            textView.setTextColor(Color.BLACK);
            textView.setHighlightColor(getResources().getColor(R.color.kati_red, getTheme()));
            textView.setGravity(Gravity.CENTER);
            tab.setCustomView(textView);
        }).attach();
    }
}