package com.plim.kati_app.domain.asset;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.model.DetailTableItem;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;


public abstract class AbstractExpandableItemList extends ExpandableFragment {
    private TextView title;

    private EditText editText;
    private ImageView addbutton;

    //    private DetailTableItem detailTableItem;
    private RecyclerAdapter adapter;
    private ExpandableListItem expandableListItem;


    public AbstractExpandableItemList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.abstract_expandable_item_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.title = view.findViewById(R.id.expandableItemListFragment_titleTextView);
        this.expandButton = view.findViewById(R.id.expandableItemListFragment_expandButton);
        this.recyclerView = view.findViewById(R.id.expandableItemListFragment_recyclerView);

        this.editText = view.findViewById(R.id.allergyItem_editText);
        this.addbutton = view.findViewById(R.id.allergyItem_add);

        //set view
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.adapter = new RecyclerAdapter();
        this.recyclerView.setAdapter(this.adapter);
        this.expandButton.setOnClickListener(v -> changeVisibility(!this.isExpanded));

        this.addbutton.setOnClickListener(v -> this.addItem(editText.getText().toString()));

        super.onViewCreated(view, savedInstanceState);
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

    /**
     * 데이터를 ui에 표시한다.
     *
     * @param expandableListItem 이 테이블을 위한 데이터 클래스 타입.
     */
    protected void setItemValues(ExpandableListItem expandableListItem) {
        this.expandableListItem = expandableListItem;
        this.adapter.setItems(this.expandableListItem.getValues());
        this.title.setText(this.expandableListItem.getTitle());
        this.adapter.notifyDataSetChanged();
//        this.recyclerView.setAdapter(this.adapter);


    }

    @AllArgsConstructor
    @Getter
    protected class ExpandableListItem {
        private String title;
        private Vector<String> values;
    }

    /**
     * 어댑터 클래스.
     */
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewViewHolder> {

        private Vector<String> values;

        private RecyclerAdapter() {
            this.values = new Vector<>();
        }

        @NonNull
        @Override
        public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_allergy_row, parent, false);

            return new RecyclerViewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {
            holder.setValue(values.get(position));
        }


        @Override
        public int getItemCount() {
            return values.size();
        }


        public void setItems(Vector<String> values) {
            this.values = values;
            this.notifyDataSetChanged();
        }

        public void add(String value) {
            if (this.values.indexOf(value) != -1)
                KatiDialog.simpleAlertDialog(
                        getContext(),
                        "중복된 아이템",
                        value + "(이)라는 아이템이 이미 존재합니다.",
                        (dialog, which) -> resetEditText(),
                        getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                ).showDialog();
            else {
                this.values.add(value);
                this.notifyItemRangeChanged(values.indexOf(value), getItemCount());
            }
        }
        public void delete(String value){
            int index = this.values.indexOf(value);
            this.values.remove(index);
            this.notifyItemRemoved(index);
        }

        public List<String> getItems() {
            return this.values;
        }

        /**
         * 뷰 홀더.
         */
        private class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;
            private ImageView delView;

            public RecyclerViewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.textView = itemView.findViewById(R.id.allergyItem_value);
                this.delView = itemView.findViewById(R.id.allergyItem_delete);
            }

            /**
             * ui에 데이터를 표시한다.
             *
             * @param value 테이블의 하나의 행을 위한 데이터 인스턴스.
             */
            public void setValue(String value) {
                Log.d("디버그", value);
                this.textView.setText(value);
                this.delView.setColorFilter(getResources().getColor(R.color.kati_orange,getContext().getTheme()));
                this.delView.setOnClickListener(v -> {
                    Log.d("디버그","삭제 눌림");
                    adapter.delete(value);
                });
            }

        }
    }
}