package com.plim.kati_app.kati.domain.nnew.main.category;

import android.graphics.Color;
import android.os.Bundle;
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

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class CategoryFoodDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2_container);

        CategoryViewPagerAdapter fgAdapter = new CategoryViewPagerAdapter(this.getActivity());
        viewPager2.setAdapter(fgAdapter);

        final List<String> tabElement = Arrays.asList("카테","카테","카테","카테","카테","카테","카테","카테","카테","카테","카테");
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(CategoryFoodDetailFragment.this.getContext());
                textView.setText(tabElement.get(position));
                textView.setTextColor(Color.BLACK);
                textView.setHighlightColor(getResources().getColor(R.color.kati_red, getActivity().getTheme()));
                textView.setGravity(Gravity.CENTER);
                tab.setCustomView(textView);
            }
        }).attach();
    }

}