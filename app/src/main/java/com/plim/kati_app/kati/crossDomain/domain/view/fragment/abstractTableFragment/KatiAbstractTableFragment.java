package com.plim.kati_app.kati.crossDomain.domain.view.fragment.abstractTableFragment;

import android.animation.ValueAnimator;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiFoodFragment;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ABSTRACT_TABLE_FRAGMENT_LARGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ABSTRACT_TABLE_FRAGMENT_SMALL;

public abstract class KatiAbstractTableFragment extends KatiFoodFragment {

    // Working Variable
    private boolean isExpanded = true;

    // Associate
        // View
        private TextView nameTextView, expandButton;
        private RecyclerView recyclerView;

    // Component
        // View
        private TableAdapter adapter;
        // Model
        private TableInfo tableInfo;

    /**
     * System Life Cycle Callback
     */
    @Override
    protected int getLayoutId() { return R.layout.abstract_detail_table; }
    @Override
    protected void associateView(View view) {
        this.nameTextView = view.findViewById(R.id.detailInformationTableTitle_NameTextView);
        this.expandButton = view.findViewById(R.id.detailInformationTableTitle_expandButton);
        this.recyclerView = view.findViewById(R.id.detailInformationTable_recyclerView);
    }
    @Override
    protected void initializeView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        this.adapter = new TableAdapter();
        this.recyclerView.setAdapter(this.adapter);
        this.expandButton.setOnClickListener(v -> changeVisibility());
    }
    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    /**
     * Method
     */
    protected void setItemValues(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        this.adapter.setItems(tableInfo.getValueMap());
        this.adapter.notifyDataSetChanged();
        this.nameTextView.setText(this.tableInfo.getName());
        this.nameTextView.setBackgroundColor(getContext().getResources().getColor(R.color.kati_yellow,getContext().getTheme()));
    }
    private void changeVisibility() {
        this.isExpanded = !this.isExpanded;
        ValueAnimator va = this.isExpanded ? ValueAnimator.ofInt(0, this.recyclerView.getHeight()) : ValueAnimator.ofInt(this.recyclerView.getHeight(), 0);
        va.setDuration(500);
        va.addUpdateListener(animation -> {
            this.recyclerView.getLayoutParams().height = (int) animation.getAnimatedValue();
            this.recyclerView.requestLayout();
            this.recyclerView.setVisibility(this.isExpanded ? View.VISIBLE : View.GONE);
            this.expandButton.setText(this.isExpanded ? ABSTRACT_TABLE_FRAGMENT_SMALL : ABSTRACT_TABLE_FRAGMENT_LARGE);
        });
        va.start();
    }
}