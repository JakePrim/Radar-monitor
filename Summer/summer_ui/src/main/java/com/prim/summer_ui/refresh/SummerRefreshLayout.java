package com.prim.summer_ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author prim
 * @version 1.0.0
 * @desc 包裹整个下拉刷新列表的容器：列表+刷新头部+刷新尾部
 * @time 3/26/21 - 3:48 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class SummerRefreshLayout extends FrameLayout implements IRefresh {

    /**
     * 下拉刷新的状态
     */
    private SummerOverView.SummerRefreshState mState;
    /**
     * 下拉刷新的手势监听
     */
    private GestureDetector mGestureDetector;

    /**
     * 下拉刷新的监听接口
     */
    private SummerRefreshListener summerRefreshListener;

    /**
     * 下拉刷新的头部view
     */
    protected SummerOverView summerOverView;

    /**
     * 下拉时Y轴的坐标
     */
    private int mLastY;

    /**
     * 刷新时是否禁止滚动
     */
    private boolean disableRefreshScroll;

    private AutoScroller autoScroller;

    public SummerRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public SummerRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummerRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化方法
     */
    private void init() {
        //初始化手势监听器
        mGestureDetector = new GestureDetector(getContext(), gestureDetector);
        //初始化自动滚动器
        autoScroller = new AutoScroller();

    }

    /**
     * 刷新时是否禁止滚动
     *
     * @param disableRefreshScroll
     */
    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    /**
     * 刷新完成
     */
    @Override
    public void refreshFinished() {
        View head = getChildAt(0);
        summerOverView.onFinish();
        summerOverView.setState(SummerOverView.SummerRefreshState.STATE_INIT);
        int bottom = head.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }
        mState = SummerOverView.SummerRefreshState.STATE_INIT;
    }

    /**
     * 下拉刷新监听
     *
     * @param summerRefreshListener
     */
    @Override
    public void setRefreshListener(SummerRefreshListener summerRefreshListener) {
        this.summerRefreshListener = summerRefreshListener;
    }


    /**
     * 设置下拉刷新的视图
     */
    @Override
    public void setRefreshOverView(SummerOverView overView) {
        if (this.summerOverView != null) {
            removeView(this.summerOverView);
        }
        this.summerOverView = overView;

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(this.summerOverView, 0, params);
    }

    /**
     * 对子view进行排版
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //定义head与child的位置
        View head = getChildAt(0);
        View child = getChildAt(1);
        if (head != null && child != null) {
            int childTop = child.getTop();
            if (mState == SummerOverView.SummerRefreshState.STATE_REFRESH) {
                //如果是刷新状态
                head.layout(0, summerOverView.mPullRefreshHeight - head.getMeasuredHeight(),
                        right, summerOverView.mPullRefreshHeight);
                //在head之下
                child.layout(0, summerOverView.mPullRefreshHeight, right,
                        summerOverView.mPullRefreshHeight + child.getMeasuredHeight());
            } else {
                head.layout(0, childTop - head.getMeasuredHeight(),
                        right, childTop);
                child.layout(0, childTop, right,
                        childTop + child.getMeasuredHeight());
            }
        }
        //添加剩余的view
        View other;
        for (int i = 2; i < getChildCount(); i++) {
            other = getChildAt(i);
            other.layout(0, top, right, bottom);
        }
    }

    SummerGestureDetector gestureDetector = new SummerGestureDetector() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (Math.abs(distanceX) > Math.abs(distanceY)
                    || summerRefreshListener != null && !summerRefreshListener.enableRefresh()) {
                //发生横向滑动，或刷新被禁止则不处理
                return false;
            }
            //刷新过程中禁止滚动(需要设置disableRefreshScroll=true)
            if (disableRefreshScroll && mState == SummerOverView.SummerRefreshState.STATE_REFRESH) {
                return true;
            }
            //获取头部刷新视图
            View head = getChildAt(0);
            //获取滚动容器包裹的列表
            View child = SummerScrollUtils.findScrollableChild(SummerRefreshLayout.this);

            //判断列表是否发生了滚动
            if (SummerScrollUtils.childScrolled(child)) {
                //列表要滚动到顶部才能下拉
                return false;//不去处理
            }

            //没有刷新或者没有达到可以刷新的距离，并且头部已经划出或下拉没有达到可刷新的距离
            if ((mState != SummerOverView.SummerRefreshState.STATE_REFRESH ||
                    head.getBottom() <= summerOverView.mPullRefreshHeight)
                    && (head.getBottom() > 0 || distanceY <= 0.0F)) {
                //如果还在滑动中状态没有处于松手的状态
                if (mState != SummerOverView.SummerRefreshState.STATE_OVER_RELEASE) {
                    int seed;
                    //根据阻尼计算速度
                    if (child.getTop() < summerOverView.mPullRefreshHeight) {
                        //没有滑动到设定的刷新的高度 阻尼使用小的阻尼
                        seed = (int) (mLastY / summerOverView.minDamp);
                    } else {
                        //如果超过了设定的刷新的高度 阻尼使用大的阻尼
                        seed = (int) (mLastY / summerOverView.maxDamp);
                    }
                    //如果正在刷新中，则不允许在滑动的时候改变状态
                    boolean bool = moveDown(seed, true);
                    mLastY = (int) -distanceY;
                    return bool;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    };

    /**
     * 根据偏移量移动header与child
     *
     * @param offsetY 偏移量
     * @param nonAuto 是否非自动滚动触发
     * @return
     */
    private boolean moveDown(int offsetY, boolean nonAuto) {
        View head = getChildAt(0);
        View child = getChildAt(1);
        int childTop = child.getTop() + offsetY;
        if (childTop <= 0) {
            //异常情况
            offsetY = -child.getTop();
            //移动head与child的位置到原始位置
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (mState != SummerOverView.SummerRefreshState.STATE_REFRESH) {
                mState = SummerOverView.SummerRefreshState.STATE_INIT;
            }
        } else if (mState == SummerOverView.SummerRefreshState.STATE_REFRESH &&
                childTop > summerOverView.mPullRefreshHeight) {
            //正在下拉刷新中，禁止继续下拉
            return false;
        } else if (childTop <= summerOverView.mPullRefreshHeight) {
            //还没有超出设定的刷新距离
            if (summerOverView.getState() != SummerOverView.SummerRefreshState.STATE_VISIBLE
                    && nonAuto) {//头部开始显示
                //头部开始露出了
                summerOverView.onVisible();
                summerOverView.setState(SummerOverView.SummerRefreshState.STATE_VISIBLE);
                mState = SummerOverView.SummerRefreshState.STATE_VISIBLE;
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (childTop == summerOverView.mPullRefreshHeight
                    && mState == SummerOverView.SummerRefreshState.STATE_OVER_RELEASE) {
                //下拉刷新完成
                refresh();
            }
        } else {
            if (summerOverView.getState() != SummerOverView.SummerRefreshState.STATE_OVER
                    && nonAuto) {
                //超出刷新位置
                summerOverView.onOver();
                summerOverView.setState(SummerOverView.SummerRefreshState.STATE_OVER);
                mState = SummerOverView.SummerRefreshState.STATE_OVER;
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
        }

        if (summerOverView != null) {
            summerOverView.onScroll(head.getBottom(), summerOverView.mPullRefreshHeight);
        }
        return true;
    }

    /**
     * 开始刷新
     */
    private void refresh() {
        if (summerRefreshListener != null) {
            mState = SummerOverView.SummerRefreshState.STATE_REFRESH;
            summerOverView.onRefresh();
            summerOverView.setState(SummerOverView.SummerRefreshState.STATE_REFRESH);
            summerRefreshListener.onRefresh();
        }
    }

    //事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View head = getChildAt(0);//获取整个布局的第一个子view
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL
                || ev.getAction() == MotionEvent.ACTION_POINTER_INDEX_MASK) {
            //用户松开手
            if (head.getBottom() > 0) {//说明头部view被拉下来了
                //非正在刷新的状态
                if (mState != SummerOverView.SummerRefreshState.STATE_REFRESH) {
                    //如果拉下来还没有刷新则恢复成原样
                    recover(head.getBottom());
                    return false;//这是就不然它进行手势触摸
                }

            }
            //将最后距离更新为0
            mLastY = 0;
        }
        //用户一直下拉没有松手
        boolean consumed = mGestureDetector.onTouchEvent(ev);
        if ((!consumed ||
                (mState != SummerOverView.SummerRefreshState.STATE_INIT
                        && mState != SummerOverView.SummerRefreshState.STATE_REFRESH))
                && head.getBottom() != 0) {
            ev.setAction(MotionEvent.ACTION_CANCEL);//让父类接收不到真实的事件
            return super.dispatchTouchEvent(ev);
        }
        if (consumed) {
            //消费了这个事件
            return true;
        } else {
            //否则让父类触发事件
            return super.dispatchTouchEvent(ev);
        }
    }

    /**
     * 这个方法是头部慢慢的平滑滚动回去
     *
     * @param dis
     */
    private void recover(int dis) {
        //如果监听不为空并且下拉出来的高度大于头部视图设定的刷新的高度
        if (summerRefreshListener != null && dis > summerOverView.mPullRefreshHeight) {
            //滚动到指定位置bottom - mPullRefreshHeight 平滑的完成视图的滚动
            autoScroller.recover(dis - summerOverView.mPullRefreshHeight);
            //设置状态
            mState = SummerOverView.SummerRefreshState.STATE_OVER_RELEASE;
        } else {
            //如果下拉的高度小于头部视图设定的刷新的高度 则直接滚动
            autoScroller.recover(dis);
        }
    }


    /**
     * 借助scroller完成视图的自动滚动
     */
    private class AutoScroller implements Runnable {

        private Scroller mScroller;
        private int mLastY;//最后一次y的位置
        private boolean mIsFinished;//是否完成了滚动

        public AutoScroller() {
            mScroller = new Scroller(getContext(), new LinearInterpolator());
            mIsFinished = true;
        }

        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {//还未完成滚动
                //进行相关的滚动
                moveDown(mLastY - mScroller.getCurrY(), false);
                mLastY = mScroller.getCurrY();
                post(this);//继续下次滚动 判断是否完成滚动
            } else {
                removeCallbacks(this);
                mIsFinished = true;
            }
        }

        /**
         * 触发滚动的方法
         *
         * @param dis 滚动的距离
         */
        public void recover(int dis) {
            if (dis < 0) {
                return;
            }
            //新一轮的滚动 先移除任务
            removeCallbacks(this);
            mLastY = 0;
            mIsFinished = false;
            mScroller.startScroll(0, 0, 0, dis, 300);
            post(this);
        }

        /**
         * 滚动是否完成
         *
         * @return
         */
        public boolean isFinished() {
            return mIsFinished;
        }
    }
}
