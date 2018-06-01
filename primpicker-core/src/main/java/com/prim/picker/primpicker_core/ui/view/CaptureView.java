package com.prim.picker.primpicker_core.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.prim.picker.primpicker_core.R;
import com.prim.picker.primpicker_core.entity.SelectSpec;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/28 0028
 * 描    述：拍照view
 * 修订历史：
 * ================================================
 */
public class CaptureView extends FrameLayout {
    private TextView tv_item_capture;

    public CaptureView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CaptureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CaptureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lib_item_capture_layout, this, true);
        tv_item_capture = (TextView) findViewById(R.id.tv_item_capture);
        if (SelectSpec.getInstance().onlyShowVideos()) {
            tv_item_capture.setText(context.getResources().getString(R.string.str_capture_video_text));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
