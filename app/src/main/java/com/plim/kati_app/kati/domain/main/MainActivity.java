package com.plim.kati_app.kati.domain.main;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.plim.kati_app.R;

import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;

import com.plim.kati_app.jshCrossDomain.tech.google.JSHGoogleMap;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;

import com.plim.kati_app.kati.domain.main.category.CategoryFoodDetailFragment;
import com.plim.kati_app.kati.domain.main.home.HomeFragment;

public class MainActivity extends KatiViewModelActivity {

    private KatiDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_old;
    }

    @Override
    protected void associateView() {

    }

    @Override
    protected void initializeView() {
        if (this.findViewById(R.id.mainFragment_bottomNavigation) != null) {
            BottomNavigationView bottomNavigationView = this.findViewById(R.id.mainFragment_bottomNavigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                NavController navController = Navigation.findNavController(this, R.id.mainActivity_navHostFragment);
                switch (item.getItemId()) {
                    case R.id.action_home:
                        navController.navigate(R.id.action_global_mainFragment);
                        break;
                    case R.id.action_favorite:
                        navController.navigate(R.id.action_global_favoriteFragment);
                        break;
                    case R.id.action_search:
                        navController.navigate(R.id.action_global_searchFragment);
                        break;
                    case R.id.action_find_market:
                        JSHGoogleMap.openGoogleMapMyPositionAndSearch(this, "Convenience");
                        break;
                    case R.id.action_mykati:
                        navController.navigate(R.id.action_global_myKatiFragment);
                        break;
                }
                return true;
            });
        }
    }

    @Override
    public void katiEntityUpdated() {

    }

    @Override
    protected void onDestroy() {
        if (this.dialog != null)
            this.dialog.dismiss();
        super.onDestroy();
    }

    //
    @Override
    public void onBackPressed() {
        if (this.getFragment(HomeFragment.class)) this.showSystemOffCheckDialog();
        else if(this.getFragment(CategoryFoodDetailFragment.class)){
            Navigation.findNavController(this,R.id.mainActivity_navHostFragment).navigate(R.id.action_global_mainFragment);
        }else
            super.onBackPressed();
    }

    private boolean getFragment(Class className) {

        NavHostFragment navHostFragment = (NavHostFragment) this.getSupportFragmentManager().getFragments().get(0);
        for (Fragment fragment : navHostFragment.getChildFragmentManager().getFragments()) {
            if (className.isAssignableFrom(fragment.getClass())) {
                return true;
            }
        }
        return false;
    }

    public void showSystemOffCheckDialog() {
        this.dialog = KatiDialog.simplerTwoOptionAlertDialog(this,
                Constant.MAIN_ACTIVITY_FINISH_DIALOG_TITLE, Constant.MAIN_ACTIVITY_FINISH_DIALOG_MESSAGE,
                (dialog, which) -> {
                    this.autoLogout();
                    this.save();
                    this.finish();
                    this.finishAffinity();
                }, null
        );
    }

    private void autoLogout() {
        this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, KatiEntity.EKatiData.NULL.name());

        if (this.dataset.get(KatiEntity.EKatiData.AUTO_LOGIN).equals(KatiEntity.EKatiData.FALSE.name())) {
            this.dataset.put(KatiEntity.EKatiData.EMAIL, KatiEntity.EKatiData.NULL.name());
            this.dataset.put(KatiEntity.EKatiData.PASSWORD, KatiEntity.EKatiData.NULL.name());
        }
    }


}

