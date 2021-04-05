package com.prim.summer_ui.banner.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.prim.summer_ui.R;
import com.prim.summer_ui.banner.indicator.BannerIndicator;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc banner的具体实现
 * @time 4/3/21 - 9:17 PM
 * @contact https://jakeprim.cn
 * @name Summer
 */
public class SummerBanner extends FrameLayout implements IBanner {

    private SummerBannerDelegate delegate;

    public SummerBanner(@NonNull Context context) {
        this(context, null);
    }

    public SummerBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummerBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new SummerBannerDelegate(context, this);
        initCustomAttrs(context, attrs);
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SummerBanner);
        boolean autoPlay = typedArray.getBoolean(R.styleable.SummerBanner_autoPlay, true);
        boolean loop = typedArray.getBoolean(R.styleable.SummerBanner_loop, true);
        int intervalTime = typedArray.getInteger(R.styleable.SummerBanner_intervalTime, -1);
        setAutoPlay(autoPlay);
        setLoop(loop);
        setIntervalTime(intervalTime);
        typedArray.recycle();
    }

    @Override
    public void setBannerData(int layoutResId, List<? extends BannerMo> bannerMos) {
        delegate.setBannerData(layoutResId, bannerMos);
    }

    @Override
    public void setBannerData(List<? extends BannerMo> bannerMos) {
        delegate.setBannerData(bannerMos);
    }

    @Override
    public void setIndicator(BannerIndicator<?> indicator) {
        delegate.setIndicator(indicator);
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        delegate.setAutoPlay(autoPlay);
    }

    @Override
    public void setLoop(boolean loop) {
        delegate.setLoop(loop);
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        delegate.setIntervalTime(intervalTime);
    }

    @Override
    public void setBannerAdapter(IBindAdapter adapter) {
        delegate.setBannerAdapter(adapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        delegate.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener bannerClickListener) {
        delegate.setOnBannerClickListener(bannerClickListener);
    }

    @Override
    public void setScrollDuration(int duration) {
        delegate.setScrollDuration(duration);
    }
}
