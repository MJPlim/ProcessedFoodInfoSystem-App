package com.plim.kati_app.domain.view.search.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.view.search.adapter.LightButtonRecyclerViewAdapter;
import com.plim.kati_app.domain.view.search.adapter.RankRecyclerViewAdapter;

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

    //adapter
    private recentButtonRecyclerViewAdapter recentValueRecyclerViewAdapter;
    private RankRecyclerViewAdapter rankRecyclerViewAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_search_recommendation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //associate view
        this.recentValueRecyclerView = view.findViewById(R.id.foodSearchRecommendationFragment_recentValuesRecyclerView);
        this.rankRecyclerView = view.findViewById(R.id.foodSearchRecommendationFragment_rankRecyclerView);

        //데이터 받아오기.
        Vector<String> val = this.getDatas();


        //create adapter
        this.recentValueRecyclerViewAdapter = new recentButtonRecyclerViewAdapter(val);
        this.rankRecyclerViewAdapter = new RankRecyclerViewAdapter(val);

        //set view
        this.recentValueRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.recentValueRecyclerView.setAdapter(recentValueRecyclerViewAdapter);
        this.rankRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.rankRecyclerView.setAdapter(rankRecyclerViewAdapter);

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

    public class recentButtonRecyclerViewAdapter extends LightButtonRecyclerViewAdapter {

        public recentButtonRecyclerViewAdapter(Vector<String> values) {
            super(values);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context= parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_light_button,parent,false);
            RecentButtonRecyclerviewViewHolder viewHolder= new RecentButtonRecyclerviewViewHolder(view);

            return viewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String value = this.values.get(position);
            ((RecentButtonRecyclerviewViewHolder) holder).setValueButton(value);
        }


        /**
         * 뷰 홀더
         */
        public class RecentButtonRecyclerviewViewHolder extends RecyclerView.ViewHolder {
            private Button valueButton;

            public RecentButtonRecyclerviewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.valueButton = itemView.findViewById(R.id.item_button);
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
        }
    }

}