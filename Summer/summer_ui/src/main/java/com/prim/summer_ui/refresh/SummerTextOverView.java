package com.prim.summer_ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prim.summer_ui.R;

/**
 * @author prim
 * @version 1.0.0
 * @desc overview 下拉刷新的头部
 * @time 3/31/21 - 4:39 PM
 * @contact https://jakeprim.cn
 * @name Summer
 */
public class SummerTextOverView extends SummerOverView {

    private ImageView iv_rotate;

    private TextView mText;

    public SummerTextOverView(@NonNull Context context) {
        this(context, null);
    }

    public SummerTextOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummerTextOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.summer_test_over_view, this, true);
        iv_rotate = findViewById(R.id.iv_rotate);
        mText = findViewById(R.id.tv_text);
    }

    @Override
    protected void onScroll(int scrollY, int pullRefreshHeight) {

    }

    @Override
    protected void onVisible() {
        mText.setText("下拉刷新");
    }

    @Override
    protected void onOver() {
        mText.setText("松开刷新");
    }

    @Override
    protected void onRefresh() {
        mText.setText("正在刷新");
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_anim);
        //线性加速器
        LinearInterpolator interpolator = new LinearInterpolator();
        animation.setInterpolator(interpolator);
        iv_rotate.startAnimation(animation);
    }

    @Override
    protected void onFinish() {
        iv_rotate.clearAnimation();
    }
}
