package com.prim.summer_ui.banner.core;

import android.view.WindowAnimationFrameStats;

import androidx.annotation.LayoutRes;
import androidx.viewpager.widget.ViewPager;

import com.prim.summer_ui.banner.indicator.BannerIndicator;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 4/3/21 - 8:27 PM
 * @contact https://jakeprim.cn
 * @name Summer
 */
public interface IBanner {
    void setBannerData(@LayoutRes int layoutResId, List<? extends BannerMo> bannerMos);

    void setBannerData(List<? extends BannerMo> bannerMos);

    void setIndicator(BannerIndicator<?> indicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBannerAdapter(IBindAdapter adapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener bannerClickListener);

    interface OnBannerClickListener {
        void onBannerClick(BannerAdapter.BannerViewHolder viewHolder, BannerMo bannerMo, int position);
    }

    void setScrollDuration(int duration);

}
