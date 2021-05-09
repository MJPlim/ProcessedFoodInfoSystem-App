package com.plim.kati_app.domain.view.search.food.detail.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.GetResultFragment;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.RegisterActivityViewModel;
import com.plim.kati_app.domain.model.dto.CreateReviewRequest;
import com.plim.kati_app.domain.model.dto.CreateReviewResponse;
import com.plim.kati_app.domain.model.dto.ReviewViewmodel;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.tech.GlideApp;
import com.plim.kati_app.tech.RestAPIClient;

import java.util.Vector;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewWriteFragment extends Fragment {

    private Vector<Star> stars;
    private ImageView productImage;
    private TextView productName, manufacturer;
    private EditText reviewValue;
    private Button saveButton;
    private long foodId;
    private int score;

    @Getter
    private class Star {
        private ImageView imageView;
        private Boolean flag;

        public Star(ImageView imageView) {
            this.imageView = imageView;
            imageView.setOnClickListener(v -> scoreStar(this.imageView));
            setFlag(false);
        }

        public void setFlag(boolean flag) {
//            Log.d("값",flag+"");
            this.flag = flag;
            if(flag)
                this.imageView.getDrawable().clearColorFilter();
            this.imageView.getDrawable().setColorFilter(getResources().getColor(!flag?R.color.kati_yellow:R.color.kati_orange,getContext().getTheme()),PorterDuff.Mode.SRC_IN);
        }
    }

    public ReviewWriteFragment() {
        this.stars = new Vector<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.productImage = view.findViewById(R.id.writeReviewFragment_productImageView);
        this.stars.add(new Star(view.findViewById(R.id.writeReviewFragment_star1)));
        this.stars.add(new Star(view.findViewById(R.id.writeReviewFragment_star2)));
        this.stars.add(new Star(view.findViewById(R.id.writeReviewFragment_star3)));
        this.stars.add(new Star(view.findViewById(R.id.writeReviewFragment_star4)));
        this.stars.add(new Star(view.findViewById(R.id.writeReviewFragment_star5)));


        this.productName = view.findViewById(R.id.writeReviewFragment_productName);
        this.manufacturer = view.findViewById(R.id.writeReviewFragment_manufacturerName);

        this.reviewValue = view.findViewById(R.id.writeReviewFragment_writeReviewEditText);
        this.saveButton = view.findViewById(R.id.writeReviewFragment_saveButton);

        this.scoreStar(view.findViewById(R.id.writeReviewFragment_star5));


      Intent intent= getActivity().getIntent();

        Glide.with(getContext()).load(intent.getStringExtra("image")).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(productImage);
        this.productName.setText(intent.getStringExtra("product"));
        this.manufacturer.setText(intent.getStringExtra("manufacturer"));
        this.reviewValue.setText(intent.getStringExtra("value"));
        this.foodId = intent.getLongExtra("foodId",0L);
        this.scoreStar(view.findViewById(R.id.writeReviewFragment_star5));
        this.saveButton.setOnClickListener(v -> {
            Log.d("디버그", "버튼눌림");
            this.saveReview();
        });

    }

//    @Override
//    public void setFragmentRequestKey() {
//        this.fragmentRequestKey = "formBundle";
//    }
//
//    @Override
//    public void ResultParse(String requestKey, Bundle result) {
//
//        Log.d("e디버그","받음받음");
//        Glide.with(getContext()).load(result.getString("image")).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(productImage);
//        this.productName.setText(result.getString("product"));
//        this.manufacturer.setText(result.getString("manufacturer"));
////        this.reviewValue.setText(result.get);
//        this.foodId = result.getLong("foodId");
//        this.saveButton.setOnClickListener(v -> {
//            Log.d("디버그", "버튼눌림");
//            this.saveReview();
//        });
//    }


    private void scoreStar(ImageView oneStar) {

        int score = 0;
        boolean startColor = false;

        for (int i = stars.size()-1; i > -1; i--) {
            if (stars.get(i).getImageView().equals(oneStar)) {
                startColor = true;
                score = i + 1;
            }
                    Log.d("디버그"+i,"별 그리기"+startColor);
            stars.get(i).setFlag(startColor);
        }
        this.score = score;
    }


    private void saveReview() {
        new Thread(() -> {
            CreateReviewRequest request = new CreateReviewRequest();
            request.setFoodId(this.foodId);
            request.setReviewDescription(this.reviewValue.getText().toString());
            request.setReviewRating(this.score);

            KatiDatabase katiDatabase = KatiDatabase.getAppDatabase(getContext());
            String token = katiDatabase.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
            if (token == null) {
                KatiDialog.simpleAlertDialog(
                        getContext(),
                        "리뷰 작성 오류",
                        "리뷰를 남기기 위해서는 로그인해야 합니다.",
                        null,
                        getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                ).showDialog();
                return;
            }
            RestAPIClient.getApiService2(token).createReview(request).enqueue(new Callback<CreateReviewResponse>() {
                @Override
                public void onResponse(Call<CreateReviewResponse> call, Response<CreateReviewResponse> response) {
                    if (!response.isSuccessful()) {
                        KatiDialog.showRetrofitNotSuccessDialog(getContext(), response.code() + "", null);
                    } else {
                        KatiDialog.simpleAlertDialog(
                                getContext(),
                                "리뷰 저장 완료",
                                "성공적으로 리뷰를 저장하였습니다.",
                                (dialog, which) -> {
                                    Intent intent =new Intent(getActivity(),DetailProductInfoFragment.class);
                                    intent.putExtra("foodId",foodId);
                                    intent.putExtra("isAd",false);
                                    startActivity(intent);
                                },
                                getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                        ).showDialog();
                    }
                    new Thread(() -> {
                        String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                        katiDatabase.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
                    }).start();
                }

                @Override
                public void onFailure(Call<CreateReviewResponse> call, Throwable t) {
                    KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null);
                }
            });
        }).start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_write, container, false);
    }


}