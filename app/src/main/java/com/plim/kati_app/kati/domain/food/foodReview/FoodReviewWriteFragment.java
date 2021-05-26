package com.plim.kati_app.kati.domain.food.foodReview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiLoginCheckViewModelFragment;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.search.foodInfo.view.FoodInfoActivity;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.CreateAndUpdateReviewRequest;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.CreateReviewResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.view.review.ReviewWriteFragment;

import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit2.Response;


public class FoodReviewWriteFragment extends KatiLoginCheckViewModelFragment {

    //component
    //view
    private ImageView productImage,photoInsertButton;
    private TextView productName, manufacturer;
    private EditText reviewValue;
    private Button submitButton;

    //working variable
    private int score=5;
    private Long foodId;
    private Vector<Star> stars;

    public FoodReviewWriteFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_food_review_write;
    }

    @Override
    protected void associateView(View view) {
        this.productImage=view.findViewById(R.id.foodReviewWriteFragment_productImage);
        this.photoInsertButton=view.findViewById(R.id.foodReviewWriteFragment_photoInsertButton);

        this.productName=view.findViewById(R.id.foodReviewWriteFragment_productNameTextView);
        this.manufacturer=view.findViewById(R.id.foodReviewWriteFragment_manufacturerNameTextView);

        this.reviewValue=view.findViewById(R.id.foodReviewWriteFragment_reviewWriteEditText);

        this.submitButton=view.findViewById(R.id.foodReviewWriteFragment_submitButton);

        for (EReviewStar reviewStar : EReviewStar.values())
            this.stars.add(new Star(view.findViewById(reviewStar.getId())));
        this.scoreStar(view.findViewById(EReviewStar.fifth.getId()));
    }

    @Override
    protected void initializeView() {
        this.submitButton.setOnClickListener(v -> this.saveReview());
        this.photoInsertButton.setOnClickListener(v-> Toast.makeText(getActivity(), "사진 첨부 미구현", Toast.LENGTH_SHORT).show());
        Intent intent = this.getActivity().getIntent();
        Glide.with(getContext()).load(intent.getStringExtra("photo")).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(productImage);
        this.productName.setText(intent.getStringExtra("foodName"));
        this.manufacturer.setText(intent.getStringExtra("manufacturerName").split("_")[0]);
        this.foodId=intent.getLongExtra("foodId",0L);
        this.scoreStar(stars.get(score - 1).getImageView());
    }

    @Override
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    private class CreateAndUpdateReviewCallback extends SimpleLoginRetrofitCallBack<CreateReviewResponse> {

        public CreateAndUpdateReviewCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<CreateReviewResponse> response) {
            KatiDialog.simplerAlertDialog(
                    getActivity(),
                    "리뷰 저장 완료",
                    "성공적으로 리뷰를 저장하였습니다.",
                    (dialog, which) -> {
                        Intent intent = new Intent(getActivity(), FoodInfoActivity.class);
                        intent.putExtra("foodId",foodId);
                        intent.putExtra("isAd", false);
                        startActivity(intent);
                    }
            );
        }
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
        first(R.id.foodReviewWriteFragment_firstStar),
        second(R.id.foodReviewWriteFragment_secondStar),
        third(R.id.foodReviewWriteFragment_thirdStar),
        fourth(R.id.foodReviewWriteFragment_fourthStar),
        fifth(R.id.foodReviewWriteFragment_fifthStar);
        private int id;
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
        String token= this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

        CreateAndUpdateReviewRequest request= new CreateAndUpdateReviewRequest();
        request.setReviewDescription(this.reviewValue.getText().toString());
        request.setReviewRating(this.score);
        request.setFoodId(this.foodId);

        KatiRetrofitTool.getAPIWithAuthorizationToken(token).createReview(request).enqueue(JSHRetrofitTool.getCallback(new CreateAndUpdateReviewCallback(this.getActivity())));
    }

}