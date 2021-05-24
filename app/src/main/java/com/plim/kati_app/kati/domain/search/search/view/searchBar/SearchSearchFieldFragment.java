package com.plim.kati_app.kati.domain.search.search.view.searchBar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.domain.search.search.view.searchBar.barcode.view.BarcodeActivity;

public class SearchSearchFieldFragment extends KatiSearchFragment {

    // Associate
    // view
    private Spinner searchModeSpinner;
    private EditText searchEditText;
    private ImageView cameraSearchButton, textSearchButton;

    /**
     * System Life Cycle Callback
     */
    @Override
    public int getLayoutId() {
        return R.layout.layout_food_search_field;
    }

    @Override
    protected void associateView(View view) {
        this.searchEditText = view.findViewById(R.id.foodSearchFieldFragment_searchEditText);
        this.searchModeSpinner = view.findViewById(R.id.foodSearchFieldFragment_searchModeSpinner);
        this.cameraSearchButton = view.findViewById(R.id.foodSearchFieldFragment_cameraSearchButton);
        this.textSearchButton = view.findViewById(R.id.foodSearchFieldFragment_textSearchButton);
    }

    @Override
    protected void initializeView() {
        this.textSearchButton.setOnClickListener(v -> this.searchStart());
        this.cameraSearchButton.setOnClickListener(v->this.barcodeSearch());
    }

    private void barcodeSearch() {
        startActivity(BarcodeActivity.class);
    }

    @Override
    protected void katiEntityUpdated() {

    }

    @Override
    protected void searchModelDataUpdated() {
        if (searchModel.getSearchText() != null && searchModel.getSearchText().length() != 0) {
            this.searchEditText.setText(searchModel.getSearchText());
//            this.searchStart();
//            this.textSearchButton.performClick();

        }
    }

    /**
     * Callback
     */
    private void searchStart() {
        if(this.searchEditText.length()==0) {
            KatiDialog.simplerAlertDialog(
                    getActivity(),
                    "검색 오류",
                    "검색어가 입력되지 않았습니다.",
                    null
            );
            return;
        }

        Log.d("검색 시작","저장");
        String searchText= this.searchEditText.getText().toString();
        if(this.searchWords.contains(searchText)) this.searchWords.remove(searchText);
        this.searchWords.add(searchText);
        this.save();


        this.searchModel.setSearchPageNum(1);
        this.searchModel.setSearchMode(this.searchModeSpinner.getSelectedItem().toString());
        this.searchModel.setSearchText(searchText.replaceAll("[ ]", "_"));
        this.saveSearch();

//        Log.d("최근 저장한 검색어, 검색어 개수", this.searchWords.get(searchWords.size() - 1) + "," + searchWords.size());


        Log.d("내비게이션","이동");
        this.textSearchButton.setEnabled(false);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_search_fragment);
        navHostFragment.getNavController().navigate(R.id.action_foodSearchRecommendationFragment_to_foodSearchResultListFragment);
    }


}