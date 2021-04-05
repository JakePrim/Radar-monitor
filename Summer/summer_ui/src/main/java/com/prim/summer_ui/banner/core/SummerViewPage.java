package com.prim.summer_ui.banner.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.lang.reflect.Field;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_UP;

/**
 * @author prim
 * @version 1.0.0
 * @desc banner的viewpager
 * @time 4/1/21 - 11:51 AM
 * @contact https://jakeprim.cn
 * @name Summer
 */
public class SummerViewPage extends ViewPager {

    /**
     * 间隔多长时间
     */
    private int mIntervalTime;

    /**
     * 是否开启自动轮播
     */
    private boolean mAutoPlay = true;

    /**
     * isLayout
     */
    private boolean isLayout;

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //切换下一个
            next();
            mHandler.postDelayed(this, mIntervalTime);
        }
    };

    public SummerViewPage(@NonNull Context context) {
        this(context, null);
    }

    public SummerViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 对外提供设置ViewPage的速度一个方法
     */
    public void setScrollDuration(int duration) {
        try {
            Field field = ViewPager.class.getField("mScroller");
            field.setAccessible(true);
            field.set(this, new SummerBannerScroller(getContext(), duration));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取下一个
     */
    private int next() {
        int nextPosition = -1;
        if (getAdapter() == null || getAdapter().getCount() <= 1) {
            stop();
            return nextPosition;
        }
        nextPosition = getCurrentItem() + 1;
        //如果下一个索引大于view的数量时重新开始
        if (nextPosition >= getAdapter().getCount()) {
            //获取第一个索引
            nextPosition = ((BannerAdapter) getAdapter()).getFirstItem();
        }
        setCurrentItem(nextPosition, true);
        return nextPosition;
    }

    /**
     * 设置间隔时间
     *
     * @param intervalTime
     */
    public void setIntervalTime(int intervalTime) {
        this.mIntervalTime = intervalTime;
    }

    /**
     * 开始播放
     */
    public void start() {
        //移除Handler
        mHandler.removeCallbacksAndMessages(null);
        //如果开启了自动播放
        if (mAutoPlay) {
            mHandler.postDelayed(mRunnable, mIntervalTime);
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        //停止自动播放
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 是否开启自动播放
     */
    public void setAutoPlay(boolean isAutoPlay) {
        this.mAutoPlay = isAutoPlay;
        if (!isAutoPlay) {
            //如果没有开启自动播放 移除
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.isLayout = true;
    }

    /**
     * viewpager 滑动回来的时候
     * 1. RecyclerView 滚动上去，直到ViewPager看不见，再滚动下来，ViewPager下一次切换没有动画。
     * 2. 当ViewPager滚动到一半的时候，RecyclerView滚动上去，再滚动下来，ViewPager会卡在一半。
     * 原因1：ViewPager里有一个私有变量mFirstLayout 它是表示是不是第一次显示布局，如果是true，则使用无动画的方式
     * 显示item，如果是false，则使用动画方式显示item
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isLayout && getAdapter() != null && getAdapter().getCount() > 0) {
            try {
                Field field = this.getClass().getDeclaredField("mFirstLayout");
                field.setAccessible(true);
                //设置为false 动画的方式显示
                field.set(this, false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //开启滚动
        start();
    }

    /**
     * Viewpager 滑动滑出销毁的时候
     */
    @Override
    protected void onDetachedFromWindow() {
        if (((Activity) getContext()).isFinishing()) {
            //当回收的时候动画会停止 会有可能导致动画滑动到了一半的问题
            super.onDetachedFromWindow();
        }
        stop();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case ACTION_UP:
            case ACTION_CANCEL:
                //松开手后开始自动播放
                start();
                break;
            default:
                //其他事件停止播放
                stop();
                break;
        }
        return super.onTouchEvent(ev);
    }


}
