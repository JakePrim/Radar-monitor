package com.prim.summer_ui.tab.top;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.prim.summer_ui.tab.bottom.SummerTabBottom;
import com.prim.summer_ui.tab.bottom.TabBottomInfo;
import com.prim.summer_ui.tab.common.ITabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 3/23/21 - 4:02 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class SummerTabTopLayout extends HorizontalScrollView implements ITabLayout<SummerTabTop, TabTopInfo<?>> {

    private List<OnTabSelectedListener<TabTopInfo<?>>> tabSelectedListeners = new ArrayList<>();

    private TabTopInfo<?> selectedInfo;

    private List<TabTopInfo<?>> infoList;

    private static final String TAG_TAB_TOP = "TAG_TAG_TOP";

    public SummerTabTopLayout(Context context) {
        this(context, null);
    }

    public SummerTabTopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummerTabTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //隐藏bar
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    public SummerTabTop findTab(@NonNull TabTopInfo<?> data) {
        //获取TAG
        ViewGroup ll = findViewWithTag(TAG_TAB_TOP);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof SummerTabTop) {
                SummerTabTop tab = (SummerTabTop) child;
                if (tab.getTabInfo() == data) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<TabTopInfo<?>> listener) {
        if (tabSelectedListeners != null) {
            tabSelectedListeners.add(listener);
        }
    }

    @Override
    public void defaultSelected(@NonNull TabTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(List<TabTopInfo<?>> infoList) {
        if (infoList == null || infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        //清空选择的tab
        LinearLayout rootLayout = getRootLayout(true);
        rootLayout.setTag(TAG_TAB_TOP);
        selectedInfo = null;
        //清除之前的所有 tab
        Iterator<OnTabSelectedListener<TabTopInfo<?>>> iterator = tabSelectedListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof SummerTabTop) {
                iterator.remove();
            }
        }
        for (int i = 0; i < infoList.size(); i++) {
            TabTopInfo<?> info = infoList.get(i);
            SummerTabTop tabTop = new SummerTabTop(getContext());
            tabSelectedListeners.add(tabTop);
            tabTop.setTabInfo(info);
            rootLayout.addView(tabTop);
            tabTop.setOnClickListener(v -> onSelected(info));
        }
    }

    private void onSelected(@NonNull TabTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<TabTopInfo<?>> listener : tabSelectedListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    private LinearLayout getRootLayout(boolean clear) {
        LinearLayout rootView = (LinearLayout) getChildAt(0);
        if (rootView == null) {
            //创建LinearLayout
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(rootView, params);
        } else if (clear) {
            //清除所有的子view
            rootView.removeAllViews();
        }
        return rootView;
    }
}
