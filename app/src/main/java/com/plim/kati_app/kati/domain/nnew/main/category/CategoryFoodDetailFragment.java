package com.plim.kati_app.kati.domain.nnew.main.category;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class CategoryFoodDetailFragment extends KatiViewModelFragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_food;
    }

    @Override
    protected void associateView(View view) {
        this.tabLayout = view.findViewById(R.id.categoryFoodFragment_tabLayout);
        this.viewPager2 = view.findViewById(R.id.categoryFoodFragment_viewPager2);

    }

    @Override
    protected void initializeView() {
        final Vector<Constant.ECategory> categories = new Vector(Arrays.asList(Constant.ECategory.values()));

        CategoryViewPagerAdapter fgAdapter = new CategoryViewPagerAdapter(this.getActivity(), categories);
        viewPager2.setAdapter(fgAdapter);

        new TabLayoutMediator(
                tabLayout,
                viewPager2,
                (tab, position) -> {
                    TextView textView = new TextView(CategoryFoodDetailFragment.this.getContext());
                    textView.setText(categories.get(position).getName());
                    textView.setTextColor(Color.BLACK);
                    textView.setHighlightColor(getResources().getColor(R.color.kati_red, getActivity().getTheme()));
                    textView.setGravity(Gravity.CENTER);
                    tab.setCustomView(textView);
                }
        ).attach();

        ArrayList<Constant.ECategory> list = new ArrayList<>();
        for (Constant.ECategory category : Constant.ECategory.values()) {
            list.add(category);
        }
        this.position = list.indexOf(Constant.ECategory.valueOf(getArguments().getString("category")));
        this.tabLayout.selectTab(this.tabLayout.getTabAt(this.position));
    }

    @Override
    protected void katiEntityUpdated() {

    }


}