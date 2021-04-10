package com.plim.kati_app.domain.view.search.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plim.kati_app.R;

/**
 * 음식 검색하는 검색필드가 있는 프래그먼트
 * !이슈: 검색할 때 입력중이면 카메라 버튼 대신 "x"버튼을 보여주고, x버튼을 누르면 입력한 데이터를 날릴 수 있으면 좋겠다.
 */
public class FoodSearchFieldFragment extends Fragment {

    //associate
    //view
//    private EditText searchEditText;
//    private ImageView cameraSearchButton, textSearchButton, deleteInputButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_food_search_field, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //associate view
//        this.searchEditText=view.findViewById(R.id.foodSearchFieldFragment_searchEditText);
//        this.cameraSearchButton =view.findViewById(R.id.foodSearchFieldFragment_cameraSearchButton);
//        this.textSearchButton =view.findViewById(R.id.foodSearchFieldFragment_textSearchButton);
//        this.deleteInputButton=view.findViewById(R.id.foodSearchFieldFragment_deleteInputButton);
        //set view
    }
}