package com.prim.summer_ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prim.base_lib.utils.DisplayUtils;

/**
 * @author prim
 * @version 1.0.0
 * @desc 下拉刷新的overlay视图，重载这个类定义自己的overlay
 * @time 3/26/21 - 3:39 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public abstract class SummerOverView extends FrameLayout {

    /**
     * 头部的状态
     */
    public enum SummerRefreshState {
        /**
         * 初始状态
         */
        STATE_INIT,
        /**
         * header展示状态
         */
        STATE_VISIBLE,
        /**
         * 刷新的状态
         */
        STATE_REFRESH,
        /**
         * 超出刷新位置，松手后的状态
         */
        STATE_OVER_RELEASE,
        /**
         * 超出可刷新距离的状态
         */
        STATE_OVER
    }

    protected SummerRefreshState mState = SummerRefreshState.STATE_INIT;

    /**
     * 触发下拉刷新需要的最小高度,只有达到最小高度才能出发下拉刷新
     */
    public int mPullRefreshHeight;

    /**
     * 最小阻尼
     */
    public float minDamp = 1.6f;

    /**
     * 最大阻尼
     */
    public float maxDamp = 2.2f;

    public SummerOverView(@NonNull Context context) {
        this(context, null);
    }

    public SummerOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummerOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        proInit();
    }

    /**
     * 预初始化 设置下拉刷新的高度
     */
    protected void proInit() {
        //下拉刷新的高度
        mPullRefreshHeight = DisplayUtils.dp2px(66f, getResources());
        init();
    }

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 滚动的坐标 Y轴的坐标以及下拉刷新的高度
     */
    protected abstract void onScroll(int scrollY, int pullRefreshHeight);

    /**
     * 显示overview
     */
    protected abstract void onVisible();

    /**
     * 超过overview，释放就会加载
     */
    protected abstract void onOver();

    /**
     * 开始刷新
     */
    protected abstract void onRefresh();

    /**
     * 刷新加载完成
     */
    protected abstract void onFinish();

    /**
     * 设置状态
     *
     * @param state SummerRefreshState
     */
    public void setState(SummerRefreshState state) {
        this.mState = state;
    }

    /**
     * 获取状态
     *
     * @return SummerRefreshState
     */
    public SummerRefreshState getState() {
        return mState;
    }
}
