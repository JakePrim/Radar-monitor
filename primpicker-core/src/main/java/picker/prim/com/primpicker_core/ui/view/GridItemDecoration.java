package picker.prim.com.primpicker_core.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpanCount;
    private int mSpace;
    private boolean mIncludeEdge;

    public GridItemDecoration(int mSpanCount, int mSpace, boolean mIncludeEdge) {
        this.mSpanCount = mSpanCount;
        this.mSpace = mSpace;
        this.mIncludeEdge = mIncludeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % mSpanCount; // item column

        if (mIncludeEdge) {
            outRect.left = mSpace - column * mSpace / mSpanCount;
            outRect.right = (column + 1) * mSpace / mSpanCount;
            if (position < mSpanCount) { // top edge
                outRect.top = mSpace;
            }
            outRect.bottom = mSpace; // item bottom
        } else {
            outRect.left = column * mSpace / mSpanCount;
            outRect.right = mSpace - (column + 1) * mSpace / mSpanCount;
            if (position >= mSpanCount) {
                outRect.top = mSpace; // item top
            }
        }
    }
}
