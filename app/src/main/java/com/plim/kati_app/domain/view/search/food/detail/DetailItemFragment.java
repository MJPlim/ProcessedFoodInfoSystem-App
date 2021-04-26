package com.plim.kati_app.domain.view.search.food.detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.view.MainActivity;

public class DetailItemFragment extends Fragment {
    private ImageView favoriteImageView;
    private boolean favorite = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_item, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.favoriteImageView = view.findViewById(R.id.foodItem_favoriteImageView);


        this.favoriteImageView.setOnClickListener(v -> {
            this.favorite = !this.favorite;
            int newImageId = this.favorite ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24;
            int newTint = this.favorite ? R.color.kati_red : R.color.kati_yellow;
            this.favoriteImageView.setImageResource(newImageId);
            this.favoriteImageView.setColorFilter(ContextCompat.getColor(v.getContext(), newTint), android.graphics.PorterDuff.Mode.SRC_IN);
        });

    }

    /**
     * 메인 액티비티를 시작한다.
     */
    public void startMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}