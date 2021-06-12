package com.plim.kati_app.kati.domain.main.category;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiCategoryFragment;


import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.HOME_FRAGMENT_BUNDLE_KEY;

public class CategoryFoodDetailFragment extends KatiCategoryFragment {

    //associate
    //view
    private FloatingActionButton actionButton;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    //working variable
    private int position;
    private Constant.ECategory eCategory = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_food, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.tabLayout = view.findViewById(R.id.categoryFoodFragment_tabLayout);
        this.viewPager2 = view.findViewById(R.id.categoryFoodFragment_viewPager2);
        this.actionButton = view.findViewById(R.id.floatingActionButton);

        this.actionButton.setOnClickListener(v ->{
            Navigation.findNavController(view).navigate(R.id.action_categoryFragment_to_categoryFilterFragment);
        });

        final Vector<Constant.ECategory> categories = new Vector(Arrays.asList(Constant.ECategory.values()));

        CategoryViewPagerAdapter fgAdapter = new CategoryViewPagerAdapter(this.getActivity());
        viewPager2.setAdapter(fgAdapter);

        if (this.eCategory == null) {
            this.eCategory = Constant.ECategory.valueOf(getArguments().getString(HOME_FRAGMENT_BUNDLE_KEY));
             }

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

        this.tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        position = tabLayout.getSelectedTabPosition();
                        eCategory = Constant.ECategory.values()[position];
                        new Handler().postDelayed(()-> viewPager2.setCurrentItem(position,false),100);
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {}
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {}
                }
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_food;
    }

    @Override
    protected void associateView(View view) { }

    @Override
    protected void initializeView() {}

    @Override
    protected void katiEntityUpdatedAndLogin() {}

    @Override
    protected void katiEntityUpdatedAndNoLogin() {}


    @Override
    protected void categoryDataUpdated() {}

    @Override
    public void onResume() {
        super.onResume();
        this.selectTab();
    }

    /**
     * method
     */
    private void selectTab() {
        List<Constant.ECategory> list = Arrays.asList(Constant.ECategory.values());
        this.position = list.indexOf(eCategory);
        this.tabLayout.selectTab(this.tabLayout.getTabAt(this.position));
    }


}