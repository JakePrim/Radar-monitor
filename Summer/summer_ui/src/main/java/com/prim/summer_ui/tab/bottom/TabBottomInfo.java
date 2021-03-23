package com.prim.summer_ui.tab.bottom;

import android.graphics.Bitmap;
import androidx.fragment.app.Fragment;

/**
 * @author prim
 * @version 1.0.0
 * @desc 底部每个tab的数据信息
 * @time 3/22/21 - 1:39 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class TabBottomInfo<Color> {
    public enum TabType {
        BITMAP, ICON
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
    public String iconFont;
    /**
     * 在java代码中设置iconfont字符串无效，需要定义在string.xml
     */
    public String defaultIconName;
    public String selectedIconName;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public TabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public TabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.BITMAP;
    }

    public TabBottomInfo(String name, String iconFont, String defaultIconName, String selectedIconName, Color defaultColor, Color tintColor) {
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectedIconName = selectedIconName;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.ICON;
    }
}
