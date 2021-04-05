package com.prim.summer_ui.banner.core;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

import com.prim.summer_ui.R;
import com.prim.summer_ui.banner.indicator.BannerIndicator;
import com.prim.summer_ui.banner.indicator.CircleIndicator;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc Banner的委托类
 * 辅助SummerBanner完成各种功能的制定
 * 将SummerBanner的逻辑内置在这，是使用者SummerBanner干净整洁
 * @time 4/3/21 - 9:18 PM
 * @contact https://jakeprim.cn
 * @name Summer
 */
public class SummerBannerDelegate implements IBanner, ViewPager.OnPageChangeListener {

    private SummerBanner summerBanner;
    private Context mContext;

    private BannerAdapter bannerAdapter;
    private BannerIndicator<?> indicator;
    private boolean mAutoPlay;
    private boolean mLoop;
    private List<? extends BannerMo> bannerMos;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private int mIntervalTime = 5000;
    private IBanner.OnBannerClickListener onBannerClickListener;
    private SummerViewPage summerViewPage;

    private int mScrollDuration = -1;

    public SummerBannerDelegate(Context context, SummerBanner summerBanner) {
        this.mContext = context;
        this.summerBanner = summerBanner;
    }

    @Override
    public void setBannerData(int layoutResId, List<? extends BannerMo> bannerMos) {
        this.bannerMos = bannerMos;
        init(layoutResId);
    }

    private void init(int layoutResId) {
        if (bannerAdapter == null) {
            bannerAdapter = new BannerAdapter(mContext);
        }
        if (indicator == null) {
            indicator = new CircleIndicator(mContext);
        }
        indicator.onInflate(bannerMos.size());
        bannerAdapter.setLayoutResId(layoutResId);
        bannerAdapter.setBannerData(bannerMos);
        bannerAdapter.setAutoPlay(mAutoPlay);
        bannerAdapter.setLoop(mLoop);
        bannerAdapter.setBannerClickListener(onBannerClickListener);

        summerViewPage = new SummerViewPage(mContext);
        summerViewPage.setIntervalTime(mIntervalTime);
        summerViewPage.addOnPageChangeListener(this);
        summerViewPage.setAutoPlay(mAutoPlay);
        summerViewPage.setAdapter(bannerAdapter);
        if (mScrollDuration > 0) {
            summerViewPage.setScrollDuration(mScrollDuration);
        }

        if ((mLoop || mAutoPlay) && bannerAdapter.getRealCount() > 0) {
            int firstItem = bannerAdapter.getFirstItem();
            //第一章图片不需要有动画
            summerViewPage.setCurrentItem(firstItem, false);
        }
        //清除缓存的view
        summerBanner.removeAllViews();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        summerBanner.addView(summerViewPage, layoutParams);
        summerBanner.addView(indicator.get(), layoutParams);
    }

    @Override
    public void setBannerData(List<? extends BannerMo> bannerMos) {
        setBannerData(R.layout.banner_default_item, bannerMos);
    }

    @Override
    public void setIndicator(BannerIndicator<?> indicator) {
        this.indicator = indicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (bannerAdapter != null) {
            bannerAdapter.setAutoPlay(mAutoPlay);
        }
        if (summerViewPage != null) {
            summerViewPage.setAutoPlay(mAutoPlay);
        }
    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        if (intervalTime > 0) {
            this.mIntervalTime = intervalTime;
        }
    }

    @Override
    public void setBannerAdapter(IBindAdapter adapter) {
        this.bannerAdapter.setBindAdapter(adapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener bannerClickListener) {
        this.onBannerClickListener = bannerClickListener;
    }

    @Override
    public void setScrollDuration(int duration) {
        this.mScrollDuration = duration;
        if (this.summerViewPage != null && this.mScrollDuration > 0) {
            summerViewPage.setScrollDuration(duration);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onPageChangeListener != null && bannerAdapter.getRealCount() > 0) {
            onPageChangeListener.onPageScrolled(position % bannerAdapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (bannerAdapter.getRealCount() == 0) {
            return;
        }
        position = position % bannerAdapter.getRealCount();
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(position);
        }
        if (indicator != null) {
            indicator.onPointChange(position, bannerAdapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }


}
