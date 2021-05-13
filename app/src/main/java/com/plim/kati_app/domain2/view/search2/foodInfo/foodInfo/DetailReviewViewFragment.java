package com.plim.kati_app.domain2.view.search2.foodInfo.foodInfo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.constant.Constant_yun;
import com.plim.kati_app.domain.asset.GetResultFragment;
import com.plim.kati_app.domain2.katiCrossDomain.domain.view.KatiFoodFragment;

public class DetailReviewViewFragment extends KatiFoodFragment {

    // Associate
        // View
        private RecyclerView recyclerView;
        private TabLayout categoryTabLayout;

    @Override
    protected int getLayoutId() { return R.layout.fragment_detail_review_view; }
    @Override
    protected void associateView(View view) {
        this.categoryTabLayout = view.findViewById(R.id.reviewViewFragment_tabLayout);
        this.recyclerView = view.findViewById(R.id.reviewViewFragment_recyclerView);
    }
    @Override
    protected void initializeView() {
        int i = 0;
        for (Constant_yun.EReviewCateGory cateGory : Constant_yun.EReviewCateGory.values()) {
            this.categoryTabLayout.addTab(this.categoryTabLayout.newTab().setText(cateGory.getName()), i);
        }
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerView.setAdapter(new RecyclerAdapter());
    }
    @Override
    protected void katiEntityUpdated() { }
    @Override
    public void foodModelDataUpdated() { }

    /**
     * 어짜피 버리던가 고치든가 할 것 같으니 정리 안 했음. 근데 신경 쓰여 죽겄네
     */
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_review, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { }
        @Override
        public int getItemCount() {
            return 3;
        }
        private class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
            public void setValue(String value) {
            }
        }
    }
}