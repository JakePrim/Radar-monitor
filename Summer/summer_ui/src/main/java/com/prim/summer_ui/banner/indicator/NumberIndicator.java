package com.prim.summer_ui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prim.base_lib.utils.DisplayUtils;
import com.prim.summer_ui.R;

/**
 * @author sufulu
 * @version 1.0.0
 * @desc 数字指示器
 * @time 4/5/21 - 11:03 PM
 * @contact sufululove@gmail.com
 * @name Summer
 */
public class NumberIndicator extends FrameLayout implements BannerIndicator<FrameLayout> {
    private static final int VWC = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 指示点的左右内间距
     */
    private int mPointLeftRightPadding;

    /**
     * 指示点的上下内间距
     */
    private int mPointTopBottomPadding;

    public NumberIndicator(@NonNull Context context) {
        this(context, null);
    }

    public NumberIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPointLeftRightPadding = DisplayUtils.dp2px(5, getContext().getResources());
        mPointTopBottomPadding = DisplayUtils.dp2px(15, getContext().getResources());

    }

    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int count) {
        //初始化指示器
        removeAllViews();
        if (count == 0) return;
        LinearLayout groupView = new LinearLayout(getContext());
        groupView.setBackgroundColor(getContext().getResources().getColor(R.color.color_565));
        groupView.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(VWC, VWC);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(mPointLeftRightPadding, mPointTopBottomPadding, mPointLeftRightPadding, mPointTopBottomPadding);
        for (int i = 0; i < 2; i++) {
            textView = new TextView(getContext());
            textView.setTextColor(getContext().getResources().getColor(R.color.color_white));
            textView.setLayoutParams(params);
            //默认第一个选中
            if (i == 0) {
                textView.setText("1");
            } else {
                textView.setText("/" + count + "");
            }
            groupView.addView(textView);
        }
        LayoutParams layoutParams = new LayoutParams(VWC, VWC);
        layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        addView(groupView, layoutParams);
    }

    @Override
    public void onPointChange(int current, int count) {
        //获取到groupView
        ViewGroup group = (ViewGroup) getChildAt(0);
        //获取子view
        TextView textView = (TextView) group.getChildAt(0);
        if (textView != null) {
            textView.setText((current + 1) + "");
        }
    }
}
