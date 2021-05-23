package com.plim.kati_app.kati.domain.search.search.view.foodRecommand;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.domain.search.search.view.foodRecommand.ranking.FoodRankAdapter;
import com.plim.kati_app.kati.domain.search.search.view.foodRecommand.recentSearch.SearchHistoryAdapter;

import java.util.Vector;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ALL_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_TITLE;

public class SearchSearchRecommendationFragment extends KatiSearchFragment {

    //Associate
    //view
    private RecyclerView recentValueRecyclerView, rankRecyclerView;
    private TextView deleteAllButton, emptyWordTextView;

    // Component
    //adapter
    private SearchHistoryAdapter searchHistoryAdapter;
    private FoodRankAdapter foodRankAdapter;


    /**
     * System Life Cycle Callback
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_food_search_recommendation;
    }

    @Override
    protected void associateView(View view) {
        this.recentValueRecyclerView = view.findViewById(R.id.foodSearchRecommendationFragment_recentValuesRecyclerView);
        this.deleteAllButton = view.findViewById(R.id.foodSearchRecommendationFragment_deleteAllButton);
        this.emptyWordTextView = view.findViewById(R.id.foodSearchRecommendationFragment_emptyWordTextView);
        this.rankRecyclerView = view.findViewById(R.id.foodSearchRecommendationFragment_rankRecyclerView);
    }

    @Override
    protected void initializeView() {
        View.OnClickListener listener = v -> {
//            Log.d("버튼 누름", (String) v.getTag());
            this.searchModel.setSearchText((String) v.getTag());
            this.searchViewModel.getSearchModel().setValue(this.searchModel);
        };
        this.foodRankAdapter = new FoodRankAdapter(this.getDataSet(), listener);
        this.searchHistoryAdapter = new SearchHistoryAdapter(this.searchWords,
                v -> {
                    this.showDeleteSearchedWordConfirm((String) v.getTag());
                    return true;
                },
                listener
        );
        this.recentValueRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.rankRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.rankRecyclerView.setAdapter(this.foodRankAdapter);
        this.recentValueRecyclerView.setAdapter(this.searchHistoryAdapter);
        this.deleteAllButton.setOnClickListener(v -> showDeleteSearchedWordConfirm());
    }

    @Override
    protected void katiEntityUpdated() {
        this.loadRecentSearchedWords();
    }

    @Override
    protected void searchModelDataUpdated() {
        this.loadRecentSearchedWords();
    }

    /**
     * Method
     */
    private void loadRecentSearchedWords() {
        if (this.searchHistoryAdapter != null&& this.searchWords!=null) {
            this.searchHistoryAdapter.setValueVector(this.searchWords);
            this.searchHistoryAdapter.notifyDataSetChanged();
            this.deleteAllButton.setVisibility(this.searchWords.size() == 0 ? View.INVISIBLE : View.VISIBLE);
            this.emptyWordTextView.setVisibility(this.searchWords.size() != 0 ? View.INVISIBLE : View.VISIBLE);
        }

    }

    private void showDeleteSearchedWordConfirm() {
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