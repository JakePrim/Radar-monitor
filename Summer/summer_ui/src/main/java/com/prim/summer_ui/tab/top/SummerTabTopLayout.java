package com.prim.summer_ui.tab.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.prim.base_lib.utils.DisplayUtils;
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

    /**
     * 选择对应的tab
     *
     * @param nextInfo
     */
    private void onSelected(@NonNull TabTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<TabTopInfo<?>> listener : tabSelectedListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
        autoScroll(nextInfo);
    }

    int tabWith;

    /**
     * 滚动几个tab
     */
    private int range = 2;

    /**
     * 设置点击某个tab,是否需要展示左侧或者右侧要有几个tab
     * @param range
     */
    public void setRange(int range) {
        this.range = range;
    }

    private static final String TAG = "SummerTabTopLayout";

    /**
     * 自动滚动，实现点击的位置能够自动滚动以展示前后2个
     *
     * @param nextInfo
     */
    private void autoScroll(TabTopInfo<?> nextInfo) {
        //获取点击tab的view
        SummerTabTop tab = findTab(nextInfo);
        if (tab == null) return;
        //获取点击的tab的位置
        int index = infoList.indexOf(nextInfo);
        int[] loc = new int[2];
        //获取点击的控件在屏幕的位置
        tab.getLocationInWindow(loc);
        int scrollWidth;
        if (tabWith == 0) {
            tabWith = tab.getWidth();
        }
        //判断点击了屏幕左侧还是右侧
        if ((loc[0] + tabWith / 2) > DisplayUtils.getDisplayWidthInPx(getContext()) / 2) {
            //在屏幕的右侧
            scrollWidth = rangeScrollWidth(index, range);
        } else {
            //在屏幕的左侧
            scrollWidth = rangeScrollWidth(index, -range);
        }
        Log.e(TAG, "autoScroll: " + scrollWidth);
        //进行滚动
        scrollTo(getScrollX() + scrollWidth, 0);
    }

    /**
     * 获取可滚动的范围
     *
     * @param index 从第几个开始
     * @param range 向前向后的范围
     * @return 可滚动的范围
     */
    private int rangeScrollWidth(int index, int range) {
        int scrollWidth = 0;
        //遍历范围内
        for (int i = 0; i <= Math.abs(range); i++) {
            int next;
            if (range < 0) {
                next = range + i + index;
            } else {
                next = range - i + index;
            }
            if (next >= 0 && next < infoList.size()) {
                if (range < 0) {
                    scrollWidth -= scrollWidth(next, false);
                } else {
                    scrollWidth += scrollWidth(next, true);
                }
            }
        }
        return scrollWidth;
    }

    /**
     * 指定位置的空间可滚动的距离
     *
     * @param index   指定位置的空间
     * @param toRight 是否是点击了屏幕右侧
     * @return 可滚动的距离
     */
    private int scrollWidth(int index, boolean toRight) {
        SummerTabTop target = findTab(infoList.get(index));
        if (target == null) return 0;
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (toRight) {
            //如果点击了屏幕的右侧
            if (rect.right > tabWith) {//right坐标大于控件的宽度，说明完全没有显示
                return tabWith;
            } else {
                //显示部分，减去已显示的宽度
                return tabWith - rect.right;
            }
        } else {
            if (rect.left <= -tabWith) {//left坐标小于等于-空间宽度，说明完全没有显示
                return tabWith;
            } else if (rect.left > 0) {//显示部分
                return rect.left;
            }
            return 0;
        }
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
