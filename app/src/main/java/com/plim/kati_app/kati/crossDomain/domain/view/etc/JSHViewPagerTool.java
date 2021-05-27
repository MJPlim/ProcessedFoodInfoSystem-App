package com.plim.kati_app.kati.crossDomain.domain.view.etc;


import android.os.Handler;

import androidx.viewpager2.widget.ViewPager2;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class JSHViewPagerTool {

    public static void setAutoSlide(ViewPager2 target, int interval){
        AtomicInteger currentPage= new AtomicInteger();
        final Handler handler = new Handler();
        final Runnable update = () -> {
            if(currentPage.get() == 5) currentPage.set(0);
            target.setCurrentItem(currentPage.getAndIncrement(), true);
        };
        new Timer().schedule(new TimerTask() {@Override public void run() { handler.post(update); }}, 500, interval);
    }

    public static void setEffect(ViewPager2 target) {
        float MIN_SCALE = 0.85f;
        float MIN_ALPHA = 0.5f;
        target.setPageTransformer((view, position) -> {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);
            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }
                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            } else { // (1,+Infinity]
                view.setAlpha(0f); // This page is way off-screen to the right.
            }
        });
    }
}
