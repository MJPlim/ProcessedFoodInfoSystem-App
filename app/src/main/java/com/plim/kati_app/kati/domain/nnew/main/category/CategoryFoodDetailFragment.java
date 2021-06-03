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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton actionButton;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private int position;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.tabLayout = view.findViewById(R.id.categoryFoodFragment_tabLayout);
        this.viewPager2 = view.findViewById(R.id.categoryFoodFragment_viewPager2);
        this.actionButton=view.findViewById(R.id.floatingActionButton);

        this.actionButton.setOnClickListener(v->{

            ( (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mainActivity_navHostFragment)).getNavController()
                    .navigate(R.id.action_categoryFragment_to_categoryFilterFragment);
        });

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

        //최초에 옮기는 코드
        Log.d("최초에만","설마");
        List<Constant.ECategory> list = Arrays.asList(Constant.ECategory.values());
        this.position = list.indexOf(Constant.ECategory.valueOf(getArguments().getString("category")));
        this.tabLayout.selectTab(this.tabLayout.getTabAt(this.position));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_food;
    }

    @Override
    protected void associateView(View view) {


    }

    @Override
    protected void initializeView() {



    }

    @Override
    protected void katiEntityUpdated() {

    }


}