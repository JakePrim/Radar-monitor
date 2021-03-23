package com.prim.summer_common.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author prim
 * @version 1.0.0
 * @desc 用来管理选中哪个fragment
 * @time 3/23/21 - 2:08 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class SummerFragmentTabView extends FrameLayout {
    public SummerFragmentTabView(@NonNull Context context) {
        this(context, null);
    }

    public SummerFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummerFragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //引入adapter
    private SummerTabViewAdapter mAdapter;
    private int currentPosition;

    public SummerTabViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(SummerTabViewAdapter adapter) {
        if (this.mAdapter != null || adapter == null) {
            return;
        }
        this.mAdapter = adapter;
        //初始化的值
        currentPosition = -1;
    }

    /**
     * 设置选中第几个fragment
     *
     * @param position
     */
    public void setCurrentItem(int position) {
        if (position < 0 || position > mAdapter.getCount()) {
            return;
        }
        //选中当前之外的
        if (currentPosition != position) {
            currentPosition = position;
            mAdapter.instantiateItem(this, position);
        }
    }

    public Fragment getCurrentFragment() {
        if (mAdapter == null) {
            throw new IllegalArgumentException("please call setAdapter first");
        }
        return mAdapter.getCurFragment();
    }

    public int getCurrentItem() {
        return currentPosition;
    }
}
