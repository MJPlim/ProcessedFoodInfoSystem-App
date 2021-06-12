package com.plim.kati_app.kati.domain.main.category;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.plim.kati_app.kati.crossDomain.domain.model.Constant;

import org.jetbrains.annotations.NotNull;

public class CategoryViewPagerAdapter extends FragmentStateAdapter {

    public CategoryViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return new CategoryResultFragment(Constant.ECategory.values()[position]);
    }

    @Override
    public int getItemCount() {
        return Constant.ECategory.values().length;
    }
}