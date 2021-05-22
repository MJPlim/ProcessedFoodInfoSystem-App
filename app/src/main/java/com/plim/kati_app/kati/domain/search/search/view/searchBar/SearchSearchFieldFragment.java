package com.plim.kati_app.kati.domain.search.search.view.searchBar;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;

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
        this.searchModel.setSearchPageNum(1);
        this.searchModel.setSearchMode(this.searchModeSpinner.getSelectedItem().toString());
        this.searchModel.setSearchText(this.searchEditText.getText().toString().replaceAll("[ ]", "_"));

        Navigation.findNavController(this.getActivity().getCurrentFocus().getRootView().findViewById(R.id.nav_search_fragment))
                .navigate(R.id.action_foodSearchRecommendationFragment_to_foodSearchResultListFragment);
    }
}