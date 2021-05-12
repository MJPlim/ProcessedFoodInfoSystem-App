package com.plim.kati_app.domain2.view.search2.search.searchBar;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.plim.kati_app.R;
import com.plim.kati_app.domain2.katiCrossDomain.domain.view.KatiFoodFragment;

public class FoodSearchFieldFragment extends KatiFoodFragment {

    // Associate
        // view
        private Spinner searchModeSpinner;
        private EditText searchEditText;
        private ImageView cameraSearchButton, textSearchButton;

    /**
     * System Life Cycle Callback
     */
    @Override
    public int getLayoutId() { return R.layout.layout_food_search_field; }
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
    }
    @Override
    protected void katiEntityUpdated() { }

    /**
     * Callback
     */
    private void searchStart() {
        this.searchWords.add(this.searchEditText.getText().toString());
        this.foodModel.setSearchPageNum(Integer.toString(1));
        this.foodModel.setSearchMode(this.searchModeSpinner.getSelectedItem().toString());
        this.foodModel.setSearchText(this.searchEditText.getText().toString().replaceAll("[ ]", "_"));
        this.navigateTo(R.id.action_foodSearchRecommendationFragment_to_foodSearchResultListFragment);
    }
}