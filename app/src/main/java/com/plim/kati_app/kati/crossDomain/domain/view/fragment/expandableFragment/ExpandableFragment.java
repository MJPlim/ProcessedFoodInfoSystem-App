package com.plim.kati_app.kati.crossDomain.domain.view.fragment.expandableFragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.kati.crossDomain.domain.view.fragment.GetAndSetFragmentResultFragment;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ABSTRACT_TABLE_FRAGMENT_LARGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ABSTRACT_TABLE_FRAGMENT_SMALL;


public abstract class ExpandableFragment extends KatiViewModelFragment {

    //associate
    //view
    protected TextView expandButton;
    protected RecyclerView recyclerView;

    //working variable
    protected boolean isExpanded =true;


    protected void changeVisibility(final boolean isExpanded) {
        this.isExpanded = isExpanded;
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, recyclerView.getHeight()) : ValueAnimator.ofInt(recyclerView.getHeight(), 0);
        va.setDuration(500);
        va.addUpdateListener(animation -> {
            recyclerView.getLayoutParams().height = (int) animation.getAnimatedValue();
            recyclerView.requestLayout();
            recyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            expandButton.setText(isExpanded ? ABSTRACT_TABLE_FRAGMENT_SMALL : ABSTRACT_TABLE_FRAGMENT_LARGE);
        });
        va.start();
    }
}
