package com.plim.kati_app.kati.domain.search.search.view.foodList;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.search.foodInfo.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.search.search.model.AdvertisementResponse;
import com.plim.kati_app.kati.domain.search.search.view.foodList.advertisement.AdRecyclerAdapter;
import com.plim.kati_app.kati.domain.search.search.model.FindFoodBySortingResponse;
import com.plim.kati_app.kati.domain.search.search.model.FoodResponse;
import com.plim.kati_app.kati.domain.search.search.view.foodList.searchResult.FoodRecyclerAdapter;

import java.util.List;
import java.util.Vector;

import lombok.AllArgsConstructor;
import retrofit2.Response;

public class SearchSearchResultListFragment extends KatiSearchFragment {

    // Associate
    // View
    private RecyclerView adFoodInfoRecyclerView;
    private RecyclerView foodInfoRecyclerView;

    // Component
    // View
    private LoadingDialog dialog;
    private FoodRecyclerAdapter foodRecyclerAdapter;
    private AdRecyclerAdapter adRecyclerAdapter;
    private NestedScrollView scrollView;

    //model
    private Vector<FoodResponse> vector;

    //working Variable
    private boolean isLoadingMore = false;
    private boolean hasNext = true;

    public SearchSearchResultListFragment(){
        this.vector = new Vector<>();
    }


    /**
     * System Life Cycle Callback
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_food_search_result_list;
    }

    @Override
    protected void associateView(View view) {
        this.scrollView=view.findViewById(R.id.foodSearchResultListFragment_nestedScrollView);
        this.adFoodInfoRecyclerView = view.findViewById(R.id.searchFragment_adFoodInfoRecyclerView);
        this.foodInfoRecyclerView = view.findViewById(R.id.searchFragment_foodInfoRecyclerView);
    }

    @Override
    protected void initializeView() {
        this.adRecyclerAdapter = new AdRecyclerAdapter(this.getActivity());
        this.foodRecyclerAdapter = new FoodRecyclerAdapter(this.getActivity());
        this.dialog = new LoadingDialog(getContext());


        this.adFoodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.adFoodInfoRecyclerView.setAdapter(this.foodRecyclerAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        this.foodInfoRecyclerView.setLayoutManager(manager);
        this.foodInfoRecyclerView.setAdapter(this.foodRecyclerAdapter);

        this.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if(v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
                    Log.d("스크롤 내림","dd");
                    loadMore();
                }
            }
        });

    }

    private void loadMore() {
        if (hasNext) {
            this.searchModel.setSearchPageNum(this.searchModel.getSearchPageNum() + 1);
            this.isLoadingMore = true;
            this.saveSearch();

        }
    }

    @Override
    protected void katiEntityUpdated() {
        this.loadAdvertisement();
        this.loadSearchResult();
        Log.d("리스트", "엔티티 업데이트");
    }

    @Override
    protected void searchModelDataUpdated() {
        Log.d("리스트", "모델 업데이트");
        this.loadSearchResult();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.dialog.dismiss();
    }

    private void loadAdvertisement() {
        KatiRetrofitTool.getAPI().getAdFoodList().enqueue(JSHRetrofitTool.getCallback(new AdListRequestCallback(getActivity())));
    }

    private void loadSearchResult() {
//        dialog.show();

        Log.d("태그",              "페이지"+this.searchModel.getSearchPageNum()+" 정렬기준"+
                this.searchModel.getFoodSortElement()+" 검색어"+
                this.searchModel.getSearchText()+" 더불러오기"+this.isLoadingMore);

        if (!this.isLoadingMore) this.vector.clear();

        if (this.searchModel.getSearchMode().equals(Constant.ESearchMode.제품.name())) {
            KatiRetrofitTool.getAPI().getNameFoodListBySorting(
                    this.searchModel.getSearchPageNum(),
                    this.searchModel.getPageSize(),
                    this.searchModel.getFoodSortElement(),
                    this.searchModel.getSearchText(),
                    this.searchModel.isFiltered() ? this.searchModel.getAllergyList() : null
            ).enqueue(JSHRetrofitTool.getCallback(new SearchRequestCallback(this.getActivity())));
        } else {
            KatiRetrofitTool.getAPI().getManufacturerFoodListBySorting(
                    this.searchModel.getSearchPageNum(),
                    this.searchModel.getPageSize(),
                    this.searchModel.getFoodSortElement(),
                    this.searchModel.getSearchText(),
                    this.searchModel.isFiltered() ? this.searchModel.getAllergyList() : null
            ).enqueue(JSHRetrofitTool.getCallback(new SearchRequestCallback(this.getActivity())));
        }
    }


    /**
     * Callback
     */
    private class AdListRequestCallback extends SimpleRetrofitCallBackImpl<List<AdvertisementResponse>> {
        public AdListRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<List<AdvertisementResponse>> response) {
            Vector<AdvertisementResponse> items = new Vector<>(response.body());
            adRecyclerAdapter.setItems(items);
            adFoodInfoRecyclerView.setAdapter(adRecyclerAdapter);
        }
    }

    private class SearchRequestCallback extends SimpleRetrofitCallBackImpl<FindFoodBySortingResponse> {


        public SearchRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<FindFoodBySortingResponse> response) {
            FindFoodBySortingResponse dto = response.body();

            if (dto.getPageNo() >= dto.getMaxPage()) hasNext = false;
            else hasNext = true;
            searchModel.setSearchPageNum(dto.getPageNo());

            if(!isLoadingMore)vector.clear();
            vector.addAll(response.body().getResultList());
            foodRecyclerAdapter.setItems(vector);
            foodRecyclerAdapter.notifyDataSetChanged();

            isLoadingMore=false;
        }
    }
}