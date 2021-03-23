package com.prim.summer_common.tab;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.prim.summer_ui.tab.bottom.TabBottomInfo;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc TabView的适配器
 * @time 3/23/21 - 1:53 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class SummerTabViewAdapter {
    private List<TabBottomInfo<?>> mInfoList;
    private Fragment mCurFragment;
    private FragmentManager mFragmentManager;

    public SummerTabViewAdapter(FragmentManager mFragmentManager, List<TabBottomInfo<?>> mInfoList) {
        this.mInfoList = mInfoList;
        this.mFragmentManager = mFragmentManager;
    }

    /**
     * 实例化以及显示指定位置的fragment
     */
    public void instantiateItem(View container, int position) {
        FragmentTransaction mCurTransaction = mFragmentManager.beginTransaction();
        //判断当前的fragment不为空的情况下 则隐藏当前的fragment，打开下一个fragment需要隐藏
        if (mCurFragment != null) {
            mCurTransaction.hide(mCurFragment);
        }
        String tag = container.getId() + ":" + position;
        //获取fragment
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        //fragment已经创建过了
        if (fragment != null) {
            //显示fragment
            mCurTransaction.show(fragment);
        } else {
            //fragment没有创建过,则通过TabBottomInfo反射创建
            fragment = getItem(position);
            //添加到事务中去
            if (!fragment.isAdded()) {
                mCurTransaction.add(container.getId(), fragment, tag);
            }
        }
        mCurFragment = fragment;
        mCurTransaction.commitAllowingStateLoss();
    }

    /**
     * 创建Fragment
     *
     * @param position
     * @return
     */
    public Fragment getItem(int position) {
        try {
            Fragment fragment = mInfoList.get(position).fragment.newInstance();
            return fragment;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Fragment getCurFragment() {
        return mCurFragment;
    }

    public int getCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }
}
