package com.prim.summer_ui.refresh;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 3/29/21 - 5:16 PM
 * @contact https://jakeprim.cn
 * @name Summer
 */
public class SummerScrollUtils {
    /**
     * 获取可滚动的视图 尽可能的找到，如果找不到返回下标为1的元素view
     * 最多允许包两层
     */
    public static View findScrollableChild(ViewGroup viewGroup) {
        View child = viewGroup.getChildAt(1);
        if (child instanceof RecyclerView || child instanceof AdapterView) {
            return child;
        }
        if (child instanceof ViewGroup) {
            //最大往下多找一层
            View tempChild = ((ViewGroup) child).getChildAt(0);
            if (tempChild instanceof RecyclerView || tempChild instanceof AdapterView) {
                child = tempChild;
            }
        }
        return child;
    }

    /**
     * 判断列表是否发生了滚动
     *
     * @return true:表示发生了滚动
     */
    public static boolean childScrolled(View child) {
        if (child instanceof AdapterView) {
            AdapterView adapterView = (AdapterView) child;
            if (adapterView.getFirstVisiblePosition() != 0
                    || adapterView.getFirstVisiblePosition() == 0 && adapterView.getChildAt(0) != null
                    && adapterView.getChildAt(0).getTop() < 0) {
                //列表的第一项以不显示在屏幕内 表示列表发生了滚动 或者 第一个还显示在屏幕内，但是滚动了一段距离
                return true;
            }
        } else if (child.getScrollY() > 0) {
            return true;
        }
        if (child instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) child;
            View view = recyclerView.getChildAt(0);
            int firstPosition = recyclerView.getChildAdapterPosition(view);
            //如果现实的不是第一个说明滚动 或者第一个view距离顶部不是0说明滚动了
            return firstPosition != 0 || view.getTop() != 0;
        }
        return false;
    }
}
