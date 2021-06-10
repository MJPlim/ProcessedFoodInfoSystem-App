package com.plim.kati_app.kati.domain.nnew.main.search;

import android.app.Activity;
import android.app.Service;
import android.content.res.ColorStateList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.nnew.main.search.model.ItemRankingResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.barcode.view.BarcodeActivity;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ALL_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.SEARCH_WORD_DELETE_ONE_DIALOG_TITLE;

public class SearchFragment extends KatiSearchFragment {

    //associate
    //view
    private EditText searchFieldEditText;
    private TextView deleteTextView;
    private ImageView deleteIcon;
    private ImageView barcodeSearchButton;
    private ConstraintLayout recentSearchWordLayout;
    private GridLayout rankGridLayout;

    private ChipGroup recentSearchedChipGroup;

    //listener
    private View.OnClickListener deleteAllListener, deleteOneListener, setSearchTextListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void associateView(View view) {
        this.recentSearchedChipGroup = view.findViewById(R.id.searchFragment_recentSearchWordChipGroup);
        this.barcodeSearchButton = view.findViewById(R.id.searchFragment_barcodeSearchIcon);
        this.searchFieldEditText = view.findViewById(R.id.searchFragment_searchFieldEditText);
        this.deleteTextView = view.findViewById(R.id.searchFragment_deleteTextView);
        this.deleteIcon = view.findViewById(R.id.searchFragment_deleteIcon);

        this.recentSearchWordLayout = view.findViewById(R.id.searchFragment_constraintLayout);
        this.rankGridLayout = view.findViewById(R.id.searchFragment_rankGridLayout);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) this.getActivity().findViewById(R.id.mainFragment_bottomNavigation);
        if (bottomNavigationView.getSelectedItemId() != R.id.action_search)
            bottomNavigationView.findViewById(R.id.action_search).performClick();
    }

    @Override
    protected void initializeView() {
        this.barcodeSearchButton.setOnClickListener(v -> this.barcodeSearch());
        this.searchFieldEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                this.doSearchStart(v);
            }
            return false;
        });

        this.setSearchTextListener = v -> this.setTextAndSearchStart(v);
        this.deleteAllListener = v -> this.showDeleteAllSearchedWordConfirm();
        this.deleteOneListener = v -> this.showDeleteSearchedWordConfirm((String) v.getTag());

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

    /**
     * callback
     */
    private class ReadRankingListCallback extends SimpleRetrofitCallBackImpl<List<ItemRankingResponse>> {
        public ReadRankingListCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<List<ItemRankingResponse>> response) {
            Vector<ItemRankingResponse> itemVector = new Vector<>(response.body());
            fillRankGridLayout2(itemVector);

        }
    }

    /**
     * method
     */
    private void loadRank() {
        KatiRetrofitTool.getAPI().getRankingList().enqueue(JSHRetrofitTool.getCallback(new ReadRankingListCallback(this.getActivity())));
    }

    private void setTextAndSearchStart(View v) {
        this.searchModel.setSearchText((String) v.getTag());
        this.searchFieldEditText.setText((String) v.getTag());
        this.doSearchStart(v);
    }

    private void barcodeSearch() {
        startActivity(BarcodeActivity.class);
    }

    private void fillRankGridLayout2(Vector<ItemRankingResponse> itemVector) {
        for(int i=0; i<10; i++){
            String foodName = itemVector.get(i).getFoodName();

            TextView numView = this.inflateTextView(R.layout.jsh_ranking_num, String.valueOf(i+1), foodName);
            TextView nameView = this.inflateTextView(R.layout.jsh_ranking_name, foodName, foodName);

            GridLayout.Spec rowSpec = GridLayout.spec((i<5)? i:i-5, GridLayout.FILL);
            GridLayout.Spec columnSpec = GridLayout.spec((i<5)? 0:1, GridLayout.FILL,1f);

            GridLayout.LayoutParams numParam = new GridLayout.LayoutParams(rowSpec, columnSpec);
            numParam.width=0;
            rankGridLayout.addView(numView, numParam);

            GridLayout.LayoutParams nameParam = new GridLayout.LayoutParams(rowSpec, columnSpec);
            nameParam.width=0;
            nameParam.leftMargin=60;
            rankGridLayout.addView(nameView, nameParam);
        }
    }
    private TextView inflateTextView(int layoutId, String text, String tag) {
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) inflater.inflate(layoutId, null);
        textView.setOnClickListener(setSearchTextListener);
        textView.setText(text);
        textView.setTag(tag);
        return textView;
    }

    private void fillRankGridLayout(Vector<ItemRankingResponse> itemVector) {
        rankGridLayout.setRowCount(5);
        rankGridLayout.setColumnCount(2);
        rankGridLayout.removeAllViews();

        for (int i = 0; i < itemVector.size(); i++) {

            ItemRankingResponse item = itemVector.get(i);
            String foodName = item.getFoodName();
            if (i == 0 || i == 5) {
                for (int j = i + 1; j < i + 6; j++) {
                    rankGridLayout.addView(createRankTextView(15, String.valueOf(j), foodName));
                }
            }
            rankGridLayout.addView(createRankTextView(15, foodName));
        }

    }

    private TextView createRankTextView(int size, String text) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setGravity(Gravity.LEFT);
        return this.createRankTextView(params, size, text);
    }

    private TextView createRankTextView(int size, String text, String tag) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setGravity(Gravity.CENTER);
        return this.createRankTextView(params, size, text, tag);
    }

    private TextView createRankTextView(GridLayout.LayoutParams params, int size, String text) {
        int paddingVertical = 10;
        int paddingHorizontal = 25;
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) inflater.inflate(R.layout.jsh_ranking_name, null);
        textView.setText(text);
        textView.setTag(text);
//        textView.setLayoutParams(params);
        textView.setOnClickListener(setSearchTextListener);
//        textView.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
//        textView.setTextColor(getResources().getColor(R.color.black, getContext().getTheme()));
//        textView.setTextSize(size);
//        textView.setWidth(600);
//        textView.setMaxWidth(600);
//        textView.setMaxLines(1);
//        textView.setEllipsize(TextUtils.TruncateAt.END);
        return textView;
    }

    // 12345
    private TextView createRankTextView(GridLayout.LayoutParams params, int size, String text, String tag) {
        int paddingVertical = 10;
        int paddingHorizontal = 10;
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) inflater.inflate(R.layout.jsh_ranking_num, null);
        textView.setText(text);
        textView.setTag(tag);
//        textView.setWidth(0);
//        textView.setLayoutParams(params);
        textView.setOnClickListener(setSearchTextListener);
//        textView.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
//        textView.setTextColor(getResources().getColor(R.color.black, getContext().getTheme()));
//        textView.setTextSize(size);
        return textView;
    }

    private boolean doSearchStart(View view) {
        String searchText = this.searchFieldEditText.getText().toString();
        if (this.searchWords.contains(searchText))
            this.searchWords.remove(searchText);
        this.searchWords.add(0, searchText);
        this.searchModel.setSearchText(searchText);
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
                chip.setCloseIconTint(ColorStateList.valueOf(getResources().getColor(R.color.kati_gray, getContext().getTheme())));
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
}