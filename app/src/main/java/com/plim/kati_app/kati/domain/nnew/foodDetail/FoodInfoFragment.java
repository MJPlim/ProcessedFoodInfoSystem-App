package com.plim.kati_app.kati.domain.nnew.foodDetail;

import android.app.Activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.plim.kati_app.kati.domain.nnew.foodDetail.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FindFoodByBarcodeRequest;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

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
    private ImageView foodImageView, starIcon;
    private TextView foodNameTextView, ratingTextView, reviewCountTextView;
    private JSHInfoItem materialItem, ingredientItem, allergyItem;
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
        this.foodImageView = view.findViewById(R.id.foodItemFragment_foodImageView);
        this.starIcon = view.findViewById(R.id.foodItemFragment_starIcon);

        this.allergyItem = view.findViewById(R.id.foodItemFragment_allergy);

        this.foodNameTextView = view.findViewById(R.id.foodItemFragment_foodNameTextView);
        this.ratingTextView = view.findViewById(R.id.foodItemFragment_ratingTextView);
        this.reviewCountTextView = view.findViewById(R.id.foodItemFragment_reviewCountTextView);

        this.materialItem = view.findViewById(R.id.foodItemFragment_materialItem);
        this.ingredientItem = view.findViewById(R.id.foodItemFragment_ingredientItem);

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
        this.heartButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                saveLike();
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
//                saveLike();
            }
        });
    }

    @Override
    public void foodModelDataUpdated() {
        if (!this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION).equals(KatiEntity.EKatiData.NULL.name())) {
            String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
            Log.d("즐찾 확인", token + "-");
            this.foodId = this.foodDetailResponse.getFoodId();
            this.checkFavorite(token);
            this.heartButton.setVisibility(View.VISIBLE);
        } else {
            this.heartButton.setVisibility(View.GONE);
        }
        this.changeImage(this.foodDetailResponse.getFoodImageAddress());
        this.changeImage(this.foodModel.getFoodDetailResponse().getValue().getFoodImageAddress());
        this.foodNameTextView.setText(this.foodDetailResponse.getFoodName());
        this.ratingTextView.setText(String.valueOf(this.foodDetailResponse.getReviewRate()));
        this.reviewCountTextView.setText("(" + this.foodDetailResponse.getReviewCount() + ")");


        this.materialItem.setContentText(this.splitString(",", this.foodDetailResponse.getMaterials()));
        this.ingredientItem.setContentText(this.splitString("`", this.foodDetailResponse.getNutrient()));
        this.allergyItem.setContentText(this.splitString(" ", this.foodDetailResponse.getAllergyMaterials()));
    }

    private String splitString(String splitChar, String string) {

        String[] array = string.split(splitChar);
        StringBuilder builder = new StringBuilder();
        boolean inBig = false,inMiddle = false,inSmall = false;
        for (String value : array) {
            if(value.indexOf('[')!=-1) inBig=true;
            if(value.indexOf('<')!=-1) inMiddle=true;
            if(value.indexOf('(')!=-1) inSmall=true;

            if(inBig&& value.indexOf(']')!=-1) inBig=false;
            if(inMiddle&& value.indexOf('>')!=-1) inMiddle=false;
            if(inSmall&& value.indexOf(')')!=-1) inSmall=false;

            if(inBig||inMiddle||inSmall){
                builder.append(value);
                builder.append(splitChar);

            }else{
                builder.append(value);
                builder.append('\n');
                builder.append('\n');
            }

            Log.d(value,(inBig||inMiddle||inSmall)+"");





//            if (in) {
//                builder.append(value);
//                builder.append(splitChar);
//                if (value.indexOf(']') != -1 || value.indexOf(')') != -1 || value.indexOf('>') != -1)
//                    in = false;
//            }else {
//                if (value.indexOf('[') != -1 || value.indexOf('(') != -1 || value.indexOf('<') != -1)
//                    in = true;
//                builder.append(value);
//                builder.append('\n');
//            }
        }
        return builder.toString();
    }

    @Override
    protected void summaryDataUpdated() {
    }


    private class FoodDetailRequestCallback extends SimpleRetrofitCallBackImpl<FoodDetailResponse> {
        public FoodDetailRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<FoodDetailResponse> response) {
            foodDetailResponse = response.body();
            saveFoodDetail();
        }

    }

    private class AddFavoriteCallBack extends SimpleLoginRetrofitCallBack<Boolean> {

        public AddFavoriteCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<Boolean> response) {
            setIsFavorite(true);
        }
    }

    private class DeleteFavoriteCallBack extends SimpleLoginRetrofitCallBack<Void> {

        public DeleteFavoriteCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<Void> response) {
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
        if (!isAd) {
            KatiRetrofitTool.getAPI().getFoodDetailByFoodId(this.foodId).enqueue(JSHRetrofitTool.getCallback(new FoodDetailRequestCallback(this.getActivity())));
        } else {
            KatiRetrofitTool.getAPI().getAdFoodDetail(this.foodId).enqueue(JSHRetrofitTool.getCallback(new FoodDetailRequestCallback(this.getActivity())));
        }
    }

    private void barcodeSearch() {
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
        if (address != null)
            Glide.with(getContext()).load(address).fitCenter().transform(new CenterCrop()).into(this.foodImageView);
    }

    private void setIsFavorite(boolean flag) {
        this.isFavorite = flag;
        this.heartButton.pressOnTouch(flag);
        this.heartButton.setChecked(flag);
    }

    private void saveLike() {
        Log.d("값", "좋아요 저장");
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
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).getFavoriteStateForFood(this.foodId).enqueue(JSHRetrofitTool.getCallback(new CheckFavoriteCallBack(this.getActivity())));
    }
}