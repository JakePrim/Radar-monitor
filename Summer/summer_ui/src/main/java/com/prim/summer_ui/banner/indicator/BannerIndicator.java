package com.prim.summer_ui.banner.indicator;

import android.view.View;

/**
 * @author prim
 * @version 1.0.0
 * @desc banner指示器
 * @time 4/3/21 - 8:29 PM
 * @contact https://jakeprim.cn
 * @name Summer
 */
public interface BannerIndicator<T extends View> {

    /**
     * 获取指示器的view
     * @return
     */
    T get();

    /**
     * 初始化 Indicator
     * @param count 幻灯片的数量
     */
    void onInflate(int count);

    /**
     * 幻灯片的切换回调
     * @param current 要切换到幻灯片的位置
     * @param count 幻灯片的数量
     */
    void onPointChange(int current,int count);
}
