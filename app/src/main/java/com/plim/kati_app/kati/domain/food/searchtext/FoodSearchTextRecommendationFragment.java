package com.plim.kati_app.kati.domain.food.searchtext;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.domain.search.search.view.foodRecommand.ranking.FoodRankAdapter;

import java.util.Vector;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ALL_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_TITLE;


public class FoodSearchTextRecommendationFragment extends KatiSearchFragment {

    //component
    //view
    private EditText searchEditText;
    private Chip deleteRecentSearchedAllButtonChip;
    private ChipGroup recentSearchedChipGroup;
    private RecyclerView recentSearchedRankRecyclerView;

    //unUsedView
    private TextView recentSearchedTitleTextView;
    private View secondBarrier;

    //component
    private FoodRankAdapter foodRankAdapter;
    private View.OnClickListener deleteAllListener, deleteOneListener, setSearchTextListener;

    public FoodSearchTextRecommendationFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_food_search_text_recommendation;
    }

    @Override
    protected void associateView(View view) {
        this.searchEditText = view.findViewById(R.id.foodSearchTextRecommendationFragment_searchEditText);
        this.deleteRecentSearchedAllButtonChip = view.findViewById(R.id.foodSearchTextRecommendationFragment_deleteRecentSearchedAllButtonChip);
        this.recentSearchedChipGroup = view.findViewById(R.id.foodSearchTextRecommendationFragment_recentSearchedChipGroup);
        this.recentSearchedRankRecyclerView = view.findViewById(R.id.foodSearchTextRecommendationFragment_recentSearchedRankRecyclerView);
        this.recentSearchedTitleTextView=view.findViewById(R.id.foodSearchTextRecommendationFragment_recentSearchedTitleTextView);
        this.secondBarrier=view.findViewById(R.id.foodSearchTextRecommendationFragment_secondBarrier);

    }


    @Override
    protected void initializeView() {
        this.searchEditText.setOnKeyListener((v, keyCode, event) -> this.doSearchStart(event, keyCode));
        this.setSearchTextListener = v -> {
            this.searchModel.setSearchText((String) v.getTag());
            this.searchEditText.setText((String) v.getTag());
            this.doSearchStart();
        };

        this.deleteAllListener = v -> {
            this.showDeleteAllSearchedWordConfirm();
            this.save();
        };

        this.deleteOneListener = v -> {
            this.showDeleteSearchedWordConfirm((String) v.getTag());
            this.save();
        };

        this.foodRankAdapter = new FoodRankAdapter(this.getDataSet(), this.setSearchTextListener);
        this.recentSearchedRankRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recentSearchedRankRecyclerView.setAdapter(this.foodRankAdapter);
        this.deleteRecentSearchedAllButtonChip.setOnClickListener(this.deleteAllListener);

    }

    @Override
    protected void katiEntityUpdated() {
        this.loadRecentSearchedWords();
    }

    @Override
    protected void searchModelDataUpdated() {
        this.loadRecentSearchedWords();
    }

    private void loadRecentSearchedWords() {
        if (this.searchWords != null && this.recentSearchedChipGroup != null) {
            this.recentSearchedChipGroup.removeAllViews();
            for (String value : this.searchWords) {
                Chip chip = new Chip(getContext());
                chip.setTag(value);
                chip.setText(value);
                chip.setBackgroundColor(getResources().getColor(R.color.kati_orange, getActivity().getTheme()));
                chip.setCloseIconVisible(true);
                chip.setOnCloseIconClickListener(this.deleteOneListener);
                this.recentSearchedChipGroup.addView(chip);
            }

            int visibility= this.searchWords.size()==0? View.GONE:View.VISIBLE;
                this.deleteRecentSearchedAllButtonChip.setVisibility(visibility);
                this.recentSearchedTitleTextView.setVisibility(visibility);
                this.secondBarrier.setVisibility(visibility);
        }

    }

    private void showDeleteAllSearchedWordConfirm() {
        KatiDialog.simplerAlertDialog(this.getActivity(),
                SEARCH_WORD_DELETE_ALL_DIALOG_TITLE, SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE,
                (dialog, which) -> this.deleteAllSearchedWords()
        );
    }

    private void showDeleteSearchedWordConfirm(String value) {
        KatiDialog.simplerAlertDialog(this.getActivity(),
                SEARCH_WORD_DELETE_ONE_DIALOG_TITLE,
                SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD + value + SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL,
                (dialog, which) -> this.deleteSearchedWordByValue(value)
        );
    }

    private void deleteAllSearchedWords() {
        this.searchWords.clear();
        this.save();
    }

    private void deleteSearchedWordByValue(String value) {
        this.searchWords.remove(value);
        this.save();
    }

    private boolean doSearchStart(KeyEvent event, int keyCode) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            this.doSearchStart();
            return true;
        } else {
            return false;
        }
    }

    private void doSearchStart() {
        String searchText = this.searchEditText.getText().toString();
        if (this.searchWords.contains(searchText))
            this.searchWords.remove(searchText);
        this.searchWords.add(0, searchText);
        this.save();
    }

    private Vector<String> getDataSet() { // 임시 랭크 데이터.
        Vector<String> val = new Vector<>();
        val.add("새우깡");
        val.add("감자깡");
        val.add("오징어집");
        val.add("신라면");
        val.add("참깨라면");
        val.add("진라면");
        val.add("팔도비빔면");
        val.add("포스틱");
        val.add("불닭볶음면");
        val.add("벌집핏자");
        return val;
    }


}