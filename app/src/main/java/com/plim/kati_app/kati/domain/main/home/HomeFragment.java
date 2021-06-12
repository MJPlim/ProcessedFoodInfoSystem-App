package com.plim.kati_app.kati.domain.main.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHViewPagerTool;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.YYECategoryItem;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.login.model.LoginResponse;
import com.plim.kati_app.kati.domain.main.category.CategoryFoodDetailFragment;
import com.plim.kati_app.kati.domain.main.category.CategoryFoodDetailFragmentDirections;
import com.plim.kati_app.kati.domain.main.home.advertisement.AdvertisementViewPagerAdapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.HOME_FRAGMENT_BUNDLE_KEY;

public class HomeFragment extends KatiViewModelFragment {

    private ViewPager2 viewPager2;
    private TextView searchText;
    private GridLayout layout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void associateView(View view) {
        this.viewPager2 = view.findViewById(R.id.homeFragment_viewPager);
        this.layout = view.findViewById(R.id.homeFragment_category);
        this.searchText = view.findViewById(R.id.homeFragment_searchTextView);


        BottomNavigationView bottomNavigationView= (BottomNavigationView)this.getActivity().findViewById(R.id.mainFragment_bottomNavigation);
        if (bottomNavigationView.getSelectedItemId() != R.id.action_home)
        bottomNavigationView.findViewById(R.id.action_home).performClick();

    }

    @AllArgsConstructor
    @Getter
    public enum EAdImage{
        first(R.drawable.ad1),second(R.drawable.ad2),third(R.drawable.ad3);
        private int ImageId;
    }

    @Override
    protected void initializeView() {
        this.viewPager2.setAdapter(new AdvertisementViewPagerAdapter(EAdImage.values(),this.getContext()));
        JSHViewPagerTool.setAutoSlide(this.viewPager2, 5000);
        JSHViewPagerTool.setEffect(this.viewPager2);

        this.createCategories();

        this.searchText.setOnClickListener(v -> {
           this.clickSearchNavigation();
        });
    }



    @Override
    protected void katiEntityUpdated() {
        this.autoLogin();
    }



    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * Callback
     */
    private class LoginRequestCallback extends SimpleRetrofitCallBackImpl<LoginResponse> {
        public LoginRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<LoginResponse> response) {
        }

        @Override
        public void onResponse(Response<LoginResponse> response) {
            dataset.put(KatiEntity.EKatiData.AUTHORIZATION, response.headers().get("Authorization"));
        }
    }

    /**
     * method
     */
    private void autoLogin() {
        if (this.dataset.get(KatiEntity.EKatiData.AUTO_LOGIN).equals(KatiEntity.EKatiData.TRUE.name())) {
            LoginResponse loginRequest = new LoginResponse(this.dataset.get(KatiEntity.EKatiData.EMAIL), this.dataset.get(KatiEntity.EKatiData.PASSWORD));
            KatiRetrofitTool.getAPIWithNullConverter().login(loginRequest).enqueue(JSHRetrofitTool.getCallback(new LoginRequestCallback(this.getActivity())));
        }
    }

    private void moveToCategory(Constant.ECategory category) {
        Navigation.findNavController(this.getView()).navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(category.name()));
    }

    private void createCategories(){
        this.layout.removeAllViews();
        for(int i = 0; i< Constant.ECategory.values().length; i++){
            GridLayout.Spec rowSpec = GridLayout.spec((i<5)? 0:1, GridLayout.FILL);
            GridLayout.Spec columnSpec = GridLayout.spec((i<5)? i:i-5, GridLayout.FILL, 1f);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.width=0;

            Constant.ECategory category = Constant.ECategory.values()[i];
            YYECategoryItem item = new YYECategoryItem(getContext());
            item.setText(category.getName());
            item.setImage(getResources().getDrawable(category.getDrawable(), getActivity().getTheme()));
            item.setOnClickListener(v -> this.moveToCategory(category));
            this.layout.addView(item, params);
        }
    }

    private void clickSearchNavigation(){
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView)this.getActivity().findViewById(R.id.mainFragment_bottomNavigation);
        bottomNavigationView.findViewById(R.id.action_search).performClick();

    }

}