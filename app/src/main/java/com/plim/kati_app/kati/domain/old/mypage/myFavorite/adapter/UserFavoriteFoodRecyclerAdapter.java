package com.plim.kati_app.kati.domain.old.mypage.myFavorite.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.old.mypage.myFavorite.model.UserFavoriteResponse;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.FoodInfoActivity;

import java.util.Vector;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID;

public class UserFavoriteFoodRecyclerAdapter extends RecyclerView.Adapter<UserFavoriteFoodViewHolder> {

    // Associate
        // ETC
        private Activity activity;
        private Vector<UserFavoriteResponse> items;

    // Constructor
    public UserFavoriteFoodRecyclerAdapter(Activity activity) {
        this.items = new Vector<>();
        this.activity=activity;
    }

    @Override
    public UserFavoriteFoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_old, parent, false);
        return new UserFavoriteFoodViewHolder(view, v->this.startNewDetailActivity((Long) v.getTag()), this.activity);
    }
    @Override
    public void onBindViewHolder(UserFavoriteFoodViewHolder holder, int position) {
        UserFavoriteResponse item = items.get(position);
        (holder).setValue(item);}
    @Override
    public int getItemCount() { return items.size(); }

    public void setItems(Vector<UserFavoriteResponse> items) { this.items=items; }
    private void startNewDetailActivity(Long foodId) {
        Log.d("디버그",foodId.toString());
        Intent intent = new Intent(this.activity, FoodInfoActivity.class);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID, foodId);
        this.activity.startActivity(intent);
    }
}
