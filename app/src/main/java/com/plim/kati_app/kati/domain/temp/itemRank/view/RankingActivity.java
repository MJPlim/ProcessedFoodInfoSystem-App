package com.plim.kati_app.kati.domain.temp.itemRank.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.temp.itemRank.model.ItemRankingResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingActivity extends AppCompatActivity {
    private RecyclerView rankingRecyclerView;
    private RankingViewAdapter rankingViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        this.rankingRecyclerView = findViewById(R.id.fragment_ranking_recyclerView);
        this.rankingViewAdapter = new RankingViewAdapter();
        this.rankingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.rankingRecyclerView.setAdapter(this.rankingViewAdapter);

        this.getRank();
    }

    private class GetRankingListRequestCallback implements JSHRetrofitCallback<ItemRankingResponse>{

        @Override
        public void onSuccessResponse(Response<ItemRankingResponse> response) {
            Vector<ItemRankingResponse> items = new Vector<ItemRankingResponse>((Collection<? extends ItemRankingResponse>) response.body());
            Log.d("Test", "리스폰스 받음");
            runOnUiThread(()->{
                rankingViewAdapter.setItems(items);
                rankingRecyclerView.setAdapter(rankingViewAdapter);
            });
        }

        @Override
        public void onFailResponse(Response<ItemRankingResponse> response) {
            Log.d("Test", "실패" + response.message());
        }

        @Override
        public void onConnectionFail(Throwable t) {
            Log.d("Test", "실패" + t.getMessage());
        }
    }

    private void getRank(){
    //    KatiRetrofitTool.getAPI().getRankingList().enqueue(JSHRetrofitTool.getCallback(new GetRankingListRequestCallback()));
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
                for (Constant.ERankImage image : Constant.ERankImage.values()) {
                    this.foodItem_rankImage.setImageResource(image.getImage());
                }
                this.foodItem_productName.setText(item.getFoodName());
                this.foodItem_rankAvg.setText(item.getAvgRating());

            }
        }
    }
}