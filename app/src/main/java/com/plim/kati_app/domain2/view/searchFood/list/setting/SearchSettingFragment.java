package com.plim.kati_app.domain2.view.searchFood.list.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.constant.Constant_yun;
import com.plim.kati_app.domain.asset.BlankFragment;
import com.plim.kati_app.domain2.view.searchFood.list.adapter.SortButtonRecyclerViewAdapter;

import java.util.Vector;


public class SearchSettingFragment extends Fragment {


    //working variable
    private boolean isFiltered = true;
    private boolean showAllergy = false;


    // Associate
    // View
    //카테고리를 선택.
    private RecyclerView sortButtonsRecyclerView;
    private TabLayout categoryTabLayout;
    //알레르기 필터링
    private ImageView allergyImageView;
    private TextView allergyTextView;


    //component
    private Vector<Fragment> categoryViewFragmentVector;
    private Fragment allergyViewFragment;
    private BlankFragment blankFragment;




    public SearchSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_setting_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associate View
        this.allergyImageView = view.findViewById(R.id.searchFragment_allergyImageView);
        this.allergyTextView = view.findViewById(R.id.searchFragment_allergyTextView);
        this.sortButtonsRecyclerView = view.findViewById(R.id.searchFragment_sortButtonRecyclerView);
        this.categoryTabLayout = view.findViewById(R.id.searchFragment_tabLayout);

        // Set View Attribute
        //enum 값을 바탕으로 카테고리 구성하기.
        this.categoryViewFragmentVector = new Vector<>();
        Vector<String> val = new Vector<>();
        int i = 0;
        for (Constant_yun.ECategory category : Constant_yun.ECategory.values()) {
            categoryTabLayout.addTab(categoryTabLayout.newTab().setText(category.getName()), i);
            val.addAll(category.getChildNames());
            this.categoryViewFragmentVector.add(i, new FoodCategoryDetailListFragment(val));

            //재사용하기 위해 다시 세팅
            i++;
            val.clear();
        }
        getParentFragmentManager().beginTransaction().replace(R.id.searchFragment_frameLayout, categoryViewFragmentVector.get(0)).commit();
        this.categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = categoryViewFragmentVector.get(position);
                getParentFragmentManager().beginTransaction().replace(R.id.searchFragment_frameLayout, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        Vector<String> data = this.getData();
        this.allergyViewFragment = new AllergyViewFragment(data);
        this.blankFragment = new BlankFragment();
        this.allergyTextView.setOnClickListener(v -> {
            this.showAllergy = !showAllergy;
            if (showAllergy)
                getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_allergyFrameLayout, allergyViewFragment).commit();
            else
                getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_allergyFrameLayout, blankFragment).commit();
        });

        this.allergyImageView.setOnClickListener((v) -> {
            setColor();
        });

        this.setColor();
        this.sortButtonsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        val.clear();
        for (Constant_yun.ESortModeBig mode : Constant_yun.ESortModeBig.values())
            val.add(mode.getName());

        this.sortButtonsRecyclerView.setAdapter(new SortButtonRecyclerViewAdapter(val, new myListener()));


    }


    /**
     * 알러지 필터 버튼 눌렀을 때 색 변경하는 메소드.
     */
    public void setColor() {
        int newTint = this.isFiltered ? R.color.kati_red : R.color.kati_yellow;
        this.isFiltered = !isFiltered;
        this.allergyImageView.setColorFilter(ContextCompat.getColor(getContext(), newTint), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    /**
     * 설정된 알러지 필터 목록을 불러온다.
     *
     * @return 알러지 필터로 된 벡터.
     */
    private Vector<String> getData() {
        Vector<String> data = new Vector<>();
        //임시. 실제로는 저장 값 가져온다.
        data.add("땅콩");
        data.add("대두");
        data.add("우유");
        data.add("호두");
        return data;
    }






    /**
     * 알레르기 필터 레이아웃을 교체하는 리스너.
     */
    private class myListener implements View.OnClickListener {

        private boolean show;
        private Fragment menuFragment, blankFragment;

        public myListener() {
            this.menuFragment = new FoodSearchSortMenuFragment();
            this.blankFragment = new BlankFragment();
        }

        @Override
        public void onClick(View v) {
            this.show = !show;
            if (show)
                getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_sortFrameLayout, menuFragment).commit();
            else
                getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_sortFrameLayout, blankFragment).commit();
        }
    }









}