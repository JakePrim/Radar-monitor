package com.prim.base_lib.log;

import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.prim.base_lib.utils.DisplayUtils;

/**
 * @author prim
 * @version 1.0.0
 * @desc 日志视图的控制
 * @time 3/22/21 - 11:12 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class ViewPrinterProvider {
    private FrameLayout rootView;
    private View floatingView;
    private boolean isOpen;
    private FrameLayout logView;
    private RecyclerView recyclerView;


    public ViewPrinterProvider(FrameLayout rootView, RecyclerView recyclerView) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
    }

    //下面两个字段作为 view创建的标志防止重复创建
    private static final String TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW";
    private static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";

    /**
     * 显示悬浮view
     */
    public void showFloatingView() {
        if (rootView.findViewWithTag(TAG_FLOATING_VIEW) != null) {
            //表示已经添加了
            return;
        }
        FrameLayout.LayoutParams params = new
                FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        View floatView = getFloatingView();
        floatView.setTag(TAG_FLOATING_VIEW);
        floatView.setBackgroundColor(Color.BLACK);
        floatView.setAlpha(0.8f);
        params.bottomMargin = DisplayUtils.dp2px(100, recyclerView.getResources());
        rootView.addView(floatView, params);
    }

    public void closeFloatingView() {
        rootView.removeView(getFloatingView());
    }

    private View getFloatingView() {
        if (floatingView != null) {
            return floatingView;
        }
        TextView textView = new TextView(rootView.getContext());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpen) {
                    //显示日志信息view
                    showLogView();
                }
            }
        });
        textView.setText("PLog");
        return floatingView = textView;
    }

    private void showLogView() {
        if (rootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            //表示界面已经添加了
            return;
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DisplayUtils.dp2px(160, rootView.getResources()));
        params.gravity = Gravity.BOTTOM;
        View logView = getLogView();
        logView.setTag(TAG_LOG_VIEW);
        rootView.addView(logView, params);
        isOpen = true;
    }

    private View getLogView() {
        if (logView != null) {
            return logView;
        }
        FrameLayout layout = new FrameLayout(rootView.getContext());
        layout.setBackgroundColor(Color.BLACK);
        layout.addView(recyclerView);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        TextView closeView = new TextView(rootView.getContext());
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeLogView();
            }
        });
        closeView.setText("Close");
        layout.addView(closeView, params);
        return logView = layout;
    }

    /**
     * 关闭日志的view
     */
    private void closeLogView() {
        isOpen = false;
        rootView.removeView(getLogView());
    }
}
