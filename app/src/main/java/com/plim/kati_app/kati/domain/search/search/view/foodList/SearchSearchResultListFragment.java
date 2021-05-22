package com.plim.kati_app.kati.domain.search.search.view.foodList;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBack;
import com.plim.kati_app.kati.domain.search.search.model.AdvertisementResponse;
import com.plim.kati_app.kati.domain.search.search.view.foodList.advertisement.AdRecyclerAdapter;
import com.plim.kati_app.kati.domain.search.search.model.FindFoodBySortingResponse;
import com.plim.kati_app.kati.domain.search.search.model.FoodResponse;
import com.plim.kati_app.kati.domain.search.search.view.foodList.searchResult.FoodRecyclerAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

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

    /**
     * System Life Cycle Callback
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_food_search_result_list;
    }

    @Override
    protected void associateView(View view) {
        this.adFoodInfoRecyclerView = view.findViewById(R.id.searchFragment_adFoodInfoRecyclerView);
        this.foodInfoRecyclerView = view.findViewById(R.id.searchFragment_foodInfoRecyclerView);
    }

    @Override
    protected void initializeView() {
        this.adRecyclerAdapter = new AdRecyclerAdapter(this.getActivity());
        this.foodRecyclerAdapter = new FoodRecyclerAdapter(this.getActivity());
        this.dialog = new LoadingDialog(getContext());
        this.foodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.foodInfoRecyclerView.setAdapter(this.foodRecyclerAdapter);
        this.adFoodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.adFoodInfoRecyclerView.setAdapter(this.foodRecyclerAdapter);
    }

    @Override
    protected void katiEntityUpdated() {
        Log.d("업데이트!","업데이트됨");
        this.loadAdvertisement();
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
    private class AdListRequestCallback extends SimpleRetrofitCallBack<List<AdvertisementResponse>> {
        public AdListRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<List<AdvertisementResponse>> response) {
            Vector<AdvertisementResponse> items = new Vector<>(response.body());
            adRecyclerAdapter.setItems(items);
            adFoodInfoRecyclerView.setAdapter(adRecyclerAdapter);
        }

        @Override
        public void refreshToken(KatiEntity.EKatiData eKatiData, String authorization) {
        }
    }

    private class SearchRequestCallback extends SimpleRetrofitCallBack<FindFoodBySortingResponse> {
        public SearchRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<FindFoodBySortingResponse> response) {
//            dialog.hide();
            Vector<FoodResponse> items = new Vector<>(response.body().getResultList());
            foodRecyclerAdapter.setItems(items);
            foodRecyclerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailResponse(Response<FindFoodBySortingResponse> response) throws IOException, JSONException {
//            dialog.hide();
            super.onFailResponse(response);
        }

        @Override
        public void onConnectionFail(Throwable t) {
//            dialog.hide();
            super.onConnectionFail(t);
        }

        @Override
        public void refreshToken(KatiEntity.EKatiData eKatiData, String authorization) {
        }
    }
}