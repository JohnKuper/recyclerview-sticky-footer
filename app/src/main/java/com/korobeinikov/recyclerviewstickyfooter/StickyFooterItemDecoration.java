package com.korobeinikov.recyclerviewstickyfooter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Dmitriy_Korobeinikov on 8/3/2016.
 * Uses to make footer sticky to the bottom of the screen when {@link RecyclerView} contains a few rows.
 */
public class StickyFooterItemDecoration extends RecyclerView.ItemDecoration {

    private static final int OFF_SCREEN_OFFSET = 5000;

    @Override
    public void getItemOffsets(Rect outRect, final View view, final RecyclerView parent, RecyclerView.State state) {
        int adapterItemCount = parent.getAdapter().getItemCount();
        if (isFooter(parent, view, adapterItemCount)) {
            if (view.getHeight() == 0 && state.didStructureChange()) {
                hideFooterAndUpdate(outRect, view, parent);
            } else {
                outRect.set(0, calculateTopOffset(parent, view, adapterItemCount), 0, 0);
            }
        }
    }

    private void hideFooterAndUpdate(Rect outRect, final View footerView, final RecyclerView parent) {
        outRect.set(0, OFF_SCREEN_OFFSET, 0, 0);
        footerView.post(new Runnable() {
            @Override
            public void run() {
                parent.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private int calculateTopOffset(RecyclerView parent, View footerView, int itemCount) {
        int topOffset = parent.getHeight() - visibleChildrenHeightWithFooter(parent, footerView, itemCount);
        return topOffset < 0 ? 0 : topOffset;
    }

    private int visibleChildrenHeightWithFooter(RecyclerView parent, View footerView, int itemCount) {
        int totalHeight = 0;
        int onScreenItemCount = Math.min(parent.getChildCount(), itemCount);
        for (int i = 0; i < onScreenItemCount - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            totalHeight += child.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
        }
        return totalHeight + footerView.getHeight() + parent.getPaddingTop() + parent.getPaddingBottom();
    }

    private boolean isFooter(RecyclerView parent, View view, int itemCount) {
        return parent.getChildAdapterPosition(view) == itemCount - 1;
    }
}
