package com.plim.kati_app.kati.domain.mypage.myFavorite.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;


import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * 마이페이지 즐겨찾기 프래그먼트.
 *
 */

public class UserFavoriteFragment extends Fragment {
//    KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
//    private TextView favoriteNum;
//    private String token;
//    private RecyclerView foodInfoRecyclerView;
//    private LoadingDialog dialog;
//    private RecyclerAdapter recyclerAdapter;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_mypage_favorite_food_list, container, false);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        new Thread(() -> {
//            // 토큰값 저장
//            token=database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
//            getFavoriteFoods();
//        }).start();
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        this.favoriteNum = view.findViewById(R.id.myPage_favorite_num);
//        this.foodInfoRecyclerView = view.findViewById(R.id.myPage_foodInfoRecyclerView);
//        this.recyclerAdapter = new RecyclerAdapter();
//        this.dialog = new LoadingDialog(getContext());
//        this.foodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        this.foodInfoRecyclerView.setAdapter(new FoodInfoRecyclerViewAdapter(5));
//    }
//
//    // 서버에서 사용자 즐겨찾기 정보를 받아와서 나타내는 메서드
//    private void getFavoriteFoods() {
//
//        Call<List<UserFavoriteResponse>> call = getApiService2(token).getUserFavorite();
//        Callback<List<UserFavoriteResponse>> callback = new Callback<List<UserFavoriteResponse>>() {
//            @Override
//            public void onResponse( Call<List<UserFavoriteResponse>> call, Response<List<UserFavoriteResponse>> response) {
//                Vector<UserFavoriteResponse> items = new Vector<>(response.body());
//                if (!response.isSuccessful()) {
//                    try {
//
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    new Thread(() -> {
//                        String token = response.headers().get(KatiDatabase.AUTHORIZATION);
//                        database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
//                    }).start();
//
//                    getActivity().runOnUiThread(() -> {
//                        //  todo
//                        dialog.hide();
//                        recyclerAdapter.setItems(items);
//                        foodInfoRecyclerView.setAdapter(recyclerAdapter);
//                        favoriteNum.setText("총 "+items.size()+"개");
//
//                    });
//                }
//            }
//            @Override
//            public void onFailure(Call<List<UserFavoriteResponse>> call, Throwable t) {
//
//            }
//        };
//        call.enqueue(callback);
//    }
//
//    private void intentDetailPage(Long foodId) {
//        this.startNewDetailActivity(foodId);
//    }
//
//    private void startNewDetailActivity(Long foodId) {
//        Intent intent = new Intent(getActivity(), NewDetailActivity.class);
//        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID, foodId);
//        startActivity(intent);
//    }
//
//    private class RecyclerAdapter extends RecyclerView.Adapter {
//
//        private Vector<UserFavoriteResponse> items;
//
//        private RecyclerAdapter() {
//            items = new Vector<>();
//        }
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            Context context = parent.getContext();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.item_food, parent, false);
//            RecyclerViewViewHolder RecyclerViewViewHolder = new RecyclerViewViewHolder(view);
//
//            return RecyclerViewViewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//            UserFavoriteResponse item = items.get(position);
//            ((RecyclerViewViewHolder) holder).setValue(item);
//        }
//
//        @Override
//        public int getItemCount() {
//            return items.size();
//        }
//
//        public void clearItems() {
//            this.items = new Vector<>();
//        }
//
//        public void addItems(Vector<UserFavoriteResponse> items) {
//            this.items.addAll(items);
//        }
//
//        public void setItems(Vector<UserFavoriteResponse> items) {
//            this.clearItems();
//            this.addItems(items);
//        }
//
//        /**
//         * 뷰 홀더.
//         */
//        private class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
//            private ImageView imageView;
//            private TextView productName, companyName;
//            private String imageAddress;
//
//            public RecyclerViewViewHolder(@NonNull View itemView) {
//                super(itemView);
//                this.productName = itemView.findViewById(R.id.foodItem_productName);
//                this.companyName = itemView.findViewById(R.id.foodItem_companyName);
//                this.imageView = itemView.findViewById(R.id.foodItem_foodImageView);
//                itemView.setOnClickListener(v -> {
//                    intentDetailPage(items.get(this.getAdapterPosition()).getFood().getFoodId());
//                });
//            }
//
//            /**
//             * 각 값을 설정한다.
//             *
//             * @param item
//             */
//            public void setValue(@NotNull UserFavoriteResponse item) {
//                this.imageAddress = item.getFood().getFoodImageAddress();
//                Glide.with(getContext()).load(this.imageAddress).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(imageView);
//
//                this.imageView.setOnClickListener(v -> {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageAddress));
//
//                    startActivity(intent);
//                });
//
//                this.productName.setText(item.getFood().getFoodName());
//                this.companyName.setText(item.getFood().getManufacturerName());
//            }
//        }
//    }
}
