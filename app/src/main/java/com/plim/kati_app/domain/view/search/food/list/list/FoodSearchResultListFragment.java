package com.plim.kati_app.domain.view.search.food.list.list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.GetResultFragment;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.FoodResponse;
import com.plim.kati_app.domain.model.dto.AdvertisementResponse;
import com.plim.kati_app.domain.model.dto.FindFoodBySortingResponse;
import com.plim.kati_app.domain.view.search.food.detail.NewDetailActivity;
import com.plim.kati_app.domain.view.search.food.list.adapter.FoodInfoRecyclerViewAdapter;
//import com.plim.kati_app.tech.GlideApp;
import com.plim.kati_app.tech.RestAPI;
import com.plim.kati_app.tech.RestAPIClient;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_SORT;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT;
import static com.plim.kati_app.constants.Constant_yun.NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID;
import static com.plim.kati_app.constants.Constant_yun.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

/**
 * 음식 검색하여 나온 리스트와 정렬화면 프래그먼트.
 * !이슈 여기랑 레코멘데이션이랑 왔다갔다 하다보면 Activity가 없다고 하는 일이 생긴다 왜지?
 */
public class FoodSearchResultListFragment extends GetResultFragment {

    private String foodSearchMode;
    private String foodSearchText;
    private String foodSortElement;
    private boolean isFiltered = false;
    private Vector<String> allergyList;

    //음식 리스트
    private RecyclerView adFoodInfoRecyclerView;
    private RecyclerView foodInfoRecyclerView;
    private LoadingDialog dialog;

    private RecyclerAdapter recyclerAdapter;
    private RecyclerAdapter adRecyclerAdapter;
    private int pageNum = 1;
    private int pageSize = 10;

    public FoodSearchResultListFragment() {
        this.allergyList = new Vector<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_search_result_list, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.dialog.dismiss();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associate View
        this.adFoodInfoRecyclerView = view.findViewById(R.id.searchFragment_adFoodInfoRecyclerView);
        this.foodInfoRecyclerView = view.findViewById(R.id.searchFragment_foodInfoRecyclerView);

        this.adRecyclerAdapter = new RecyclerAdapter();
        this.recyclerAdapter = new RecyclerAdapter();

        this.dialog = new LoadingDialog(getContext());

        this.foodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.foodInfoRecyclerView.setAdapter(new FoodInfoRecyclerViewAdapter(5));
        this.adFoodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.adFoodInfoRecyclerView.setAdapter(this.recyclerAdapter);


        this.getActivity().getSupportFragmentManager().setFragmentResultListener(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY, getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                int pageNum = result.getInt(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX);
                String sort = result.getString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_SORT);
                String mode = result.getString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE);
                String text = result.getString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT);
//                Log.d("디버그",sort+" 널");
                set(pageNum, sort, mode, text);
            }
        });
    }

    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey = "allergyFilter";
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
        this.isFiltered = result.getBoolean("flag");
        this.allergyList = (Vector<String>) result.getSerializable("allergy");
        this.refresh();
    }


    private void refresh() {
        this.set(0, null, null, null);
    }

    public void set(int pageNum, String foodSortElement, String mode, String text) {
        if (pageNum != 0) this.pageNum = pageNum;
        if (foodSortElement != null) this.foodSortElement = foodSortElement;
        if (mode != null) this.foodSearchMode = mode;
        if (text != null) this.foodSearchText = text;

        Log.d("디버그 세팅", "페이지 번호: " + this.pageNum);
        Log.d("디버그 세팅", "정렬: " + this.foodSortElement);
        Log.d("디버그 세팅", "검색모드: " + this.foodSearchMode);
        Log.d("디버그 세팅", "검색 텍스트: " + this.foodSearchText);
        Thread thread = new Thread(() -> {
            this.search();
            this.ad();
        });
        thread.start();
    }

    /**
     * 광고 불러오기.
     */
    private void ad() {
        Call<List<AdvertisementResponse>> adListCall = RestAPIClient.getApiService().getAdFoodList();
        adListCall.enqueue(new Callback<List<AdvertisementResponse>>() {
            @Override
            public void onResponse(Call<List<AdvertisementResponse>> call, Response<List<AdvertisementResponse>> response) {
                if (!response.isSuccessful()) {
                    KatiDialog.showRetrofitNotSuccessDialog(getContext(), response.code() + "", null);
                } else {
                    Vector<FoodResponse> items = new Vector<>();
                    Vector<AdvertisementResponse> responseVector = new Vector<>(response.body());
                    for (AdvertisementResponse advertisementResponse : responseVector) {
                        FoodResponse response1 = advertisementResponse.getFood();
                        response1.setFoodId(advertisementResponse.getId());
                        items.add(response1);
                    }
                    getActivity().runOnUiThread(() -> {
                        Log.d("광고 디버그", "리스폰스 받음");
                        adRecyclerAdapter.setItems(items, true);
                        adFoodInfoRecyclerView.setAdapter(adRecyclerAdapter);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<AdvertisementResponse>> call, Throwable t) {
                getActivity().runOnUiThread(() -> {
                    dialog.hide();
                });
                KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null);
            }
        });
    }

    private void startNewDetailActivity(Long foodId, boolean isAd) {
        Intent intent = new Intent(getActivity(), NewDetailActivity.class);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID, foodId);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, isAd);
        startActivity(intent);
    }

    private void intentAdPage(Long foodId) {
        this.startNewDetailActivity(foodId, true);
    }

    private void intentDetailPage(Long foodId) {
        this.startNewDetailActivity(foodId, false);
    }

    /**
     * 검색하는 메소드.
     */
    private void search() {
        //java.lang.IllegalStateException: Fragment FoodSearchResultListFragment{9275fcf} (9f1df36e-8aa9-4197-adb4-b353110ab62e)} not attached to an activity.
        requireActivity().runOnUiThread(() -> {
            //android.view.WindowLeaked: Activity com.plim.kati_app.domain.view.search.food.list.FoodSearchActivity has leaked window DecorView@420548c[FoodSearchActivity] that was originally added here
            dialog.show();
        });

        RestAPI service = RestAPIClient.getApiService();


        Call<FindFoodBySortingResponse> listCall;
        if (foodSearchMode.equals(Constant_yun.ESearchMode.제품.name())) {
            listCall = service.getNameFoodListBySorting(this.pageNum, this.pageSize, this.foodSortElement, foodSearchText, isFiltered ? allergyList : null);
        } else {
            listCall = service.getManufacturerFoodListBySorting(this.pageNum, this.pageSize, this.foodSortElement, foodSearchText, isFiltered ? allergyList : null);
        }

        listCall.enqueue(new Callback<FindFoodBySortingResponse>() {
            @Override
            public void onResponse(Call<FindFoodBySortingResponse> call, Response<FindFoodBySortingResponse> response) {
                if (!response.isSuccessful()) {
                    getActivity().runOnUiThread(() -> dialog.hide());
                    KatiDialog.showRetrofitNotSuccessDialog(getContext(), response.code() + "", null);
                } else {
                    Vector<FoodResponse> items = new Vector<>(response.body().getResultList());
                    getActivity().runOnUiThread(() -> {
                        dialog.hide();
                        recyclerAdapter.setItems(items, false);
                        foodInfoRecyclerView.setAdapter(recyclerAdapter);
                    });
                }
            }

            @Override
            public void onFailure(Call<FindFoodBySortingResponse> call, Throwable t) {
                getActivity().runOnUiThread(() -> {
                    dialog.hide();
                });
                KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null);
            }
        });
    }


    /**
     * 어댑터 클래스.
     */
    private class RecyclerAdapter extends RecyclerView.Adapter {

        private Vector<FoodResponse> items;
        private boolean isAd;

        private RecyclerAdapter() {
            items = new Vector<>();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_food, parent, false);
            RecyclerViewViewHolder rankRecyclerViewViewHolder = new RecyclerViewViewHolder(view);
            return rankRecyclerViewViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            FoodResponse item = items.get(position);
            ((RecyclerViewViewHolder) holder).setValue(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void clearItems() {
            this.items = new Vector<>();
        }

        public void addItems(Vector<FoodResponse> items) {
            this.items.addAll(items);
        }

        public void setItems(Vector<FoodResponse> items, boolean isAd) {
            this.isAd = isAd;
            this.clearItems();
            this.addItems(items);
        }

        /**
         * 뷰 홀더.
         */
        private class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView productName, companyName, reviewCount, score;
            private String imageAddress;


            public RecyclerViewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.productName = itemView.findViewById(R.id.foodItem_productName);
                this.companyName = itemView.findViewById(R.id.foodItem_companyName);
                this.imageView = itemView.findViewById(R.id.foodItem_foodImageView);
                this.reviewCount = itemView.findViewById(R.id.foodItem_reviewCountTextView);
                this.score = itemView.findViewById(R.id.foodItem_scoreTextView);
                itemView.setOnClickListener(v -> {
                    if (isAd)
                        intentAdPage(items.get(this.getAdapterPosition()).getFoodId());
                    else
                        intentDetailPage(items.get(this.getAdapterPosition()).getFoodId());
                });
            }

            /**
             * 각 값을 설정한다.
             *
             * @param item
             */
            public void setValue(@NotNull FoodResponse item) {
                this.imageAddress = item.getFoodImageAddress();
                Glide.with(getContext()).load(this.imageAddress).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(imageView);
                this.imageView.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageAddress));
                    startActivity(intent);
                });
                this.score.setText(item.getReviewRate() == null ? "0.00" : item.getReviewRate());
                this.reviewCount.setText("(" + item.getReviewCount() + ")");
                this.productName.setText(item.getFoodName());
                this.companyName.setText(item.getManufacturerName().split("_")[0]);
            }
        }
    }


}