package com.prim.summer_ui.banner.core;

import android.content.Context;
import android.widget.Scroller;

/**
 * @author sufulu
 * @version 1.0.0
 * @desc 控制viewpager的滚动速度
 * @time 4/5/21 - 11:51 AM
 * @contact sufululove@gmail.com
 * @name Summer
 */
public class SummerBannerScroller extends Scroller {

    /**
     * 值越大滑动越慢
     */
    private int mDuration = 1000;

    public SummerBannerScroller(Context context, int duration) {
        super(context);
        this.mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
