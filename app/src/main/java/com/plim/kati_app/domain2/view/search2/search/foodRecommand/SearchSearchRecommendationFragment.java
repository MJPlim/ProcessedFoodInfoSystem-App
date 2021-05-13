package com.plim.kati_app.domain2.view.search2.search.foodRecommand;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain2.katiCrossDomain.domain.view.KatiSearchFragment;
import com.plim.kati_app.domain2.view.search2.search.foodRecommand.ranking.FoodRankAdapter;
import com.plim.kati_app.domain2.view.search2.search.foodRecommand.recentSearch.SearchHistoryAdapter;

import java.util.Vector;

import static com.plim.kati_app.domain.constant.Constant_yun.SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.SEARCH_WORD_DELETE_ALL_DIALOG_TITLE;
import static com.plim.kati_app.domain.constant.Constant_yun.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD;
import static com.plim.kati_app.domain.constant.Constant_yun.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL;
import static com.plim.kati_app.domain.constant.Constant_yun.SEARCH_WORD_DELETE_ONE_DIALOG_TITLE;

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
    protected int getLayoutId() { return R.layout.fragment_food_search_recommendation; }
    @Override
    protected void associateView(View view) {
        this.recentValueRecyclerView = view.findViewById(R.id.foodSearchRecommendationFragment_recentValuesRecyclerView);
        this.deleteAllButton = view.findViewById(R.id.foodSearchRecommendationFragment_deleteAllButton);
        this.emptyWordTextView = view.findViewById(R.id.foodSearchRecommendationFragment_emptyWordTextView);
        this.rankRecyclerView = view.findViewById(R.id.foodSearchRecommendationFragment_rankRecyclerView);
    }
    @Override
    protected void initializeView() {
        this.foodRankAdapter = new FoodRankAdapter(this.getDataSet());
        this.searchHistoryAdapter = new SearchHistoryAdapter(this.searchWords,
                v-> {this.showDeleteSearchedWordConfirm((String) v.getTag()); return true;});
        this.recentValueRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.rankRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.rankRecyclerView.setAdapter(this.foodRankAdapter);
        this.recentValueRecyclerView.setAdapter(this.searchHistoryAdapter);
        this.deleteAllButton.setOnClickListener(v -> showDeleteSearchedWordConfirm());
    }
    @Override
    protected void katiEntityUpdated() { this.loadRecentSearchedWords(); }

    /**
     * Method
     */
    private void loadRecentSearchedWords() {
        this.searchHistoryAdapter.setValueVector(this.searchWords);
        this.searchHistoryAdapter.notifyDataSetChanged();
        int visibility = this.searchWords.size() == 0? View.INVISIBLE:View.VISIBLE;
        this.deleteAllButton.setVisibility(visibility);
        this.emptyWordTextView.setVisibility(visibility);
    }
    private void showDeleteSearchedWordConfirm() {
        KatiDialog.simplerAlertDialog(this.getActivity(),
            SEARCH_WORD_DELETE_ALL_DIALOG_TITLE, SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE,
            (dialog, which) ->  this.deleteAllSearchedWords()
        );
    }
    private void showDeleteSearchedWordConfirm(String value) {
        KatiDialog.simplerAlertDialog(this.getActivity(),
            SEARCH_WORD_DELETE_ONE_DIALOG_TITLE, SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD + value + SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL,
            (dialog, which) -> this.deleteSearchedWordByValue(value)
        );
    }
    private void deleteAllSearchedWords() {
        this.searchWords.clear();
        loadRecentSearchedWords();
    }
    private void deleteSearchedWordByValue(String value) {
        this.searchWords.remove(value);
        loadRecentSearchedWords();
    }

    public Vector<String> getDataSet() { // 임시 랭크 데이터. 여기에 서버로부터 불러올 코드를 다시 작성할 것이다.
        Vector<String> val = new Vector<>();
        val.add("새우깡"); val.add("감자깡"); val.add("오징어집"); val.add("신라면"); val.add("참깨라면");
        val.add("진라면"); val.add("팔도비빔면"); val.add("포스틱"); val.add("불닭볶음면"); val.add("벌집핏자");
        return val;
    }
}