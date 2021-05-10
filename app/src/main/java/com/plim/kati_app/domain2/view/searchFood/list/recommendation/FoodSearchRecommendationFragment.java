package com.plim.kati_app.domain2.view.searchFood.list.recommendation;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.room.KatiDatabase;

import java.util.List;
import java.util.Vector;

import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.domain.constant.Constant_yun.KATI_DIALOG_NO;
import static com.plim.kati_app.domain.constant.Constant_yun.KATI_DIALOG_YES;
import static com.plim.kati_app.domain.constant.Constant_yun.SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.SEARCH_WORD_DELETE_ALL_DIALOG_TITLE;
import static com.plim.kati_app.domain.constant.Constant_yun.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD;
import static com.plim.kati_app.domain.constant.Constant_yun.SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL;
import static com.plim.kati_app.domain.constant.Constant_yun.SEARCH_WORD_DELETE_ONE_DIALOG_TITLE;

/**
 * 검색어 입력하는 중에 하단에 보여주는 검색 추천 프래그먼트
 */
public class FoodSearchRecommendationFragment extends Fragment {

    //Associate
    //view
    private RecyclerView recentValueRecyclerView;
    private RecyclerView rankRecyclerView;
    private TextView deleteAllButton;
    private TextView emptyWordTextView;

    //adapter
    private recentButtonRecyclerViewAdapter recentValueRecyclerViewAdapter;
    private RankRecyclerViewAdapter rankRecyclerViewAdapter;

    private Vector<String> recentSearchedWords;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.recentSearchedWords = new Vector<>();
        return inflater.inflate(R.layout.fragment_food_search_recommendation, container, false);
    }

    /**
     * !! 임시!! 서버나 로컬에서 저장된 데이터를 불러올 예정.
     * @return 데이터가 담긴 벡터.
     */
    public Vector<String> getDatas() {
        //임시 랭크 데이터. 여기에 서버로부터 불러올 코드를 다시 작성할 것이다.
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //associate view
        this.recentValueRecyclerView = view.findViewById(R.id.foodSearchRecommendationFragment_recentValuesRecyclerView);
        this.deleteAllButton = view.findViewById(R.id.foodSearchRecommendationFragment_deleteAllButton);
        this.emptyWordTextView = view.findViewById(R.id.foodSearchRecommendationFragment_emptyWordTextView);

        this.rankRecyclerView = view.findViewById(R.id.foodSearchRecommendationFragment_rankRecyclerView);

        //임시 랭킹 데이터 받아오기.
        Vector<String> val = this.getDatas();

        //create adapter
        this.rankRecyclerViewAdapter = new RankRecyclerViewAdapter(val);
        this.recentValueRecyclerViewAdapter = new recentButtonRecyclerViewAdapter(recentSearchedWords);

        //set view
        this.recentValueRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.rankRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.rankRecyclerView.setAdapter(this.rankRecyclerViewAdapter);
        this.recentValueRecyclerView.setAdapter(this.recentValueRecyclerViewAdapter);

        this.deleteAllButton.setOnClickListener(v -> showDeleteSearchedWordConfirm());

    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadRecentSearchedWords();
    }

    /**
     * 최근에 검색할 때 사용한 검색어들을 데이터베이스에서 불러오고, 리사이클러뷰에 어댑터를 설정한다.
     *
     * @return 검색어로 이루어진 벡터.
     */
    private void loadRecentSearchedWords() {
        this.recentSearchedWords.clear();
        new Thread(() -> {
            //저장되어 있는 최근 검색어 벡터를 가져와서, 역순으로 정렬하여 넣는다.
            KatiDatabase katiDatabase = KatiDatabase.getAppDatabase(this.getContext());
            List<String> stringList = katiDatabase.katiSearchWordDao().getValues();
            for (int i = stringList.size() - 1; i >= 0; i--) {
                this.recentSearchedWords.add(stringList.get(i));
            }



            //ui 스레드에서 돌릴 것.
            getActivity().runOnUiThread(() -> {
                //어댑터에 새로운 벡터를 설정한다.
                this.recentValueRecyclerViewAdapter.setValueVector(this.recentSearchedWords);

                //사이즈가 0이면 모두 지우기 버튼을 숨기고, 비었다 메시지를 표시
                if (this.recentSearchedWords.size() == 0) {
                    this.deleteAllButton.setVisibility(View.INVISIBLE);
                    this.emptyWordTextView.setVisibility(View.VISIBLE);

                    //사이즈가 0이 아니면 모두 지우기 버튼을 표시하고, 비었다 메시지 숨김
                } else {
                    this.deleteAllButton.setVisibility(View.VISIBLE);
                    this.emptyWordTextView.setVisibility(View.INVISIBLE);
                }
            });
        }).start();
    }


    /**
     * 검색어를 지울 지 물어보고, 검색어를 지운다 모두.
     */
    private void showDeleteSearchedWordConfirm() {
        KatiDialog katiDialog = new KatiDialog(this.getContext());
        katiDialog.setTitle(SEARCH_WORD_DELETE_ALL_DIALOG_TITLE);
        katiDialog.setMessage(SEARCH_WORD_DELETE_ALL_DIALOG_MESSAGE);

        katiDialog.setNegativeButton(KATI_DIALOG_NO, (dialog, which) -> {
            return;
        });
        katiDialog.setPositiveButton(KATI_DIALOG_YES, (dialog, which) -> {
            this.deleteAllSearchedWords();
        });
        katiDialog.setColor(this.getContext().getResources().getColor(R.color.kati_coral, this.getContext().getTheme()));
        katiDialog.showDialog();
    }

    /**
     * 모든 검색어를 지우고, 뷰를 새로고침한다.
     */
    private void deleteAllSearchedWords() {
        new Thread(() -> {
            KatiDatabase katiDatabase = KatiDatabase.getAppDatabase(this.getContext());
            katiDatabase.katiSearchWordDao().deleteAll();
            loadRecentSearchedWords();
        }).start();
    }


    /**
     * 검색어를 지울 지 물어보고, 검색어를 지운다 한개.
     *
     * @param value
     */
    private void showDeleteSearchedWordConfirm(String value) {
        KatiDialog katiDialog = new KatiDialog(this.getContext());
        katiDialog.setTitle(SEARCH_WORD_DELETE_ONE_DIALOG_TITLE);
        katiDialog.setMessage(SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_HEAD + value + SEARCH_WORD_DELETE_ONE_DIALOG_MESSAGE_TAIL);

        katiDialog.setNegativeButton(KATI_DIALOG_NO, (dialog, which) -> {
            return;
        });
        katiDialog.setPositiveButton(KATI_DIALOG_YES, (dialog, which) -> {
            this.deleteSearchedWordByValue(value);
        });
        katiDialog.setColor(this.getContext().getResources().getColor(R.color.kati_coral, this.getContext().getTheme()));
        katiDialog.showDialog();
    }

    /**
     * 하나의 검색어를 지우고, 뷰를 새로고침한다.
     *
     * @param value
     */
    private void deleteSearchedWordByValue(String value) {
        new Thread(() -> {
            KatiDatabase katiDatabase = KatiDatabase.getAppDatabase(this.getContext());
            katiDatabase.katiSearchWordDao().delete(value);
            loadRecentSearchedWords();
        }).start();
    }


    /**
     * 최근 검색어 리사이클러 뷰 용, 뷰 어댑터 클래스.
     */
    public class recentButtonRecyclerViewAdapter extends RecyclerView.Adapter<recentButtonRecyclerViewAdapter.RecentButtonRecyclerviewViewHolder> {
        private Vector<String> values;

        public recentButtonRecyclerViewAdapter(Vector<String> values) {
            //create component
            this.values = values;
        }

        @NonNull
        @Override
        public recentButtonRecyclerViewAdapter.RecentButtonRecyclerviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_light_button, parent, false);
            RecentButtonRecyclerviewViewHolder viewHolder = new RecentButtonRecyclerviewViewHolder(view);

            return viewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull RecentButtonRecyclerviewViewHolder holder, int position) {
            String value = this.values.get(position);
            holder.setValueButton(value);
        }

        @Override
        public int getItemCount() {
            return this.values.size();
        }

        /**
         * 내부 벡터 수정.
         *
         * @param recentSearchedWords 교체할 데이터 벡터.
         */
        public void setValueVector(Vector<String> recentSearchedWords) {
            this.values = recentSearchedWords;
            this.notifyDataSetChanged();
        }


        /**
         * 최근 검색어 리사이클러뷰용 뷰 홀드 클래스.
         */
        private class RecentButtonRecyclerviewViewHolder extends RecyclerView.ViewHolder {
            private Button valueButton;

            public RecentButtonRecyclerviewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.valueButton = itemView.findViewById(R.id.item_button);
            }

            /**
             * 밸류버튼을 세팅하기.
             *
             * @param value 값.
             */
            public void setValueButton(String value) {
                this.valueButton.setText(value);

                //클릭되었을 때 (값을 검색어 필드로 보낸다.)
                this.valueButton.setOnClickListener(v ->
                    this.putBundle(value));

                //길게 클릭되었을 때 (값을 지우고 리로드한다.)
                this.valueButton.setOnLongClickListener(v -> {
                    showDeleteSearchedWordConfirm(value);
                    return true;
                });
            }

            /**
             * 검색어 필드에게 번들을 통해 선택된 값을 전달한다.
             */
            public void putBundle(String value){
                Bundle text = new Bundle();
                text.putString(FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY, value);
                getActivity().getSupportFragmentManager().setFragmentResult(FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY, text);
            }

        }

    }

}