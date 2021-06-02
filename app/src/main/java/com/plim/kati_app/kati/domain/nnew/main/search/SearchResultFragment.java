package com.plim.kati_app.kati.domain.nnew.main.search;

import android.app.Activity;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.android.material.chip.Chip;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.nnew.foodDetail.FoodDetailActivity;
import com.plim.kati_app.kati.domain.nnew.main.myKati.allergy.model.ReadUserAllergyResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.adapter.AdRecyclerAdapter;
import com.plim.kati_app.kati.domain.nnew.main.search.adapter.FoodRecyclerAdapter;
import com.plim.kati_app.kati.domain.nnew.main.search.model.AdvertisementResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FindFoodBySortingResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FoodResponse;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class SearchResultFragment extends KatiSearchFragment {

    //associate
    //view
    private EditText searchFieldEditText;
    private ImageView deleteIcon;
    private Chip rankingChip, manufacturerChip, reviewCountChip;
    private RecyclerView adRecyclerView, resultRecyclerView;
    private NestedScrollView nestedScrollView;
    private Button allergyFilterButton;

    //adapter
    private FoodRecyclerAdapter foodRecyclerAdapter;
    private AdRecyclerAdapter adRecyclerAdapter;

    //model
    private Vector<FoodResponse> vector;

    //working Variable
    private boolean isLoadingMore = false;
    private boolean hasNext = true;

    public SearchResultFragment() {
        this.vector = new Vector<>();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void associateView(View view) {
        this.searchFieldEditText = view.findViewById(R.id.searchResultFragment_searchFieldEditText);

        this.deleteIcon = view.findViewById(R.id.searchResultFragment_deleteIcon);

        this.rankingChip = view.findViewById(R.id.searchResultFragment_rankingChip);
        this.manufacturerChip = view.findViewById(R.id.searchResultFragment_manufacturerChip);
        this.reviewCountChip = view.findViewById(R.id.searchResultFragment_reviewCountChip);

        this.adRecyclerView = view.findViewById(R.id.searchResultFragment_adRecyclerView);
        this.resultRecyclerView = view.findViewById(R.id.searchResultFragment_resultRecyclerView);

        this.nestedScrollView = view.findViewById(R.id.searchResultFragment_nestedScrollView);
        this.allergyFilterButton = view.findViewById(R.id.searchResultFragment_allergyFilterButton);
    }

    @Override
    protected void initializeView() {
        this.adRecyclerAdapter = new AdRecyclerAdapter(this.getActivity(), v -> this.doClickOnAdItem(v));
        this.foodRecyclerAdapter = new FoodRecyclerAdapter(this.getActivity(), v -> this.doClickOnFoodItem(v));

        this.allergyFilterButton.setOnClickListener(v -> navigateTo(R.id.action_searchResultFragment_to_allergyFragment));

        this.manufacturerChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.doSort(isChecked, Constant.SortElement.MANUFACTURER));
        this.rankingChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.doSort(isChecked, Constant.SortElement.RANK));
        this.reviewCountChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.doSort(isChecked, Constant.SortElement.REVIEW_COUNT));
        this.rankingChip.performClick();

        this.adRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.adRecyclerView.setAdapter(this.foodRecyclerAdapter);
        this.adRecyclerView.setNestedScrollingEnabled(false);

        this.resultRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.resultRecyclerView.setAdapter(this.foodRecyclerAdapter);
        this.resultRecyclerView.setNestedScrollingEnabled(false);

        this.nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
                    loadMore();
                }
            }
        });

        this.searchFieldEditText.setText(this.searchModel.getSearchText());
        this.searchFieldEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                this.loadSearchResult();
                this.searchModel.setSearchPageNum(1);
                this.isLoadingMore = false;
                this.searchModel.setSearchText(this.searchFieldEditText.getText().toString());
                this.saveSearch();
            }
            return false;
        });

        this.deleteIcon.setOnClickListener(v -> this.searchFieldEditText.setText(""));
    }

    private void hideKeyboard(){
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(searchFieldEditText.getWindowToken(), 0);
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        this.searchModel.setFiltered(true);
        this.saveSearch();

        this.allergyFilterButton.setEnabled(this.searchModel.isFiltered());
        this.getAllergyData(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));

        this.loadAdvertisement();
        this.refresh();

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
        this.searchModel.setFiltered(false);
        this.saveSearch();

        this.allergyFilterButton.setEnabled(this.searchModel.isFiltered());
        this.loadAdvertisement();

        this.refresh();

    }


    @Override
    public void onResume() {
        super.onResume();
        this.save();
        this.saveSearch();
        if (this.rankingChip != null)
            this.rankingChip.performClick();
    }

    @Override
    protected void searchModelDataUpdated() {
        if (this.dataset != null) {
            this.loadSearchResult();
            this.hideKeyboard();
        }
    }

    private class ReadUserAllergyShowCallBack extends SimpleLoginRetrofitCallBack<ReadUserAllergyResponse> {
        public ReadUserAllergyShowCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<ReadUserAllergyResponse> response) {
            Vector<String> vector = new Vector<>();
            vector.addAll(response.body().getUserAllergyMaterials());
            searchModel.setAllergyList(vector);
            Log.d("알레르기 불러옴",vector.size()!=0? vector.get(0): "내용 없음");
        }
    }

    private class AdListRequestCallback extends SimpleRetrofitCallBackImpl<List<AdvertisementResponse>> {
        public AdListRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<List<AdvertisementResponse>> response) {
            Vector<AdvertisementResponse> items = new Vector<>(response.body());
            adRecyclerAdapter.setItems(items);
            adRecyclerView.setAdapter(adRecyclerAdapter);
        }
    }

    private class SearchRequestCallback extends SimpleRetrofitCallBackImpl<FindFoodBySortingResponse> {
        public SearchRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<FindFoodBySortingResponse> response) {
            Log.d("검색 불러옴","검색 검색 시작");
            FindFoodBySortingResponse dto = response.body();

            hasNext = dto.isHas_next();
            searchModel.setSearchPageNum(dto.getCurrent_page() + 1);

            if (!isLoadingMore) vector.clear();
            vector.addAll(response.body().getData());
            foodRecyclerAdapter.setItems(vector);
            foodRecyclerAdapter.notifyDataSetChanged();
            isLoadingMore = false;
        }
    }


    private void loadAdvertisement() {
        KatiRetrofitTool.getAPI().getAdFoodList().enqueue(JSHRetrofitTool.getCallback(new AdListRequestCallback(getActivity())));
    }

    private void loadMore() {
        if (hasNext) {
            this.searchModel.setSearchPageNum(this.searchModel.getSearchPageNum() + 1);
            this.isLoadingMore = true;
            this.saveSearch();
        }
    }
    private void refresh() {
        if (hasNext) {
            this.searchModel.setFoodSortElement(Constant.SortElement.RANK.getMessage());
            this.rankingChip.setChecked(true);
            this.searchModel.setSearchPageNum(1);
            this.isLoadingMore = false;
            this.saveSearch();
        }
    }

    private void loadSearchResult() {

        Log.d("태그", "페이지" + this.searchModel.getSearchPageNum() + " 정렬기준" +
                this.searchModel.getFoodSortElement() + " 검색어" +
                this.searchModel.getSearchText() + " 더불러오기" + this.isLoadingMore+" 필터 켜짐"+this.searchModel.isFiltered());

        if (!this.isLoadingMore) this.vector.clear();


        String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

        if (this.searchModel.getSearchMode().equals(Constant.ESearchMode.제품.name())) {
            KatiRetrofitTool.getAPI().getNameFoodListBySorting(
                    this.searchModel.getSearchPageNum(),
                    this.searchModel.getPageSize(),
                    this.searchModel.getFoodSortElement(),
                    this.searchModel.getSearchText(),
                    !this.searchModel.isFiltered() ? null : this.getAllergyData(token)
            ).enqueue(JSHRetrofitTool.getCallback(new SearchRequestCallback(this.getActivity())));
        } else {
            KatiRetrofitTool.getAPI().getManufacturerFoodListBySorting(
                    this.searchModel.getSearchPageNum(),
                    this.searchModel.getPageSize(),
                    this.searchModel.getFoodSortElement(),
                    this.searchModel.getSearchText(),
                    !this.searchModel.isFiltered() ? this.searchModel.getAllergyList() : null
            ).enqueue(JSHRetrofitTool.getCallback(new SearchRequestCallback(this.getActivity())));
        }
    }



    private void doSort(boolean isChecked, Constant.SortElement element) {
        if (isChecked) {
            this.searchModel.setSearchPageNum(1);
            this.searchModel.setFoodSortElement(element.getMessage());
            this.saveSearch();
        }
    }


    private Vector<String> getAllergyData(String token) {
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).readUserAllergy().
                enqueue(JSHRetrofitTool.getCallback(new ReadUserAllergyShowCallBack(getActivity())));
        return this.searchModel.getAllergyList();
    }

    private void doClickOnAdItem(View v) {
        Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
        intent.putExtra(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA, (Long) v.getTag());
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, true);
        startActivity(intent);
    }

    private void doClickOnFoodItem(View v) {
        Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
        intent.putExtra(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA, (Long) v.getTag());
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);
        startActivity(intent);
    }
}