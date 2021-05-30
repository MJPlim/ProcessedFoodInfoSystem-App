package com.plim.kati_app.kati.domain.nnew.main.category;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class CategoryViewPagerAdapter extends FragmentStateAdapter {

    public CategoryViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return new CategoryResultFragment();
    }

    @Override
    public int getItemCount() {
        return 11;
    }
}