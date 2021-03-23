package com.prim.summer_ui.tab.common;

import androidx.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc TabLayout接口
 * @time 3/22/21 - 1:31 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public interface ITabLayout<Tab extends ViewGroup, D> {
    /**
     * 根据data去查找tabView
     *
     * @param data
     * @return
     */
    Tab findTab(@NonNull D data);

    /**
     * 监听Tab的选中
     *
     * @param listener
     */
    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    /**
     * 默认选中的tab
     *
     * @param defaultInfo
     */
    void defaultSelected(@NonNull D defaultInfo);

    /**
     * 对数据相关的初始化
     *
     * @param infoList
     */
    void inflateInfo(List<D> infoList);

    interface OnTabSelectedListener<D> {
        void onTabSelectedChange(int index, D prevInfo, D nextInfo);
    }
}
