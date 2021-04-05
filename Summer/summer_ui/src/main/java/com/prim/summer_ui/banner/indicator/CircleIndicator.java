package com.prim.summer_ui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prim.base_lib.utils.DisplayUtils;
import com.prim.summer_ui.R;

/**
 * @author sufulu
 * @version 1.0.0
 * @desc 圆形指示器
 * @time 4/3/21 - 9:39 PM
 * @contact sufululove@gmail.com
 * @name Summer
 */
public class CircleIndicator extends FrameLayout implements BannerIndicator<FrameLayout> {

    private static final int VWC = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 正常下的指示点
     *
     * @param context
     */
    private @DrawableRes
    int mPointNormal = R.drawable.shape_point_normal;

    /**
     * 选中状态的指示点
     */
    private @DrawableRes
    int mPointSelect = R.drawable.shape_point_select;

    /**
     * 指示点的左右内间距
     */
    private int mPointLeftRightPadding;

    /**
     * 指示点的上下内间距
     */
    private int mPointTopBottomPadding;

    public CircleIndicator(@NonNull Context context) {
        this(context, null);
    }

    public CircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    /**
     * 初始化几个指示点
     *
     * @param count 幻灯片的数量
     */
    @Override
    public void onInflate(int count) {
        removeAllViews();
        if (count == 0) return;
        LinearLayout groupView = new LinearLayout(getContext());
        groupView.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(VWC, VWC);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(mPointLeftRightPadding, mPointTopBottomPadding, mPointLeftRightPadding, mPointTopBottomPadding);
        for (int i = 0; i < count; i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(params);
            //默认第一个选中
            if (i == 0) {
                imageView.setImageResource(mPointSelect);
            } else {
                imageView.setImageResource(mPointNormal);
            }
            groupView.addView(imageView);
        }
        LayoutParams layoutParams = new LayoutParams(VWC, VWC);
        layoutParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
        addView(groupView, layoutParams);
    }

    @Override
    public void onPointChange(int current, int count) {
        //获取到groupView
        ViewGroup group = (ViewGroup) getChildAt(0);
        //获取子view
        for (int i = 0; i < group.getChildCount(); i++) {
            ImageView imageView = (ImageView) group.getChildAt(i);
            if (i == current) {
                //选中的指示点
                imageView.setImageResource(mPointSelect);
            } else {
                imageView.setImageResource(mPointNormal);
            }
            imageView.requestLayout();
        }
    }
}
