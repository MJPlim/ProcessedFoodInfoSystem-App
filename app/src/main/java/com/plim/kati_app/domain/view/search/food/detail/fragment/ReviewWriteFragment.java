package com.plim.kati_app.domain.view.search.food.detail.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

    private ReviewViewmodel viewModel;
    private Vector<Star> stars;
    private ImageView productImage;
    private TextView productName, manufacturer;
    private EditText reviewValue;
    private Button saveButton;

    @Getter
    private class Star{
        private ImageView imageView;
        private Boolean flag;
        public Star(ImageView imageView) {
            this.imageView = imageView;
            imageView.setOnClickListener(v-> scoreStar(this.imageView));
            setFlag(false);
        }
        public void setFlag(boolean flag){
            this.flag=flag;
            this.imageView.setColorFilter(!flag?R.color.kati_yellow:R.color.kati_orange);
        }
    }

    public ReviewWriteFragment() {
        this.stars= new Vector<>();
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

        Glide.with(getContext()).load(this.viewModel.getImageUrl()).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(productImage);
        this.productName = view.findViewById(R.id.writeReviewFragment_productName);
        this.manufacturer = view.findViewById(R.id.writeReviewFragment_manufacturerName);

        this.reviewValue = view.findViewById(R.id.writeReviewFragment_writeReviewEditText);
        this.saveButton = view.findViewById(R.id.writeReviewFragment_saveButton);

        this.viewModel = new ViewModelProvider(this.requireActivity()).get(ReviewViewmodel.class);

        this.productName.setText(this.viewModel.getProductName());
        this.manufacturer.setText(this.viewModel.getManufacturerName());
        this.reviewValue.setText(this.viewModel.getReviewValue());
        this.saveButton.setOnClickListener(v -> this.saveReview());
    }


    private void scoreStar(ImageView oneStar){
        int score=0;
        boolean startColor=false;
        for(int i=stars.size()-1;i>0;i--){
            if(stars.get(i).getImageView().equals(oneStar)){
                startColor=true;
                score=i+1;
            }
            stars.get(i).setFlag(startColor);
        }
        this.viewModel.setScore(score);
    }


    private void saveReview() {

        CreateReviewRequest request = new CreateReviewRequest();
        request.setFoodId(this.viewModel.getFoodId());
        request.setReviewDescription(this.reviewValue.length()!=0? this.reviewValue.getText().toString():this.viewModel.getReviewValue());
        request.setReviewRating(this.viewModel.getScore());

        KatiDatabase katiDatabase = KatiDatabase.getAppDatabase(getContext());
        String token = katiDatabase.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
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
                            null,
                            getContext().getResources().getColor(R.color.kati_coral,getContext().getTheme())
                    ).showDialog();
                }
                String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                katiDatabase.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
            }

            @Override
            public void onFailure(Call<CreateReviewResponse> call, Throwable t) {
                KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_write, container, false);
    }


}