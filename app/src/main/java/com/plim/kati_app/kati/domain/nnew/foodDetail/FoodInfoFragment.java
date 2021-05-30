package com.plim.kati_app.kati.domain.nnew.foodDetail;

import android.app.Activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHInfoItem;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiFoodFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.old.search.foodInfo.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.old.model.FindFoodByBarcodeRequest;
import com.varunest.sparkbutton.SparkButton;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ADD_FAVORITE_RESULT_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ADD_FAVORITE_RESULT_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DELETE_FAVORITE_RESULT_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DELETE_FAVORITE_RESULT_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class FoodInfoFragment extends KatiFoodFragment {

    //associate
    //view
    private ImageView foodImageView,starIcon;
    private TextView foodNameTextView,ratingTextView, reviewCountTextView;
    private JSHInfoItem materialItem,ingredientItem;
    private SparkButton heartButton;


    // Working Variable
    private boolean isFront = true;
    private boolean isFavorite = false;

    //attribute
    private String barcode;
    private Long foodId;
    private boolean isAd;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_food_info;
    }

    @Override
    protected void associateView(View view) {
        this.foodImageView=view.findViewById(R.id.foodItemFragment_foodImageView);
        this.starIcon=view.findViewById(R.id.foodItemFragment_starIcon);

        this.foodNameTextView=view.findViewById(R.id.foodItemFragment_foodNameTextView);
        this.ratingTextView=view.findViewById(R.id.foodItemFragment_ratingTextView);
        this.reviewCountTextView=view.findViewById(R.id.foodItemFragment_reviewCountTextView);

        this.materialItem=view.findViewById(R.id.foodItemFragment_materialItem);
        this.ingredientItem=view.findViewById(R.id.foodItemFragment_ingredientItem);

        this.heartButton = view.findViewById(R.id.foodItemFragment_heartButton);
    }

    @Override
    protected void katiEntityUpdated() {
        this.barcode = this.getActivity().getIntent().getStringExtra("barcode");
        this.foodId = this.getActivity().getIntent().getLongExtra(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA, 0L);
       this.isAd = this.getActivity().getIntent().getBooleanExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);

        if (this.barcode != null) this.barcodeSearch();
        else this.search();
    }

    @Override
    protected void initializeView() {
        this.foodImageView.setOnClickListener(v -> this.changeProductImage(this.isFront));
//        this.heartButton.setOnClickListener(v -> this.saveLike());
    }

    @Override
    public void foodModelDataUpdated() {
        if(!this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION).equals(KatiEntity.EKatiData.NULL.name())) {
            String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
            Log.d("즐찾 확인", token + "-");
            this.foodId = this.foodDetailResponse.getFoodId();
            this.checkFavorite(token);
        }else{
//            this.heartButton.setVisibility(View.GONE);
        }
        this.changeImage(this.foodDetailResponse.getFoodImageAddress());
        this.foodNameTextView.setText(this.foodDetailResponse.getFoodName());
        this.materialItem.setContentText(this.foodDetailResponse.getMaterials());
        this.ingredientItem.setContentText(this.foodDetailResponse.getNutrient());


    }

    @Override
    protected void summaryDataUpdated() {
        int visibility;
        if(this.readSummaryResponse.getAvgRating()>0f) {
            this.ratingTextView.setText(String.valueOf(this.readSummaryResponse.getAvgRating()));
            this.reviewCountTextView.setText(String.valueOf(this.readSummaryResponse.getReviewCount()));
            visibility=View.VISIBLE;
        }else{
            visibility=View.INVISIBLE;
        }
        this.ratingTextView.setVisibility(visibility);
        this.reviewCountTextView.setVisibility(visibility);
        this.starIcon.setVisibility(visibility);
    }


    private class FoodDetailRequestCallback extends SimpleRetrofitCallBackImpl<FoodDetailResponse> {
        public FoodDetailRequestCallback(Activity activity) {
            super(activity);
        }
        @Override
        public void onSuccessResponse(Response<FoodDetailResponse> response) {
//            loadingDialog.hide();
            foodDetailResponse=response.body();
            saveFoodDetail();
        }

    }

    private class AddFavoriteCallBack extends SimpleLoginRetrofitCallBack<Boolean> {

        public AddFavoriteCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<Boolean> response) {
            KatiDialog.simplerAlertDialog(
                    getActivity(),
                    ADD_FAVORITE_RESULT_DIALOG_TITLE,
                    ADD_FAVORITE_RESULT_DIALOG_MESSAGE,
                    null
            );
            setIsFavorite(true);
        }
    }

    private class DeleteFavoriteCallBack extends SimpleLoginRetrofitCallBack<Void> {

        public DeleteFavoriteCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<Void> response) {
            KatiDialog.simplerAlertDialog(
                    getActivity(),
                    DELETE_FAVORITE_RESULT_DIALOG_TITLE,
                    DELETE_FAVORITE_RESULT_DIALOG_MESSAGE,
                    null
            );
            setIsFavorite(false);
        }
    }

    private class CheckFavoriteCallBack extends SimpleLoginRetrofitCallBack<Boolean> {

        public CheckFavoriteCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<Boolean> response) {
            setIsFavorite(response.body().booleanValue());
        }
    }



    private void search() {
//        this.loadingDialog.show();
        if (!isAd) {
            KatiRetrofitTool.getAPI().getFoodDetailByFoodId(this.foodId).enqueue(JSHRetrofitTool.getCallback(new FoodDetailRequestCallback(this.getActivity())));
        } else {
            KatiRetrofitTool.getAPI().getAdFoodDetail(this.foodId).enqueue(JSHRetrofitTool.getCallback(new FoodDetailRequestCallback(this.getActivity())));
        }
    }

    private void barcodeSearch() {
//        this.loadingDialog.show();
        KatiRetrofitTool.getAPI().findByBarcode(new FindFoodByBarcodeRequest(this.barcode)).enqueue(JSHRetrofitTool.getCallback(new FoodDetailRequestCallback(this.getActivity())));
    }

    private void changeProductImage(boolean isFront) {
        this.isFront = !isFront;
        if (!this.isFront) {
            this.changeImage(this.foodModel.getFoodDetailResponse().getValue().getFoodImageAddress());
        } else {
            this.changeImage(this.foodModel.getFoodDetailResponse().getValue().getFoodMeteImageAddress());
        }
    }

    public void changeImage(String address) {
        if(address!=null)
        Glide.with(getContext()).load(address).fitCenter().transform(new CenterCrop()).into(this.foodImageView);
    }

    private void setIsFavorite(boolean flag) {
        this.isFavorite = flag;
//        this.likeButton.setImageDrawable(getResources().getDrawable(
//                this.isFavorite ?
//                        R.drawable.ic_baseline_favorite_24 :
//                        R.drawable.ic_baseline_favorite_border_24
//                , getContext().getTheme()
//        ));
//        this.likeButton.clearColorFilter();
//        this.likeButton.setColorFilter(
//                getResources().getColor(
//                        this.isFavorite ?
//                                R.color.kati_red :
//                                R.color.gray, getContext().getTheme()
//                ),
//                PorterDuff.Mode.SRC_IN
//        );
        this.heartButton.setChecked(flag);
    }

    private void saveLike() {
        if (this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)) {
            String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

            if (!isFavorite) {
                KatiRetrofitTool.getAPIWithAuthorizationToken(token).addFavoriteFood(this.foodId).enqueue(JSHRetrofitTool.getCallback(new AddFavoriteCallBack(this.getActivity())));
            } else {
                KatiRetrofitTool.getAPIWithAuthorizationToken(token).deleteFavoriteFood(this.foodId).enqueue(JSHRetrofitTool.getCallback(new DeleteFavoriteCallBack(this.getActivity())));
            }
        }
    }

    private void checkFavorite(String token) {
        Log.d("음식아이디?",this.foodId+"");
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).getFavoriteStateForFood(this.foodId).enqueue(JSHRetrofitTool.getCallback(new CheckFavoriteCallBack(this.getActivity())));
    }
}