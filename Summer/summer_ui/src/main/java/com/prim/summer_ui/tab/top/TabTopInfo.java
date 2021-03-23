package com.prim.summer_ui.tab.top;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

import com.prim.summer_ui.tab.bottom.TabBottomInfo;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 3/23/21 - 3:38 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class TabTopInfo<Color> {
    /**
     * 支持bitmap
     */
    public enum TabType {
        BITMAP, TEXT, ICON
    }

    /**
     * 和Tab绑定的fragment
     */
    public Class<? extends Fragment> fragment;

    /**
     * Tab的名称
     */
    public String name;

    /**
     * 默认的bitmap
     */
    public Bitmap defaultBitmap;

    /**
     * 选中的bitmap
     */
    public Bitmap selectedBitmap;

    /**
     * 本地图片
     */
    public int defaultIcon;
    public int selectedIcon;

    public Color defaultColor;
    public Color tintColor;

    public TabType tabType;

    public TabTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public TabTopInfo(String name, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.TEXT;
    }

//    public TabTopInfo(String name, int defaultIcon, int selectedIcon) {
//        this.name = name;
//        this.defaultIcon = defaultIcon;
//        this.selectedIcon = selectedIcon;
//        this.tabType = TabType.ICON;
//    }
}
