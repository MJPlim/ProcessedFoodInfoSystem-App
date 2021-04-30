package com.plim.kati_app.domain.view.search.food.detail.fragment;

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

import com.google.android.material.tabs.TabLayout;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.GetResultFragment;
import com.plim.kati_app.domain.view.search.food.list.adapter.LightButtonRecyclerViewAdapter;

/**
 * 리뷰를 보여주는 fragment
 */
public class DetailReviewViewFragment extends GetResultFragment {

    private RecyclerView recyclerView;
    private TabLayout categoryTabLayout;

    public DetailReviewViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_review_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.categoryTabLayout = view.findViewById(R.id.reviewViewFragment_tabLayout);
        this.recyclerView = view.findViewById(R.id.reviewViewFragment_recyclerView);

        int i = 0;
        for (Constant_yun.EReviewCateGory cateGory : Constant_yun.EReviewCateGory.values()) {
            categoryTabLayout.addTab(categoryTabLayout.newTab().setText(cateGory.getName()), i);
        }

        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerView.setAdapter(new RecyclerAdapter());
    }

    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey = "temp";
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {

    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_review, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 3;
        }

        /**
         * 뷰 홀더
         */
        private class ViewHolder extends RecyclerView.ViewHolder {


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void setValue(String value) {
            }
        }
    }
}