package com.plim.kati_app.kati.domain.nnew.main.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ALL_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_TITLE;

public class SearchFragment extends KatiSearchFragment {


    private ChipGroup recentSearchedChipGroup;

    private View.OnClickListener deleteAllListener, deleteOneListener, setSearchTextListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void associateView(View view) {
    this.recentSearchedChipGroup=view.findViewById(R.id.searchFragment_recentSearchWordChipGroup);

        this.setSearchTextListener = v -> {
            this.searchModel.setSearchText((String) v.getTag());
//            this.searchEditText.setText((String) v.getTag());
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

    }

    @Override
    protected void initializeView() {

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        EditText editText = view.findViewById(R.id.search_searchEditText);
//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_searchResultFragment);
//                    return true;
//                }
//                return false;
//            }
//        });
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
//            this.deleteRecentSearchedAllButtonChip.setVisibility(visibility);
//            this.recentSearchedTitleTextView.setVisibility(visibility);
//            this.secondBarrier.setVisibility(visibility);
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
//        String searchText = this.searchEditText.getText().toString();
//        if (this.searchWords.contains(searchText))
//            this.searchWords.remove(searchText);
//        this.searchWords.add(0, searchText);
//        this.save();
//
//        NavHostFragment fragment = (NavHostFragment) this.getActivity().getSupportFragmentManager().findFragmentById(R.id.foodSearchTextActivity_nav);
//        fragment.getNavController().navigate(R.id.action_foodSearchTextRecommendationFragment_to_foodSearchTextSearchResultFragment);



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