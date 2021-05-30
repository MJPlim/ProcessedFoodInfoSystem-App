package com.plim.kati_app.kati.domain.old.search.foodInfo.view.review;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiLoginCheckViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.old.model.CreateAndUpdateReviewRequest;
import com.plim.kati_app.kati.domain.old.model.CreateReviewResponse;

import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit2.Response;

/**
 * 리뷰를 작성하는 fragment.
 */
public class ReviewWriteFragment extends KatiLoginCheckViewModelFragment {

 //associate
    //view
    private ImageView productImage;
    private TextView productName, manufacturer;
    private EditText reviewValue;
    private Button saveButton;

    //component
    private Vector<Star> stars;

    //working variable
    private int score=5;
    private Long foodId;


    public ReviewWriteFragment() {
        this.stars = new Vector<>();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_review_write;
    }

    @Override
    protected void associateView(View view) {
        this.productImage = view.findViewById(R.id.writeReviewFragment_productImageView);
        this.productName = view.findViewById(R.id.writeReviewFragment_productName);
        this.manufacturer = view.findViewById(R.id.writeReviewFragment_manufacturerName);
        this.reviewValue = view.findViewById(R.id.writeReviewFragment_writeReviewEditText);
        this.saveButton = view.findViewById(R.id.writeReviewFragment_saveButton);
        for (EReviewStar reviewStar : EReviewStar.values())
            this.stars.add(new Star(view.findViewById(reviewStar.getId())));
        this.scoreStar(view.findViewById(EReviewStar.fifth.getId()));
    }

    @Override
    protected void initializeView() {
        this.saveButton.setOnClickListener(v -> this.saveReview());
        Intent intent = this.getActivity().getIntent();
        Glide.with(getContext()).load(intent.getStringExtra("photo")).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(productImage);
        this.productName.setText(intent.getStringExtra("foodName"));
        this.manufacturer.setText(intent.getStringExtra("manufacturerName").split("_")[0]);
        this.foodId=intent.getLongExtra("foodId",0L);
        this.reviewValue.setText("");
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
    protected void katiEntityUpdatedAndNoLogin() { }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
//                                    Intent intent = new Intent(getActivity(), FoodInfoActivity.class);
//                                    intent.putExtra("foodId",foodId);
//                                    intent.putExtra("isAd", false);
//                                    startActivity(intent);
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
        first(R.id.writeReviewFragment_star1),
        second(R.id.writeReviewFragment_star2),
        third(R.id.writeReviewFragment_star3),
        fourth(R.id.writeReviewFragment_star4),
        fifth(R.id.writeReviewFragment_star5);
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