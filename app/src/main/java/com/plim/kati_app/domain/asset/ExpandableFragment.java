package com.plim.kati_app.domain.asset;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static com.plim.kati_app.constants.Constant_yun.ABSTRACT_TABLE_FRAGMENT_LARGE;
import static com.plim.kati_app.constants.Constant_yun.ABSTRACT_TABLE_FRAGMENT_SMALL;

/**
 * 축소 확장 버튼과, 리사이클러뷰와, 축소확장여부를 가지는 fragment.
 */
public abstract class ExpandableFragment extends GetResultFragment {

    protected TextView expandButton;
    protected RecyclerView recyclerView;
    protected boolean isExpanded =true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 인터넷 펌 Expandable recyclerView 메소드. 임시. ValueAnimator를 활용하여 애니메이션으로 늘리고 줄여준다.
     * @param isExpanded 확장할지 안할지.
     */
    protected void changeVisibility(final boolean isExpanded) {
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
}
