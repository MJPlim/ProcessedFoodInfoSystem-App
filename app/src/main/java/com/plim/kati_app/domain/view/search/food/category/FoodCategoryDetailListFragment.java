package com.plim.kati_app.domain.view.search.food.category;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.view.search.food.list.adapter.DarkToggleButtonRecyclerViewAdapter;
import com.plim.kati_app.domain.view.search.food.list.adapter.LightButtonRecyclerViewAdapter;

import java.util.Vector;

/**
 * 음식 검색에서 카테고리를 고르는 부분 프래그먼트(예시 커피,차-> 커피, 가루차, 꽃차, 보리차).
 * layout: fragment_category_detail_list.xml
 */
public class FoodCategoryDetailListFragment extends Fragment {

    //Associate
    //View
    private ChipGroup chipGroup;

    //component
    private Vector<String> values;

    public FoodCategoryDetailListFragment(){}

    public FoodCategoryDetailListFragment(Vector<String> values) {
        this.values = new Vector<>();
        this.values.addAll(values);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.chipGroup = getView().findViewById(R.id.foodSearchListFragment_chipGroup);
        for(String value:values){
            Chip chip= new Chip(getContext());
            chip.setCheckable(true);
            chip.setText(value);
            chip.setOnCheckedChangeListener(((buttonView, isChecked) -> {if(isChecked)this.searchFood(value);}));
            this.chipGroup.addView(chip);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_detail_list, container, false);
    }

    private void searchFood(String value) {
        Bundle bundle= new Bundle();
        bundle.putString("categoryName",value);
        this.getActivity().getSupportFragmentManager().setFragmentResult("searchCategory",bundle);
    }




}