package com.plim.kati_app.domain.view.rank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.FoodResponse;
import com.plim.kati_app.domain.model.ItemRankingResponse;
import com.plim.kati_app.domain.model.dto.AdvertisementResponse;
import com.plim.kati_app.domain.view.search.food.list.list.FoodSearchResultListFragment;
import com.plim.kati_app.tech.RestAPI;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;

public class RankingActivity extends AppCompatActivity {
    private RecyclerView rankingRecyclerView;
    private RankingViewAdapter rankingViewAdapter;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        this.rankingRecyclerView = findViewById(R.id.fragment_ranking_recyclerView);

        this.rankingViewAdapter = new RankingViewAdapter();

        this.rankingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.rankingRecyclerView.setAdapter(this.rankingViewAdapter);

        Thread thread = new Thread(() -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constant.URL)
                    .build();
            RestAPI service = retrofit.create(RestAPI.class);
            Call<List<ItemRankingResponse>> listCall;
            listCall = service.getRankingList();

            listCall.enqueue(new Callback<List<ItemRankingResponse>>() {
                @Override
                public void onResponse(Call<List<ItemRankingResponse>> call, Response<List<ItemRankingResponse>> response) {
                    Vector<ItemRankingResponse> items = new Vector<>(response.body());
                    Log.d("Test", "리스폰스 받음");
                    runOnUiThread(()->{
                        rankingViewAdapter.setItems(items);
                        rankingRecyclerView.setAdapter(rankingViewAdapter);
                    });
                }

                @Override
                public void onFailure(Call<List<ItemRankingResponse>> call, Throwable t) {
                    Log.d("Test", "실패" + t.getMessage());

                }
            });
        });
        thread.start();
    }

    private class RankingViewAdapter extends RecyclerView.Adapter {
        // Component
        private Vector<ItemRankingResponse> items;

        // Constructor
        public RankingViewAdapter() { this.items = new Vector<>();
        }

        @NotNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
            return new RankingItemViewAdapterHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemRankingResponse item = items.get(position);
            ((RankingItemViewAdapterHolder) holder).setValue(item);
        }

        @Override
        public int getItemCount() { return this.items.size(); }

        public void clearItems() {
            this.items = new Vector<>();
        }

        public void addItems(Vector<ItemRankingResponse> items) {
            this.items.addAll(items);
        }

        public void setItems(Vector<ItemRankingResponse> items) {
            this.clearItems();
            this.addItems(items);
        }

        private class RankingItemViewAdapterHolder extends RecyclerView.ViewHolder {
            // Associate
            // View
            public ImageView foodItem_rankImage;
            public TextView foodItem_productName;
            public TextView foodItem_rankAvg;

            public RankingItemViewAdapterHolder(View itemView) {
                super(itemView);
                // Associate View
                this.foodItem_rankImage = itemView.findViewById(R.id.foodItem_rankImage);
                this.foodItem_productName = itemView.findViewById(R.id.foodItem_productName);
                this.foodItem_rankAvg = itemView.findViewById(R.id.foodItem_rankAvg);
            }

            /**
             * 각 값을 설정한다.
             *
             * @param item
             */
            public void setValue(@NotNull ItemRankingResponse item) {

                // enum으로 처리할 것
                // enum { number, image}
                // setImageResource(image)....
                if(count==0) {this.foodItem_rankImage.setImageResource(R.drawable.first);}
                else if(count==1) {this.foodItem_rankImage.setImageResource(R.drawable.second);}
                else if(count==2) {this.foodItem_rankImage.setImageResource(R.drawable.third);}
                else if(count==3) {this.foodItem_rankImage.setImageResource(R.drawable.four);}
                else if(count==4) {this.foodItem_rankImage.setImageResource(R.drawable.five);}
                else if(count==5) {this.foodItem_rankImage.setImageResource(R.drawable.six);}
                else if(count==6) {this.foodItem_rankImage.setImageResource(R.drawable.seven);}
                else if(count==7) {this.foodItem_rankImage.setImageResource(R.drawable.eight);}
                else if(count==8) {this.foodItem_rankImage.setImageResource(R.drawable.nine);}
                else {this.foodItem_rankImage.setImageResource(R.drawable.ten);}

              //  this.foodItem_rankImage.setImageResource(R.drawable.first);
                this.foodItem_productName.setText(item.getFoodName());
                this.foodItem_rankAvg.setText(item.getAvgRating());
                count++;
            }
        }
    }

}