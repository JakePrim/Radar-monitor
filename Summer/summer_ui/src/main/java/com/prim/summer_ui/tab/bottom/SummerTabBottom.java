package com.prim.summer_ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class SummerTabBottom extends RelativeLayout implements ITab<TabBottomInfo<?>> {
    public SummerTabBottom(Context context) {
        this(context, null);
    }

    public SummerTabBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummerTabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private TabBottomInfo<?> tabInfo;
    private ImageView tabImage;
    private TextView tabIcon;
    private TextView tabName;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.tab_bottom, this);
        tabImage = findViewById(R.id.iv_image);
        tabIcon = findViewById(R.id.tv_icon);
        tabName = findViewById(R.id.tv_name);
    }

    @Override
    public void setTabInfo(@NonNull TabBottomInfo<?> data) {
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
        if (tabInfo.tabType == TabBottomInfo.TabType.ICON) {
            if (init) {
                tabImage.setVisibility(GONE);
                tabIcon.setVisibility(VISIBLE);
                //设置字体
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), tabInfo.iconFont);
                tabIcon.setTypeface(typeface);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabName.setText(tabInfo.name);
                }
            }
            //选中了当前的tab
            if (selected) {
                tabIcon.setText(TextUtils.isEmpty(tabInfo.selectedIconName) ? tabInfo.defaultIconName : tabInfo.selectedIconName);
                tabName.setTextColor(getTextColor(tabInfo.tintColor));
                tabIcon.setTextColor(getTextColor(tabInfo.tintColor));
            } else {
                tabIcon.setText(tabInfo.defaultIconName);
                tabName.setTextColor(getTextColor(tabInfo.defaultColor));
                tabIcon.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        } else if (tabInfo.tabType == TabBottomInfo.TabType.BITMAP) {
            if (init) {
                tabImage.setVisibility(VISIBLE);
                tabIcon.setVisibility(GONE);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabName.setText(tabInfo.name);
                }
            }
            if (selected) {
                tabImage.setImageBitmap(tabInfo.selectedBitmap);
                if (tabInfo.defaultColor != null) {
                    tabName.setTextColor(getTextColor(tabInfo.defaultColor));
                }
            } else {
                tabImage.setImageBitmap(tabInfo.defaultBitmap);
                if (tabInfo.tintColor != null) {
                    tabName.setTextColor(getTextColor(tabInfo.tintColor));
                }
            }

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
    public void onTabSelectedChange(int index, @NonNull TabBottomInfo<?> prevInfo, @NonNull TabBottomInfo<?> nextInfo) {
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

    public TabBottomInfo<?> getTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImage() {
        return tabImage;
    }

    public TextView getTabIcon() {
        return tabIcon;
    }

    public TextView getTabName() {
        return tabName;
    }
}
