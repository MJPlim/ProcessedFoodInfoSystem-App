package com.plim.kati_app.domain.asset;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.DetailTableItem;

import java.util.HashMap;

import static com.plim.kati_app.domain.constant.Constant_yun.ABSTRACT_TABLE_FRAGMENT_LARGE;
import static com.plim.kati_app.domain.constant.Constant_yun.ABSTRACT_TABLE_FRAGMENT_SMALL;

/**
 * 재사용 할 테이블 모양 프레그먼트.
 */
public abstract class AbstractTableFragment extends GetResultFragment {

    //associate view
    private TextView nameTextView, expandButton;
    private RecyclerView recyclerView;

    //component
    private DetailTableItem detailTableItem;
    private RecyclerAdapter adapter;

    //working variable
    private boolean isExpanded = true;

    public AbstractTableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.abstract_detail_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //find view
        this.nameTextView = view.findViewById(R.id.detailInformationTableTitle_NameTextView);
        this.expandButton = view.findViewById(R.id.detailInformationTableTitle_expandButton);
        this.recyclerView = view.findViewById(R.id.detailInformationTable_recyclerView);

        //set view
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        this.adapter = new RecyclerAdapter();
        this.recyclerView.setAdapter(this.adapter);
        this.expandButton.setOnClickListener(v -> changeVisibility(!this.isExpanded));

        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 데이터를 ui에 표시한다.
     * @param detailTableItem 이 테이블을 위한 데이터 클래스 타입.
     */
    protected void setItemValues(DetailTableItem detailTableItem) {
        this.detailTableItem = detailTableItem;
        this.adapter.setItems(detailTableItem.getValueMap());
        this.adapter.notifyDataSetChanged();
        this.nameTextView.setText(this.detailTableItem.getName());
        this.nameTextView.setBackgroundColor(getContext().getResources().getColor(R.color.kati_yellow,getContext().getTheme()));
    }


    /**
     * 인터넷 펌 Expandable recyclerView 메소드. 임시. ValueAnimator를 활용하여 애니메이션으로 늘리고 줄여준다.
     * @param isExpanded
     */
    private void changeVisibility(final boolean isExpanded) {
        this.isExpanded = isExpanded;
        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, recyclerView.getHeight()) : ValueAnimator.ofInt(recyclerView.getHeight(), 0);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // imageView의 높이 변경
                recyclerView.getLayoutParams().height = (int) animation.getAnimatedValue();
                recyclerView.requestLayout();
                // imageView가 실제로 사라지게하는 부분
                recyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                expandButton.setText(isExpanded ? ABSTRACT_TABLE_FRAGMENT_SMALL : ABSTRACT_TABLE_FRAGMENT_LARGE);
            }
        });
        // Animation start
        va.start();
    }


    /**
     * 어댑터 클래스.
     */
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewViewHolder> {

        private HashMap<Integer, DetailTableItem.Data> itemMap;

        private RecyclerAdapter() {
            this.itemMap = new HashMap<>();
        }

        @NonNull
        @Override
        public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_detail_information_table, parent, false);

            return new RecyclerViewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {
            DetailTableItem.Data data = itemMap.get(position);
            holder.setValue(data);
        }


        @Override
        public int getItemCount() {
            return itemMap.entrySet().size();
        }

        public void clearItems() {
            this.itemMap = new HashMap<>();
            this.notifyDataSetChanged();
        }

        public void addItems(HashMap<Integer, DetailTableItem.Data> itemMap) {
            this.itemMap.putAll(itemMap);
            this.notifyDataSetChanged();
        }

        public void setItems(HashMap<Integer, DetailTableItem.Data> itemMap) {
            this.clearItems();
            this.addItems(itemMap);
        }

        /**
         * 뷰 홀더.
         */
        private class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
            private TextView title, value;

            public RecyclerViewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.title = itemView.findViewById(R.id.detailInformationTableTitle_nameTextView);
                this.value = itemView.findViewById(R.id.detailInformationTableTitle_valueTextView);
            }

            /**
             * ui에 데이터를 표시한다.
             * @param data 테이블의 하나의 행을 위한 데이터 인스턴스.
             */
            public void setValue(DetailTableItem.Data data) {
                this.title.setText(data.getTitle());
                this.value.setText(data.getValue());

            }

        }
    }

}