package com.plim.kati_app.domain2.search.food.list;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.model.room.KatiSearchWord;

import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX;
import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE;
import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT;
import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY;


/**
 * 음식 검색하는 검색필드가 있는 프래그먼트
 * !이슈: 검색할 때 입력중이면 카메라 버튼 대신 "x"버튼을 보여주고, x버튼을 누르면 입력한 데이터를 날릴 수 있으면 좋겠다.
 */
public class FoodSearchFieldFragment extends Fragment {

    //associate
    //view
    private Spinner searchModeSpinner;
    private EditText searchEditText;
    private ImageView cameraSearchButton, textSearchButton;

    private boolean listFragmentOn=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_food_search_field, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //associate view
        this.searchEditText = view.findViewById(R.id.foodSearchFieldFragment_searchEditText);
        this.searchModeSpinner = view.findViewById(R.id.foodSearchFieldFragment_searchModeSpinner);
        this.cameraSearchButton = view.findViewById(R.id.foodSearchFieldFragment_cameraSearchButton);
        this.textSearchButton = view.findViewById(R.id.foodSearchFieldFragment_textSearchButton);


        //set view
        this.searchEditText.setOnFocusChangeListener((v,gainFocus)->{
            if(gainFocus){
                Log.d("검색필드 디버그","안");
                this.moveFragment();
            }else{
                //이거 작동이 안되는거 같아서 좀 수정 필요.
                Log.d("검색필드 디버그","밖");
                this.hideKeyboard();
            }
        });

        this.textSearchButton.setOnClickListener((v -> {
            this.searchStart();
        }));


        this.getActivity().getSupportFragmentManager().setFragmentResultListener(FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY, getActivity(), ((requestKey, result) -> {
            this.searchEditText.setText(result.getString(FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY));
        }));

    }

    /**
     * 검색 버튼이 눌려서 검색을 시작한다.
     */
    private void searchStart() {
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_search_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_foodSearchRecommendationFragment_to_foodSearchResultListFragment);
        this.listFragmentOn=true;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
            database.katiSearchWordDao().insert(new KatiSearchWord(this.searchEditText.getText().toString()));
        }).start();

        Bundle result = new Bundle();
        result.putString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX,1+"");
        result.putString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE,searchModeSpinner.getSelectedItem().toString());
        result.putString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_TEXT,this.searchEditText.getText().toString().replaceAll("[ ]", "_"));
        this.getParentFragmentManager().setFragmentResult(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY,result);
    }

    /**
     * 키보드 이외의 곳을 터치할 때 키보드를 내린다.
     */
    private void hideKeyboard() {
        InputMethodManager manager= (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
    }

    /**
     * 만약 결과 리스트 창에 있다면, 검색 필드를 눌렀을 때 추천 창으로 이동.
     */
    private void moveFragment() {
        if(listFragmentOn) {
            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_search_fragment);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.action_foodSearchResultListFragment_to_foodSearchRecommendationFragment);
            this.listFragmentOn=false;
        }
    }


}