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
import com.plim.kati_app.domain2.view.main.search.list.adapter.DarkToggleButtonRecyclerViewAdapter;

import java.util.Vector;

/**
 * 음식 검색에서 카테고리를 고르는 부분 프래그먼트(예시 커피,차-> 커피, 가루차, 꽃차, 보리차).
 * layout: fragment_category_detail_list.xml
 */
public class FoodCategoryDetailListFragment extends Fragment {

    //Associate
    //View
    private RecyclerView categoryDetailRecyclerView;

    //adapter
    private DarkToggleButtonRecyclerViewAdapter categoryDetailRecyclerViewAdapter;

    //component
    private Vector<String> values;

    public FoodCategoryDetailListFragment(Vector<String> values) {
        this.values = new Vector<>();
        this.values.addAll(values);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.categoryDetailRecyclerView = getView().findViewById(R.id.foodSearchListFragment_categoryDetailRecyclerView);
        this.categoryDetailRecyclerViewAdapter = new DarkToggleButtonRecyclerViewAdapter(this.values);
        this.categoryDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.categoryDetailRecyclerView.setAdapter(this.categoryDetailRecyclerViewAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_detail_list, container, false);
    }
}