package com.plim.kati_app.kati.domain.nnew.foodDetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FoodInfoFragment();
            case 1:
                return new ReviewFragment();
            case 2:
                return new ExtraInfoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

//public class ViewPagerAdapter extends RecyclerView.Adapter<PagerViewHolder> {
//
//    @Override
//    public PagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view;
//        if(viewType==0){
//            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_food_info, parent, false);
//        }else if(viewType==1){
//            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_review, parent, false);
//        }else{
//            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_extra_info, parent, false);
//        }
//        return new PagerViewHolder(view);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//    @Override
//    public void onBindViewHolder(PagerViewHolder holder, int position) {
//    }
//
//    @Override public int getItemCount() { return 3; }
//}