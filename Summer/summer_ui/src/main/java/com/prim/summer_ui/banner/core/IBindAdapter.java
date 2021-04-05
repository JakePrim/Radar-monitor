package com.prim.summer_ui.banner.core;

/**
 * @author prim
 * @version 1.0.0
 * @desc banner的数据绑定接口，基于该接口可以实现数据绑定和框架层的解耦
 * @time 4/3/21 - 8:37 PM
 * @contact https://jakeprim.cn
 * @name Summer
 */
public interface IBindAdapter {
    void onBind(BannerAdapter.BannerViewHolder viewHolder,BannerMo bannerMo,int position);
}
