package com.prim.summer_ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.prim.base_lib.utils.DisplayUtils;
import com.prim.base_lib.utils.HiViewUtil;
import com.prim.summer_ui.R;
import com.prim.summer_ui.tab.common.ITabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc SummerTabBottomLayout Tab的容器
 * @time 3/22/21 - 2:35 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class SummerTabBottomLayout extends FrameLayout implements ITabLayout<SummerTabBottom, TabBottomInfo<?>> {
    /**
     * tab的监听集合
     */
    private List<OnTabSelectedListener<TabBottomInfo<?>>> tabSelectedListeners = new ArrayList<>();

    /**
     * 选中的tab信息
     */
    private TabBottomInfo<?> selectedInfo;

    private float bottomAlpha = 1f;

    /**
     * Tab的高度
     */
    private float tabBottomHeight = 50;

    /**
     * 头部线条的高度
     */
    private float bottomLineHeight = 0.5f;

    /**
     * 头部线条的颜色
     */
    private String bottomLineColor = "#dfe0e1";

    /**
     * Tab的信息集合
     */
    private List<TabBottomInfo<?>> infoList;

    public SummerTabBottomLayout(@NonNull Context context) {
        this(context, null);
    }

    public SummerTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummerTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public SummerTabBottom findTab(@NonNull TabBottomInfo<?> data) {
        //获取TAG
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof SummerTabBottom) {
                SummerTabBottom tab = (SummerTabBottom) child;
                if (tab.getTabInfo() == data) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<TabBottomInfo<?>> listener) {
        tabSelectedListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull TabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    private static final String TAG_TAB_BOTTOM = "TAG_TAG_BOTTOM";

    @Override
    public void inflateInfo(List<TabBottomInfo<?>> infoList) {
        if (infoList == null || infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        //移除之前已经添加的View
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        //清空选择的tab
        selectedInfo = null;
        //添加背景
        addBackground();
        //清除之前的所有 tab
        Iterator<OnTabSelectedListener<TabBottomInfo<?>>> iterator = tabSelectedListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof SummerTabBottom) {
                iterator.remove();
            }
        }
        //不使用LinerLayout
        FrameLayout flTab = new FrameLayout(getContext());
        int height = DisplayUtils.dp2px(tabBottomHeight, getResources());
        //获取到每一个tab的宽度
        int width = DisplayUtils.getDisplayWidthInPx(getContext()) / infoList.size();
        flTab.setTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < infoList.size(); i++) {
            TabBottomInfo<?> info = infoList.get(i);
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;
            SummerTabBottom tabBottom = new SummerTabBottom(getContext());
            //添加到listener
            tabSelectedListeners.add(tabBottom);
            //设置tab data
            tabBottom.setTabInfo(info);
            flTab.addView(tabBottom, params);
            tabBottom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSelected(info);
                }
            });
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        addBottomLine();
        addView(flTab, params);
        fixContentView();
    }

    /**
     * 添加一条线
     */
    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px(bottomLineHeight, getResources()));
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.bottomMargin = DisplayUtils.dp2px(tabBottomHeight - bottomLineHeight, getResources());
        addView(bottomLine, layoutParams);
        bottomLine.setAlpha(bottomAlpha);
    }

    private void onSelected(@NonNull TabBottomInfo<?> nextInfo) {
        for (OnTabSelectedListener<TabBottomInfo<?>> listener : tabSelectedListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    /**
     * 添加背景
     */
    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_layout_bg, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DisplayUtils.dp2px(tabBottomHeight, getResources()));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        view.setAlpha(bottomAlpha);
    }

    public void setBottomAlpha(float bottomAlpha) {
        this.bottomAlpha = bottomAlpha;
    }

    public void setTabBottomHeight(float tabBottomHeight) {
        this.tabBottomHeight = tabBottomHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    /**
     * 修复内容区域的底部padding
     */
    private void fixContentView() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        //给第一个view一个padding
        ViewGroup targetView = HiViewUtil.findTypeView(rootView, RecyclerView.class);
        if (targetView == null) {
            targetView = HiViewUtil.findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            targetView = HiViewUtil.findTypeView(rootView, AbsListView.class);
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, DisplayUtils.dp2px(tabBottomHeight, getResources()));
            //padding可以绘制的
            targetView.setClipToPadding(false);
        }
    }
}
