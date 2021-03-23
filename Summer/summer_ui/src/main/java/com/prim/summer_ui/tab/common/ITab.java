package com.prim.summer_ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * @author prim
 * @version 1.0.0
 * @desc Tab接口 每个tab必须实现的接口
 * TODO 双击等事件
 * @time 3/22/21 - 1:36 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public interface ITab<D> extends ITabLayout.OnTabSelectedListener<D> {
    /**
     * 设置tab的数据
     *
     * @param data
     */
    void setTabInfo(@NonNull D data);

    /**
     * 动态修改某个item的大小
     *
     * @param height
     */
    void resetHeight(@Px int height);
}
