package com.prim.honorkings.logic;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.prim.honorkings.R;
import com.prim.honorkings.fragment.CategoryPageFragment;
import com.prim.honorkings.fragment.FavoriteFragment;
import com.prim.honorkings.fragment.HomePageFragment;
import com.prim.honorkings.fragment.ProfilePageFragment;
import com.prim.honorkings.fragment.RecommendFragment;
import com.prim.summer_common.tab.SummerFragmentTabView;
import com.prim.summer_common.tab.SummerTabViewAdapter;
import com.prim.summer_ui.tab.bottom.SummerTabBottomLayout;
import com.prim.summer_ui.tab.bottom.TabBottomInfo;
import com.prim.summer_ui.tab.common.ITabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc MainActivity的逻辑辅助类
 * @time 3/23/21 - 2:26 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class MainActivityLogic {
    private SummerFragmentTabView fragmentTabView;
    private SummerTabBottomLayout tabBottomLayout;
    private List<TabBottomInfo<?>> tabInfoList = new ArrayList<>();
    private ActivityProvider activityProvider;
    private final static String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";
    private int currentItemIndex;

    public MainActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        //savedInstanceState 获取保留的值 fix不保留活动导致的Fragment重叠问题
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID);
        }
        this.activityProvider = activityProvider;
        initTabBottom();
    }

    public SummerFragmentTabView getFragmentTabView() {
        return fragmentTabView;
    }

    public SummerTabBottomLayout getTabBottomLayout() {
        return tabBottomLayout;
    }

    public List<TabBottomInfo<?>> getTabInfoList() {
        return tabInfoList;
    }

    public int getCurrentItemIndex() {
        return currentItemIndex;
    }

    private void initTabBottom() {
        tabBottomLayout = activityProvider.findViewById(R.id.tab_bottom);

        int defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefault);
        int tintColor = activityProvider.getResources().getColor(R.color.tabBottomTint);

        //首页Tab
        TabBottomInfo<Integer> homeInfo = new TabBottomInfo(
                "首页",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.icon_home),
                null,
                defaultColor,
                tintColor
        );
        homeInfo.fragment = HomePageFragment.class;

        //收藏tab
        TabBottomInfo<Integer> favoriteInfo = new TabBottomInfo(
                "收藏",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_favorite),
                null,
                defaultColor,
                tintColor
        );
        favoriteInfo.fragment = FavoriteFragment.class;

        //分类tab
        TabBottomInfo<Integer> categoryInfo = new TabBottomInfo(
                "分类",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_category),
                null,
                defaultColor,
                tintColor
        );
        categoryInfo.fragment = CategoryPageFragment.class;

        //推荐tab
        TabBottomInfo<Integer> recommendInfo = new TabBottomInfo(
                "推荐",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_recommend),
                null,
                defaultColor,
                tintColor
        );
        recommendInfo.fragment = RecommendFragment.class;

        //个人tab
        TabBottomInfo<Integer> profileInfo = new TabBottomInfo(
                "个人",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_profile),
                null,
                defaultColor,
                tintColor
        );
        profileInfo.fragment = ProfilePageFragment.class;

        tabInfoList.add(homeInfo);
        tabInfoList.add(favoriteInfo);
        tabInfoList.add(recommendInfo);
        tabInfoList.add(categoryInfo);
        tabInfoList.add(profileInfo);
        //初始化底部tab
        tabBottomLayout.inflateInfo(tabInfoList);
        initFragmentTabView();
        //监听底部tab的点击事件
        tabBottomLayout.addTabSelectedChangeListener(new ITabLayout.OnTabSelectedListener<TabBottomInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, TabBottomInfo<?> prevInfo, TabBottomInfo<?> nextInfo) {
                fragmentTabView.setCurrentItem(index);
                MainActivityLogic.this.currentItemIndex = index;
            }
        });

        //fix-恢复之前保留的状态
        tabBottomLayout.defaultSelected(tabInfoList.get(currentItemIndex));
    }

    private void initFragmentTabView() {
        SummerTabViewAdapter adapter = new SummerTabViewAdapter(activityProvider.getSupportFragmentManager(), tabInfoList);
        fragmentTabView = activityProvider.findViewById(R.id.tab_view);
        fragmentTabView.setAdapter(adapter);
    }

    public void onSaveInstanceState(Bundle outState) {
        //保存当前页面的状态
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex);
    }

    /**
     * 获取activity提供的方法,有Android系统提供的方法已经被实现了
     */
    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }
}
