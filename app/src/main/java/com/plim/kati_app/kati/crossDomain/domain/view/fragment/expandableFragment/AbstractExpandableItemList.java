package com.plim.kati_app.kati.crossDomain.domain.view.fragment.expandableFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.expandableFragment.adapter.AbstractExpandableItemListAdapter;

import java.util.List;
import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;


public abstract class AbstractExpandableItemList extends ExpandableFragment {
    private TextView title;

    private EditText editText;
    private ImageView addButton;

    private Button saveButton;

    private AbstractExpandableItemListAdapter adapter;
    private ExpandableListItem expandableListItem;

    @Override
    protected int getLayoutId() {
        return R.layout.abstract_expandable_item_list;
    }

    @Override
    protected void associateView(View view) {
        this.expandButton = view.findViewById(R.id.expandableItemListFragment_expandButton);
        this.recyclerView = view.findViewById(R.id.expandableItemListFragment_recyclerView);

        this.title = view.findViewById(R.id.expandableItemListFragment_titleTextView);
        this.editText = view.findViewById(R.id.allergyItem_editText);
        this.addButton = view.findViewById(R.id.allergyItem_add);
        this.saveButton=view.findViewById(R.id.expandableItemListFragment_saveButton);
    }

    @Override
    protected void initializeView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.adapter = new AbstractExpandableItemListAdapter(this.getActivity(),(dialog, which) -> resetEditText());
        this.recyclerView.setAdapter(this.adapter);
        this.expandButton.setOnClickListener(v -> changeVisibility(!this.isExpanded));
        this.addButton.setOnClickListener(v -> this.addItem(editText.getText().toString()));
        this.saveButton.setOnClickListener(v->this.onSave());
    }

    protected void resetEditText() {
        this.editText.setText("");
    }
    protected void addItemToAdapter(String value) {
        this.adapter.add(value);
    }
    protected List<String> getItemsFromAdapter() {
        return this.adapter.getItems();
    }
    protected abstract void addItem(String toString);

    protected void setItemValues(ExpandableListItem expandableListItem) {
        this.expandableListItem = expandableListItem;
        this.adapter.setItems(this.expandableListItem.getValues());
        this.title.setText(this.expandableListItem.getTitle());
        this.adapter.notifyDataSetChanged();
    }

    protected abstract void onSave();

    @AllArgsConstructor
    @Getter
    protected class ExpandableListItem {
        private String title;
        private Vector<String> values;
    }
}