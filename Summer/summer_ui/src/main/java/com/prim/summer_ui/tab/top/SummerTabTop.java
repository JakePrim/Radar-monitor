package com.prim.summer_ui.tab.top;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.prim.summer_ui.R;
import com.prim.summer_ui.tab.common.ITab;

/**
 * @author prim
 * @version 1.0.0
 * @desc 通用的底部导航组件中的Tab组件
 * @time 3/22/21 - 1:47 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class SummerTabTop extends RelativeLayout implements ITab<TabTopInfo<?>> {
    private TabTopInfo<?> tabInfo;
    private ImageView tabTopImage;
    private TextView tabTopName;
    //指示器
    private View indicator;

    public SummerTabTop(Context context) {
        this(context, null);
    }

    public SummerTabTop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummerTabTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tab_top_layout, this);
        tabTopImage = view.findViewById(R.id.tab_image);
        tabTopName = view.findViewById(R.id.tv_name);
        indicator = view.findViewById(R.id.tab_top_indicator);
    }

    //TODO 动态设置指示器的颜色

    //TODO 指示器的动态样式

    @Override
    public void setTabInfo(@NonNull TabTopInfo<?> data) {
        this.tabInfo = data;
        inflateInfo(false, true);
    }

    /**
     * 标记是否选中和初始化
     *
     * @param selected
     * @param init
     */
    private void inflateInfo(boolean selected, boolean init) {
        if (tabInfo.tabType == TabTopInfo.TabType.TEXT) {
            fontIconType(selected, init);
        } else if (tabInfo.tabType == TabTopInfo.TabType.BITMAP) {
            bitmapType(selected, init);
        } else if (tabInfo.tabType == TabTopInfo.TabType.ICON) {
            iconType(selected, init);
        }
    }

    private void iconType(boolean selected, boolean init) {
        if (init) {
            tabTopImage.setVisibility(VISIBLE);
            tabTopName.setVisibility(GONE);
        }
        if (selected) {
            tabTopImage.setImageResource(tabInfo.selectedIcon);
            indicator.setVisibility(VISIBLE);
        } else {
            tabTopImage.setImageResource(tabInfo.defaultIcon);
            indicator.setVisibility(GONE);
        }
    }

    private void bitmapType(boolean selected, boolean init) {
        if (init) {
            tabTopImage.setVisibility(VISIBLE);
            tabTopName.setVisibility(GONE);
        }
        if (selected) {
            tabTopImage.setImageBitmap(tabInfo.selectedBitmap);
            indicator.setVisibility(VISIBLE);
        } else {
            tabTopImage.setImageBitmap(tabInfo.defaultBitmap);
            indicator.setVisibility(GONE);
        }
    }

    private void fontIconType(boolean selected, boolean init) {
        if (init) {
            tabTopName.setVisibility(VISIBLE);
            tabTopImage.setVisibility(GONE);
            if (!TextUtils.isEmpty(tabInfo.name)) {
                tabTopName.setText(tabInfo.name);
            }
        }
        //选中了当前的tab
        if (selected) {
            tabTopName.setTextColor(getTextColor(tabInfo.tintColor));
            indicator.setVisibility(VISIBLE);
        } else {
            tabTopName.setTextColor(getTextColor(tabInfo.defaultColor));
            indicator.setVisibility(GONE);
        }
    }

    @Override
    public void resetHeight(int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        getTabName().setVisibility(GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @NonNull TabTopInfo<?> prevInfo, @NonNull TabTopInfo<?> nextInfo) {
        //tab被选中 需要判断是否重复选中
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }
        if (prevInfo == tabInfo) {
            inflateInfo(false, false);
        } else {
            inflateInfo(true, false);
        }
    }

    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }

    public TabTopInfo<?> getTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImage() {
        return tabTopImage;
    }

    public TextView getTabName() {
        return tabTopName;
    }
}
