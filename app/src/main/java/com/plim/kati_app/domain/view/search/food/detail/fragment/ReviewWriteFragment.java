package com.plim.kati_app.domain.view.search.food.detail.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.dto.CreateAndUpdateReviewRequest;
import com.plim.kati_app.domain.model.dto.CreateReviewResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.search.food.detail.NewDetailActivity;
import com.plim.kati_app.tech.RestAPIClient;

import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 리뷰를 작성하는 fragment.
 */
public class ReviewWriteFragment extends Fragment {

    private Vector<Star> stars;
    private ImageView productImage;
    private TextView productName, manufacturer;
    private EditText reviewValue;
    private Button saveButton;
    private long foodId;
    private int score;
    private long reviewId;


    public ReviewWriteFragment() {
        this.stars = new Vector<>();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.productImage = view.findViewById(R.id.writeReviewFragment_productImageView);
        this.productName = view.findViewById(R.id.writeReviewFragment_productName);
        this.manufacturer = view.findViewById(R.id.writeReviewFragment_manufacturerName);
        this.reviewValue = view.findViewById(R.id.writeReviewFragment_writeReviewEditText);
        this.saveButton = view.findViewById(R.id.writeReviewFragment_saveButton);

        for (EReviewStar reviewStar : EReviewStar.values())
            this.stars.add(new Star(view.findViewById(reviewStar.getId())));
        this.scoreStar(view.findViewById(EReviewStar.fifth.getId()));

        Intent intent = getActivity().getIntent();
        Glide.with(getContext()).load(intent.getStringExtra("image")).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(productImage);
        this.productName.setText(intent.getStringExtra("product"));
        this.manufacturer.setText(intent.getStringExtra("manufacturer").split("_")[0]);
        this.reviewValue.setText(intent.getStringExtra("value"));
        this.foodId = intent.getLongExtra("foodId", 0L);
        this.reviewId = intent.getLongExtra("reviewId", 0L);
        int temp = intent.getIntExtra("score", 0);
        this.score = temp != 0 ? temp : 5;
        this.scoreStar(this.stars.get(score - 1).getImageView());

        this.saveButton.setOnClickListener(v -> {
            this.saveReview();
        });
    }


    private void scoreStar(ImageView oneStar) {
        int score = 0;
        boolean startColor = false;
        for (int i = stars.size() - 1; i > -1; i--) {
            if (stars.get(i).getImageView().equals(oneStar)) {
                startColor = true;
                score = i + 1;
            }
            stars.get(i).setFlag(startColor);
        }
        this.score = score;
    }


    private void saveReview() {
        new Thread(() -> {
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
            Call<CreateReviewResponse> call;
            CreateAndUpdateReviewRequest request = new CreateAndUpdateReviewRequest();
            request.setReviewDescription(this.reviewValue.getText().toString());
            request.setReviewRating(this.score);
            if (reviewId == 0L) {
                request.setFoodId(this.foodId);
                call = RestAPIClient.getApiService2(token).createReview(request);
            } else {
                request.setReviewId(this.reviewId);
                call = RestAPIClient.getApiService2(token).updateReview(request);
            }
            call.enqueue(new Callback<CreateReviewResponse>() {
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
                                    Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                                    intent.putExtra("foodId", foodId);
                                    intent.putExtra("isAd", false);
                                    startActivity(intent);
                                },
                                getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                        ).showDialog();
                    }
                    new Thread(() -> {
                        katiDatabase.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, response.headers().get(KatiDatabase.AUTHORIZATION)));
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
        return inflater.inflate(R.layout.fragment_review_write, container, false);
    }

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
            this.flag = flag;
            this.imageView.setImageDrawable(getResources().getDrawable(
                    !flag ?
                            R.drawable.icon_star :
                            R.drawable.icon_star_filled,
                    getContext().getTheme()
            ));
            this.imageView.clearColorFilter();
            this.imageView.setColorFilter(
                    getResources().getColor(
                            !flag ?
                                    R.color.kati_yellow :
                                    R.color.kati_orange,
                            getContext().getTheme()
                    ),
                    PorterDuff.Mode.SRC_IN);
        }
    }

    @AllArgsConstructor
    @Getter
    private enum EReviewStar {
        first(R.id.writeReviewFragment_star1),
        second(R.id.writeReviewFragment_star2),
        third(R.id.writeReviewFragment_star3),
        fourth(R.id.writeReviewFragment_star4),
        fifth(R.id.writeReviewFragment_star5);
        private int id;
    }
}