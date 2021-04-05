package com.prim.summer_ui.banner.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc viewpager的adapter
 * @time 4/3/21 - 8:39 PM
 * @contact https://jakeprim.cn
 * @name Summer
 */
public class BannerAdapter extends PagerAdapter {
    private Context mContext;

    //缓存的viewholder
    private SparseArray<BannerViewHolder> mCachedViews = new SparseArray<>();

    private IBanner.OnBannerClickListener bannerClickListener;

    private IBindAdapter bindAdapter;

    private List<? extends BannerMo> bannerMos;

    /**
     * 自动播放
     */
    private boolean mAutoPlay = true;

    /**
     * 非自动播放的情况下，是否可以循环切换
     */
    private boolean mLoop = false;

    private int mLayoutResId = -1;

    public BannerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 设置banner的数据
     *
     * @param bannerMoList
     */
    public void setBannerData(List<? extends BannerMo> bannerMoList) {
        this.bannerMos = bannerMoList;
        //初始化数据
        initCachedView();
        notifyDataSetChanged();
    }

    public void setBannerClickListener(IBanner.OnBannerClickListener bannerClickListener) {
        this.bannerClickListener = bannerClickListener;
    }

    public void setBindAdapter(IBindAdapter bindAdapter) {
        this.bindAdapter = bindAdapter;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }

    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    public void setLayoutResId(int layoutResId) {
        this.mLayoutResId = layoutResId;
    }

    @Override
    public int getCount() {
        //无限轮播
        return mAutoPlay ? Integer.MAX_VALUE : (mLoop ? Integer.MAX_VALUE : getRealCount());
    }

    public int getRealCount() {
        return bannerMos == null ? 0 : bannerMos.size();
    }

    /**
     * 获取初次展示的item的位置
     */
    public int getFirstItem() {
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * 实例化item的方法
     *
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (getRealCount() > 0) {
            realPosition = position % getRealCount();
        }
        //获取view holder
        BannerViewHolder viewHolder = mCachedViews.get(realPosition);
        //如果container已经添加过viewHolder则移除掉
        if (container.equals(viewHolder.rootView.getParent())) {
            container.removeView(viewHolder.rootView);
        }

        //数据绑定
        onBind(viewHolder, bannerMos.get(realPosition), realPosition);

        //做异常处理 防止viewHolder的parent是其他的viewGroup
        if (viewHolder.rootView.getParent() != null) {
            ((ViewGroup) viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }
        //添加viewHolder
        container.addView(viewHolder.rootView);
        return viewHolder.rootView;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //让item每次都会刷新
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //不让销毁缓存的视图view
    }

    protected void onBind(BannerViewHolder bannerViewHolder, BannerMo bannerMo, int position) {
        bannerViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bannerClickListener != null) {
                    bannerClickListener.onBannerClick(bannerViewHolder, bannerMo, position);
                }
            }
        });
        if (bindAdapter != null) {
            //调用onBind让业务层实现绑定
            bindAdapter.onBind(bannerViewHolder, bannerMo, position);
        }
    }

    /**
     * 初始化view holder
     */
    private void initCachedView() {
        mCachedViews = new SparseArray<>();
        for (int i = 0; i < bannerMos.size(); i++) {
            BannerViewHolder viewHolder = new BannerViewHolder(createView(LayoutInflater.from(mContext), null));
            mCachedViews.put(i, viewHolder);
        }
    }

    private View createView(LayoutInflater layoutInflater, ViewGroup parent) {
        if (mLayoutResId == -1) {
            throw new IllegalArgumentException("you must set layoutResId");
        }
        return layoutInflater.inflate(mLayoutResId, parent, false);
    }


    public static class BannerViewHolder {

        private SparseArray<View> viewSparseArray;

        View rootView;

        public BannerViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public View getRootView() {
            return rootView;
        }

        /**
         * 提供使用方调用
         *
         * @param id
         * @param <V>
         * @return
         */
        public <V extends View> V findViewById(int id) {
            if (!(rootView instanceof ViewGroup)) {
                return (V) rootView;
            }
            if (viewSparseArray == null) {
                viewSparseArray = new SparseArray<>(1);
            }
            V childView = (V) viewSparseArray.get(id);
            if (childView == null) {
                childView = rootView.findViewById(id);
                viewSparseArray.put(id, childView);
            }
            return childView;
        }
    }
}
