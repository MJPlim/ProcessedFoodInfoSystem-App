package com.plim.kati_app.domain2.view.main.search.list.list;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.domain.constant.Constant;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.constant.Constant_yun;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain2.model.forBackend.searchFood.FoodResponse;
import com.plim.kati_app.domain2.model.forBackend.searchFood.AdvertisementResponse;
import com.plim.kati_app.domain2.view.main.search.detail.NewDetailActivity;
import com.plim.kati_app.domain2.view.main.search.list.adapter.FoodInfoRecyclerViewAdapter;
//import com.plim.kati_app.domain.tech.GlideApp;
import com.plim.kati_app.retrofit.RestAPI;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX;
import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE;
import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT;
import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.domain.constant.Constant_yun.NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID;
import static com.plim.kati_app.domain.constant.Constant_yun.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

/**
 * 음식 검색하여 나온 리스트와 정렬화면 프래그먼트.
 * !이슈 여기랑 레코멘데이션이랑 왔다갔다 하다보면 Activity가 없다고 하는 일이 생긴다 왜지?
 */
public class FoodSearchResultListFragment extends Fragment {

    private String foodSearchMode;
    private String foodSearchText;

    //음식 리스트
    private RecyclerView adFoodInfoRecyclerView;
    private RecyclerView foodInfoRecyclerView;
    private LoadingDialog dialog;

    private RecyclerAdapter recyclerAdapter;
    private AdRecyclerAdapter adRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_search_result_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associate View


        this.adFoodInfoRecyclerView = view.findViewById(R.id.searchFragment_adFoodInfoRecyclerView);
        this.foodInfoRecyclerView = view.findViewById(R.id.searchFragment_foodInfoRecyclerView);

        this.adRecyclerAdapter = new AdRecyclerAdapter();
        this.recyclerAdapter = new RecyclerAdapter();

        this.dialog = new LoadingDialog(getContext());

        this.foodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.foodInfoRecyclerView.setAdapter(new FoodInfoRecyclerViewAdapter(5));
        this.adFoodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.adFoodInfoRecyclerView.setAdapter(this.recyclerAdapter);


        this.getActivity().getSupportFragmentManager().setFragmentResultListener(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY, getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                set(Integer.parseInt(result.getString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX)), result.getString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE), result.getString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT));
            }
        });
    }

    /**
     * 검색을 위한 정보를 넣고 시작.
     *
     * @param index 페이지 번호
     * @param mode  회사 혹은 제품
     * @param text  검색어
     */
    public void set(int index, String mode, String text) {
        foodSearchMode = mode;
        foodSearchText = text;
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
        Retrofit retrofit2 = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL)
                .build();
        RestAPI service2 = retrofit2.create(RestAPI.class);
        Call<List<AdvertisementResponse>> adListCall;
        adListCall = service2.getAdvertisementFoodList();

        adListCall.enqueue(new Callback<List<AdvertisementResponse>>() {
            @Override
            public void onResponse(Call<List<AdvertisementResponse>> call, Response<List<AdvertisementResponse>> response) {
                Vector<AdvertisementResponse> items = new Vector<>(response.body());
//                new Thread(() ->
//                        database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, response.headers().get(KatiDatabase.AUTHORIZATION)))).start();
                getActivity().runOnUiThread(() -> {
//                    dialog.hide();
                    Log.d("광고 디버그", "리스폰스 받음");
                    adRecyclerAdapter.setItems(items);
                    adFoodInfoRecyclerView.setAdapter(adRecyclerAdapter);
                });

            }

            @Override
            public void onFailure(Call<List<AdvertisementResponse>> call, Throwable t) {
                getActivity().runOnUiThread(() -> {
                    dialog.hide();
                    Log.d("광고 디버그", "실패" + t.getMessage());
                });
                KatiDialog.simpleAlertDialog(getContext(),
                        FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE,
                        t.getMessage(), null,
                        getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                ).showDialog();
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
            //android.view.WindowLeaked: Activity com.plim.kati_app.domain2.view.search.food.list.FoodSearchActivity has leaked window DecorView@420548c[FoodSearchActivity] that was originally added here
            dialog.show();
        });


//        KatiDatabase database = KatiDatabase.getAppDatabase(getContext());

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL)
                .build();
        RestAPI service = retrofit.create(RestAPI.class);
        Call<List<FoodResponse>> listCall;
        if (foodSearchMode.equals(Constant_yun.ESearchMode.제품.name())) {
            listCall = service.getSearchResultByFoodName(foodSearchText);
        } else {
            listCall = service.getSearchResultByCompanyName(foodSearchText);
        }

        listCall.enqueue(new Callback<List<FoodResponse>>() {
            @Override
            public void onResponse(Call<List<FoodResponse>> call, Response<List<FoodResponse>> response) {
                Vector<FoodResponse> items = new Vector<>(response.body());
//                new Thread(() ->
//                        database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, response.headers().get(KatiDatabase.AUTHORIZATION)))).start();
                getActivity().runOnUiThread(() -> {
                    dialog.hide();
                    recyclerAdapter.setItems(items);
                    foodInfoRecyclerView.setAdapter(recyclerAdapter);
                });

            }

            @Override
            public void onFailure(Call<List<FoodResponse>> call, Throwable t) {
                getActivity().runOnUiThread(() -> {
                    dialog.hide();
                });
                KatiDialog.simpleAlertDialog(getContext(),
                        FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE,
                        t.getMessage(), null,
                        getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                ).showDialog();
            }
        });


    }

    /**
     * 어댑터 클래스.
     */
    private class AdRecyclerAdapter extends RecyclerView.Adapter {


        private Vector<AdvertisementResponse> items;

        private AdRecyclerAdapter() {
            items = new Vector<>();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_food, parent, false);
            AdRecyclerViewViewHolder rankRecyclerViewViewHolder = new AdRecyclerViewViewHolder(view);

            return rankRecyclerViewViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            AdvertisementResponse item = items.get(position);
            ((AdRecyclerViewViewHolder) holder).setValue(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void clearItems() {
            this.items = new Vector<>();
        }

        public void addItems(Vector<AdvertisementResponse> items) {
            this.items.addAll(items);
        }

        public void setItems(Vector<AdvertisementResponse> items) {
            this.clearItems();
            this.addItems(items);
        }

        /**
         * 뷰 홀더.
         */
        private class AdRecyclerViewViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView productName, companyName;
            private String imageAddress;

            public AdRecyclerViewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.productName = itemView.findViewById(R.id.foodItem_productName);
                this.companyName = itemView.findViewById(R.id.foodItem_companyName);
                this.imageView = itemView.findViewById(R.id.foodItem_foodImageView);

                itemView.setOnClickListener(v -> {
                    intentAdPage(items.get(this.getAdapterPosition()).getId());
                });
            }

            /**
             * 각 값을 설정한다.
             *
             * @param item
             */
            public void setValue(@NotNull AdvertisementResponse item) {
                this.imageAddress = item.getFood().getFoodImageAddress();
                Glide.with(getContext()).load(this.imageAddress).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(imageView);

                this.imageView.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageAddress));
                    startActivity(intent);
                });

                this.productName.setText(item.getFood().getFoodName());
                this.companyName.setText(item.getFood().getManufacturerName());
            }
        }
    }
    /////////////////

    /**
     * 어댑터 클래스.
     */
    private class RecyclerAdapter extends RecyclerView.Adapter {

        private Vector<FoodResponse> items;

        private RecyclerAdapter() {
            items = new Vector<FoodResponse>();
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

        public void setItems(Vector<FoodResponse> items) {
            this.clearItems();
            this.addItems(items);
        }

        /**
         * 뷰 홀더.
         */
        private class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView productName, companyName;
            private String imageAddress;

            public RecyclerViewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.productName = itemView.findViewById(R.id.foodItem_productName);
                this.companyName = itemView.findViewById(R.id.foodItem_companyName);
                this.imageView = itemView.findViewById(R.id.foodItem_foodImageView);

                itemView.setOnClickListener(v -> {
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

                this.productName.setText(item.getFoodName());
                this.companyName.setText(item.getManufacturerName());
            }
        }
    }


}