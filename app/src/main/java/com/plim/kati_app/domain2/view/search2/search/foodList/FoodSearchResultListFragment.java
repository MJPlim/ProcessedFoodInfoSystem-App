package com.plim.kati_app.domain2.view.search2.search.foodList;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.constant.Constant_yun;
import com.plim.kati_app.domain2.katiCrossDomain.domain.model.forBackend.searchFood.AdvertisementResponse;
import com.plim.kati_app.domain2.katiCrossDomain.domain.model.forBackend.searchFood.FoodResponse;
import com.plim.kati_app.domain2.katiCrossDomain.domain.view.KatiFoodFragment;
import com.plim.kati_app.domain2.katiCrossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.domain2.view.search.list.adapter.FoodInfoRecyclerViewAdapter;
import com.plim.kati_app.domain2.view.search2.search.foodList.advertisement.AdRecyclerAdapter;
import com.plim.kati_app.domain2.view.search2.search.foodList.searchResult.FoodRecyclerAdapter;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;

public class FoodSearchResultListFragment extends KatiFoodFragment {

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
    protected int getLayoutId() { return R.layout.fragment_food_search_result_list; }
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
        this.foodInfoRecyclerView.setAdapter(new FoodInfoRecyclerViewAdapter(5));
        this.adFoodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.adFoodInfoRecyclerView.setAdapter(this.foodRecyclerAdapter);
    }
    @Override
    protected void katiEntityUpdated() {
        this.loadAdvertisement();
        this.loadSearchResult();
    }
    private void loadAdvertisement() {
        KatiRetrofitTool.getAPI().getAdvertisementFoodList().enqueue(JSHRetrofitTool.getCallback(new AdListRequestCallback()));
    }
    private void loadSearchResult() {
        dialog.show();
        if (this.foodModel.getSearchMode().equals(Constant_yun.ESearchMode.제품.name())) {
            KatiRetrofitTool.getAPI().getSearchResultByFoodName(this.foodModel.getSearchText()).enqueue(JSHRetrofitTool.getCallback(new SearchRequestCallback()));
        } else {
            KatiRetrofitTool.getAPI().getSearchResultByCompanyName(this.foodModel.getSearchText()).enqueue(JSHRetrofitTool.getCallback(new SearchRequestCallback()));
        }
    }

    /**
     * Callback
     */
    private class AdListRequestCallback implements JSHRetrofitCallback<List<AdvertisementResponse>> {
        @Override
        public void onSuccessResponse(Response<List<AdvertisementResponse>> response) {
            Vector<AdvertisementResponse> items = new Vector<>(response.body());
            adRecyclerAdapter.setItems(items);
            adFoodInfoRecyclerView.setAdapter(adRecyclerAdapter);
        }
        @Override
        public void onFailResponse(Response<List<AdvertisementResponse>> response) { }
        @Override
        public void onConnectionFail(Throwable t) {
            dialog.hide();
            KatiDialog.simplerAlertDialog(getActivity(),
                FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE, t.getMessage(),
                null
            );
        }
    }
    private class SearchRequestCallback implements JSHRetrofitCallback<List<FoodResponse>> {
        @Override
        public void onSuccessResponse(Response<List<FoodResponse>> response) {
            Vector<FoodResponse> items = new Vector<>(response.body());
            dialog.hide();
            foodRecyclerAdapter.setItems(items);
            foodInfoRecyclerView.setAdapter(foodRecyclerAdapter);
        }
        @Override
        public void onFailResponse(Response<List<FoodResponse>> response) { }
        @Override
        public void onConnectionFail(Throwable t) {
            dialog.hide();
            KatiDialog.simplerAlertDialog(getActivity(),
                FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE, t.getMessage(),
                null
            );
        }
    }
}