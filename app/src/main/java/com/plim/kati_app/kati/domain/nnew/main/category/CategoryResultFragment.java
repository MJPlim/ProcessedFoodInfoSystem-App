package com.plim.kati_app.kati.domain.nnew.main.category;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiCategoryFragment;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.nnew.foodDetail.FoodDetailActivity;
import com.plim.kati_app.kati.domain.nnew.main.category.CategoryAdapter.CategoryAdapter;
import com.plim.kati_app.kati.domain.nnew.main.category.model.CategoryFoodListResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FoodResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class CategoryResultFragment extends Fragment {
    //working variable
    private String selectedCategoryName;
    private int page=1;
    private boolean hasNext=false;

    private Vector<FoodResponse> vector;

    private ChipGroup chipGroup;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;


    private Constant.ECategory category;
    public CategoryResultFragment(){
        this.vector= new Vector<>();
    }

    public CategoryResultFragment(Constant.ECategory category) {
        this.vector= new Vector<>();
        this.category=category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("카테고리 이름",this.category.getName());
        this.chipGroup= view.findViewById(R.id.categoryResultFragment_chipGroup);
        this.recyclerView= view.findViewById(R.id.categoryResultFragment_recyclerView);
        this.chipGroup.removeAllViews();
        boolean firstChipCheck=true;
        for(Constant.EChildCategory childCategory: this.category.getChildCategories()){
            this.createChip(childCategory,firstChipCheck);
            if(firstChipCheck)firstChipCheck=false;
        }

        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        this.recyclerView.setLayoutManager(manager);
        this.adapter=new CategoryAdapter(v->intentDetailPage((Long)v.getTag()));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setNestedScrollingEnabled(false);


        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findLastCompletelyVisibleItemPosition();
                if (position == vector.size()-1&&hasNext) loadMore();

            }
        });
    }

    public void createChip(Constant.EChildCategory childCategory, boolean chipCheck){
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        Chip chip= (Chip) inflater.inflate(R.layout.jsh_chip, null);
//        chip.setOnCheckedChangeListener((buttonView, isChecked) -> loadCategory(isChecked,childCategory.getName()));
        chip.setChecked(chipCheck);
        chip.setText(childCategory.getName());
        this.chipGroup.addView(chip);
    }

    private void loadCategory(boolean isChecked,String categoryName){
        if(isChecked){
            Log.d("디버그, 칩 선택",categoryName);
            this.vector.clear();
            this.page=1;
            this.selectedCategoryName=categoryName;
            this.load();
        }
    }

    private void loadMore(){
        this.page++;
        this.load();
    }

    private void load(){
        KatiRetrofitTool.getAPI().getCategoryFood(this.selectedCategoryName, this.page).enqueue(
                JSHRetrofitTool.getCallback(new FoodCategoryCallback(this.getActivity())));
    }

    private void intentDetailPage(Long foodId) {
        Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID, foodId);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);
        startActivity(intent);
    }



    private class FoodCategoryCallback extends SimpleRetrofitCallBackImpl<CategoryFoodListResponse> {
        public FoodCategoryCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<CategoryFoodListResponse> response) {
            CategoryFoodListResponse categoryFoodListResponse = response.body();
            hasNext = categoryFoodListResponse.isHas_next();
            vector.addAll(categoryFoodListResponse.getData());
            adapter.setItems(vector);
        }
    }
}