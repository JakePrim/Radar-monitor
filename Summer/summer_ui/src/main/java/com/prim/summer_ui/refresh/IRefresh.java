package com.prim.summer_ui.refresh;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 3/26/21 - 3:36 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public interface IRefresh {
    /**
     * 刷新的时候是否禁止滚动
     *
     * @param disableRefreshScroll
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 刷新完成
     */
    void refreshFinished();

    /**
     * 设置下拉刷新监听
     * @param summerRefreshListener
     */
    void setRefreshListener(SummerRefreshListener summerRefreshListener);

    /**
     * 设置下拉刷新的视图
     */
    void setRefreshOverView(SummerOverView overView);

    /**
     * 下来刷新的监听器
     */
    interface SummerRefreshListener {
        void onRefresh();

        boolean enableRefresh();//是否开启下来刷新
    }
}
