package com.plim.kati_app.kati.domain.nnew.main.category;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.plim.kati_app.kati.crossDomain.domain.model.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class CategoryViewPagerAdapter extends FragmentStateAdapter {

    private Vector<Constant.ECategory> categories;

    public CategoryViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity, Vector<Constant.ECategory> categories) {
        super(fragmentActivity);
        this.categories=categories;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return new CategoryResultFragment(this.categories.get(position));
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }
}