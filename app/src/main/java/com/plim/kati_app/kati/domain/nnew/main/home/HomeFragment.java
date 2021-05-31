package com.plim.kati_app.kati.domain.nnew.main.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHViewPagerTool;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.YYECategoryItem;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.domain.nnew.main.home.advertisement.AdvertisementViewPagerAdapter;
import com.plim.kati_app.kati.domain.nnew.map.MapServiceActivity;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends KatiViewModelFragment {

    private ViewPager2 viewPager2;
    private TextView searchText;
    private GridLayout layout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void associateView(View view) {
        this.viewPager2 = view.findViewById(R.id.homeFragment_viewPager);
        this.layout = view.findViewById(R.id.homeFragment_category);
        this.searchText = view.findViewById(R.id.homeFragment_searchTextView);
    }

    @Override
    protected void initializeView() {
        this.viewPager2.setAdapter(new AdvertisementViewPagerAdapter());
        JSHViewPagerTool.setAutoSlide(this.viewPager2, 5000);
        JSHViewPagerTool.setEffect(this.viewPager2);

        this.layout.removeAllViews();
        for (Constant.ECategory category : Constant.ECategory.values()) {
            YYECategoryItem item = new YYECategoryItem(getContext());
            item.setText(category.getName());
            item.setImage(getResources().getDrawable(category.getDrawable(), getActivity().getTheme()));
            item.setOnClickListener(v -> this.moveToCategory(category));
            this.layout.addView(item);
        }
        this.searchText.setOnClickListener(v -> {
//            Button button = this.getView().findViewById(R.id.action_search);
//            if (button != null)
//                button.performClick();
            navigateTo(R.id.action_global_searchFragment);
        });

    }

    @Override
    protected void katiEntityUpdated() {

    }

    private void moveToCategory(Constant.ECategory category) {
        navigateTo(R.id.action_homeFragment_to_categoryFragment);
    }

}