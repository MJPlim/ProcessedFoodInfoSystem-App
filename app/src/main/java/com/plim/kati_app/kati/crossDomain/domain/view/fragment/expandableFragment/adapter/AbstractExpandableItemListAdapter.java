package com.plim.kati_app.kati.crossDomain.domain.view.fragment.expandableFragment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;

import java.util.List;
import java.util.Vector;

/**
 * 어댑터 클래스.
 */
public class AbstractExpandableItemListAdapter extends RecyclerView.Adapter<AbstractExpandableViewHolder> {
    private Vector<String> values;
    private Activity activity;
    private DialogInterface.OnClickListener listener;

    public AbstractExpandableItemListAdapter(Activity activity, DialogInterface.OnClickListener listener) {
        this.values = new Vector<>();
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AbstractExpandableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_allergy_row, parent, false);
        return new AbstractExpandableViewHolder(view, this.activity, v -> this.delete((String) v.getTag()));
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractExpandableViewHolder holder, int position) {
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
        if (this.values.contains(value))
            KatiDialog.simplerAlertDialog(
                    this.activity,
                    "중복된 아이템",
                    value + "(이)라는 아이템이 이미 존재합니다.",
                    listener
            );
        else {
            this.values.add(value);
            this.notifyItemRangeChanged(values.indexOf(value), getItemCount());
        }
    }

    public void delete(String value) {
        Log.d("지우다",value);
        int index = this.values.indexOf(value);
        this.values.remove(index);
        this.notifyItemRemoved(index);
    }

    public List<String> getItems() {
        return this.values;
    }
}
