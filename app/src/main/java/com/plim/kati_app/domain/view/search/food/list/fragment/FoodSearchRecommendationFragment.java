package com.plim.kati_app.domain.view.search.food.list.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.search.food.list.adapter.LightButtonRecyclerViewAdapter;
import com.plim.kati_app.domain.view.search.food.list.adapter.RankRecyclerViewAdapter;

import java.util.List;
import java.util.Vector;

import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY;

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //associate view
        this.recentValueRecyclerView = view.findViewById(R.id.foodSearchRecommendationFragment_recentValuesRecyclerView);
        this.rankRecyclerView = view.findViewById(R.id.foodSearchRecommendationFragment_rankRecyclerView);
        this.deleteAllButton = view.findViewById(R.id.foodSearchRecommendationFragment_deleteAllButton);
        this.emptyWordTextView = view.findViewById(R.id.foodSearchRecommendationFragment_emptyWordTextView);

        //데이터 받아오기.
        Vector<String> val = this.getDatas();

        //create adapter
        this.rankRecyclerViewAdapter = new RankRecyclerViewAdapter(val);

        //set view
        this.recentValueRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.rankRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.rankRecyclerView.setAdapter(rankRecyclerViewAdapter);
        this.deleteAllButton.setOnClickListener(v -> {
            deleteAllSearchedWords();
        });

    }

    /**
     * 모든 검색어를 지운다.
     */
    private void deleteAllSearchedWords() {
        new Thread(() -> {
            KatiDatabase katiDatabase = KatiDatabase.getAppDatabase(this.getContext());
            katiDatabase.katiSearchWordDao().deleteAll();
            loadRecentSearchedWords();
        }).start();
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
            for(int i=stringList.size()-1; i>=0;i--){
                this.recentSearchedWords.add(stringList.get(i));
            }

            //어댑터에 새로운 벡터를 설정한다.
            this.recentValueRecyclerViewAdapter = new recentButtonRecyclerViewAdapter(recentSearchedWords);

            //ui 스레드에서 돌릴 것.
            getActivity().runOnUiThread(() -> {
                //사이즈가 0이면 모두 지우기 버튼을 숨기고, 비었다 메시지를 표시
                if (this.recentSearchedWords.size() == 0) {
                    this.deleteAllButton.setVisibility(View.INVISIBLE);
                    this.emptyWordTextView.setVisibility(View.VISIBLE);

                    //사이즈가 0이 아니면 모두 지우기 버튼을 표시하고, 비었다 메시지 숨김
                } else {
                    this.deleteAllButton.setVisibility(View.VISIBLE);
                    this.emptyWordTextView.setVisibility(View.INVISIBLE);
                }

                //어댑터에 다 설정해준다.
                this.recentValueRecyclerView.setAdapter(recentValueRecyclerViewAdapter);
            });
        }).start();
    }

    /**
     * 서버나 로컬에서 저장된 데이터를 불러올 예정.
     *
     * @return 데이터가 담긴 벡터.
     */
    public Vector<String> getDatas() {
        //임시 실제로는 이렇게 안하고 데이터베이스같은 곳에서 불러올 것이다.
        Vector<String> val = new Vector<>();
        val.add("새우깡");
        val.add("감자깡");
        val.add("참이슬");
        val.add("신라면");
        val.add("참깨라면");
        val.add("진라면");
        val.add("팔도비빔면");
        return val;
    }

    public class recentButtonRecyclerViewAdapter extends RecyclerView.Adapter<recentButtonRecyclerViewAdapter.RecentButtonRecyclerviewViewHolder> {


        public Vector<String> values;

        public recentButtonRecyclerViewAdapter(Vector<String> values){
            //create component
            this.values= new Vector<>();

            //set components
            this.values.addAll(values);
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
            String value=this.values.get(position);
            holder.setValueButton(value);
        }

        @Override
        public int getItemCount() {
            return this.values.size();
        }





        /**
         * 뷰 홀더
         */
        private class RecentButtonRecyclerviewViewHolder extends RecyclerView.ViewHolder {
            private Button valueButton;

            public RecentButtonRecyclerviewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.valueButton = itemView.findViewById(R.id.item_button);
//                itemView.setOnCreateContextMenuListener(new MyContextMenuListener());
            }

            public void setValueButton(String value) {
                this.valueButton.setText(value);
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle text = new Bundle();
                        text.putString(FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY, value);
                        getActivity().getSupportFragmentManager().setFragmentResult(FOOD_SEARCH_RECOMMENDATION_FRAGMENT_BUNDLE_KEY, text);
                    }
                };
                this.valueButton.setOnClickListener(listener);
            }



//            private class MyContextMenuListener implements View.OnCreateContextMenuListener{
//
//                @Override
//                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                    MenuItem delete = menu.add(Menu.NONE,1001,1,"삭제");
//                    delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                                Log.d("디버그","클릭 인식");
//                                values.remove(getAdapterPosition());
//                                notifyItemRemoved(getAdapterPosition());
//                                notifyItemRangeChanged(getAdapterPosition(),values.size());
//                                return true;
//                        }
//                    });
//                }
//            }

        }

    }

}