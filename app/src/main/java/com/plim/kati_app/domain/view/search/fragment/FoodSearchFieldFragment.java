package com.plim.kati_app.domain.view.search.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.plim.kati_app.R;
import lombok.Getter;


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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_food_search_field, container, false);
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
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_search_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_foodSearchResultListFragment_to_foodSearchRecommendationFragment);
            }else{
                InputMethodManager manager= (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
            }
        });

        this.textSearchButton.setOnClickListener((v -> {

                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_search_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_foodSearchRecommendationFragment_to_foodSearchResultListFragment);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            Bundle result = new Bundle();
            result.putString("index",1+"");
            result.putString("mode",searchModeSpinner.getSelectedItem().toString());
            result.putString("text",this.searchEditText.getText().toString().replaceAll("[ ]", "_"));
            this.getParentFragmentManager().setFragmentResult("result",result);
        }));


        this.getActivity().getSupportFragmentManager().setFragmentResultListener("text", getActivity(), ((requestKey, result) -> {
            this.searchEditText.setText(result.getString("text"));
        }));

    }

    /**
     * 검색모드
     */
    @Getter
    public enum ESearchMode {
        제품("foodName"), 회사("bsshName");
        private String mappingName;

        ESearchMode(String string) {
            this.mappingName = string;
        }
    }


}