package com.plim.kati_app.kati.domain.nnew.main.search;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntityTool;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRestAPI;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.nnew.itemRank.model.ItemRankingResponse;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ALL_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_TITLE;

public class SearchFragment extends KatiSearchFragment {

    private EditText searchFieldEditText;
    private TextView deleteTextView;
    private ImageView deleteIcon;

    private ConstraintLayout recentSearchWordLayout;
    private GridLayout rankGridLayout;


    private ChipGroup recentSearchedChipGroup;

    private View.OnClickListener deleteAllListener, deleteOneListener, setSearchTextListener;

    /*
    callback
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void associateView(View view) {
        this.recentSearchedChipGroup = view.findViewById(R.id.searchFragment_recentSearchWordChipGroup);

        this.searchFieldEditText = view.findViewById(R.id.searchFragment_searchFieldEditText);
        this.deleteTextView = view.findViewById(R.id.searchFragment_deleteTextView);
        this.deleteIcon = view.findViewById(R.id.searchFragment_deleteIcon);

        this.recentSearchWordLayout = view.findViewById(R.id.searchFragment_constraintLayout);
        this.rankGridLayout = view.findViewById(R.id.searchFragment_rankGridLayout);

    }

    @Override
    protected void initializeView() {
        this.searchFieldEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                this.doSearchStart(v);
            }
            return false;
        });

        this.setSearchTextListener = v -> {
            this.searchModel.setSearchText((String) v.getTag());
            this.searchFieldEditText.setText((String) v.getTag());
            this.doSearchStart(v);
        };

        this.deleteAllListener = v -> {
            this.showDeleteAllSearchedWordConfirm();
            this.save();
        };

        this.deleteOneListener = v -> {
            this.showDeleteSearchedWordConfirm((String) v.getTag());
            this.save();
        };

        this.loadRank();
    }

    @Override
    protected void katiEntityUpdated() {
        this.loadRecentSearchedWords();
    }

    @Override
    protected void searchModelDataUpdated() {
        this.loadRecentSearchedWords();
    }

    /*
    inner class
    */
    private class ReadRankingListCallback extends SimpleRetrofitCallBackImpl<List<ItemRankingResponse>> {


        public ReadRankingListCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<List<ItemRankingResponse>> response) {

            Vector<ItemRankingResponse> itemVector = new Vector<>(response.body());
            fillRankGridLayout(itemVector);

        }
    }




    /*
    method
     */

    private void fillRankGridLayout(Vector<ItemRankingResponse> itemVector) {
        rankGridLayout.setRowCount(5);
        rankGridLayout.removeAllViews();

        for (int i = 0; i < itemVector.size(); i++) {

            ItemRankingResponse item = itemVector.get(i);
            String foodName = item.getFoodName();
            if (i == 0 || i == 5) {
                for (int j = i + 1; j < i + 6; j++) {
                    rankGridLayout.addView(createRankTextView(18, String.valueOf(j), foodName));
                }
            }
            rankGridLayout.addView(createRankTextView(15, foodName));
        }

    }

    private TextView createRankTextView(int size, String text) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setGravity(Gravity.LEFT);

        return this.createRankTextView(params, size, text, text);
    }

    private TextView createRankTextView(int size, String text, String tag) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setGravity(Gravity.CENTER);

        return this.createRankTextView(params, size, text, tag);
    }

    private TextView createRankTextView(GridLayout.LayoutParams params, int size, String text, String tag) {
        int paddingVertical = 10;
        int paddingHorizontal = 25;
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTag(tag);
        textView.setLayoutParams(params);
        textView.setOnClickListener(setSearchTextListener);
        textView.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
        textView.setTextColor(getResources().getColor(R.color.black, getContext().getTheme()));
        textView.setTextSize(size);
        return textView;
    }

    private boolean doSearchStart(View view) {
        String searchText = this.searchFieldEditText.getText().toString();
        if (this.searchWords.contains(searchText))
            this.searchWords.remove(searchText);
        this.searchWords.add(0, searchText);
        this.save();
        Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_searchResultFragment);
        return true;
    }

    private void loadRecentSearchedWords() {
        if (this.searchWords != null && this.recentSearchedChipGroup != null) {
            this.recentSearchedChipGroup.removeAllViews();
            for (String value : this.searchWords) {
                Chip chip = new Chip(getContext());
                chip.setTag(value);
                chip.setText(value);
                chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.white, getContext().getTheme())));
                chip.setChipStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.kati_middle_gray, getContext().getTheme())));
                chip.setChipStrokeWidth(1.5f);
                chip.setCloseIconVisible(true);
                chip.setOnCloseIconClickListener(this.deleteOneListener);
                chip.setOnClickListener(this.setSearchTextListener);
                this.recentSearchedChipGroup.addView(chip);
            }

            this.deleteTextView.setOnClickListener(this.deleteAllListener);
            this.deleteIcon.setOnClickListener(this.deleteAllListener);

            int visibility = this.searchWords.size() == 0 ? View.GONE : View.VISIBLE;
            this.recentSearchWordLayout.setVisibility(visibility);
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

    private void loadRank() {
        KatiRetrofitTool.getAPI().getRankingList().enqueue(JSHRetrofitTool.getCallback(new ReadRankingListCallback(this.getActivity())));
    }
}