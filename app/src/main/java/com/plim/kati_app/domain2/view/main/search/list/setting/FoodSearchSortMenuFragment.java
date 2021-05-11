package com.plim.kati_app.domain2.view.main.search.list.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.constant.Constant_yun;
import com.plim.kati_app.domain2.view.main.search.list.adapter.LightButtonRecyclerViewAdapter;

import java.util.Vector;

public class FoodSearchSortMenuFragment extends Fragment {

    private RecyclerView foodSearchSortMenuRecyclerView;
    private LightButtonRecyclerViewAdapter foodSearchSortMenuRecyclerViewAdapter;
    private Vector<String> values;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        values = new Vector<>();
        for (Constant_yun.ESortMode mode : Constant_yun.ESortMode.values()) {
            values.add(mode.name());
        }

        this.foodSearchSortMenuRecyclerView = getView().findViewById(R.id.foodSearchSortMenuFragment_categoryDetailRecyclerView);
        this.foodSearchSortMenuRecyclerViewAdapter = new LightButtonRecyclerViewAdapter(this.values);
        this.foodSearchSortMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.foodSearchSortMenuRecyclerView.setAdapter(this.foodSearchSortMenuRecyclerViewAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_food_search_sort_menu, container, false);
    }

}