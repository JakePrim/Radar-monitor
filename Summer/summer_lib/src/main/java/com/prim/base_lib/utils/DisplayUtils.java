package com.prim.base_lib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import androidx.annotation.NonNull;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 3/22/21 - 11:24 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class DisplayUtils {
    public static int dp2px(float dp, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

//    public static int dp2px(float dp) {
//        Resources resources = AppGlobals.INSTANCE.get().getResources();
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
//    }
//
//    public static int sp2px(float dp) {
//        Resources resources = AppGlobals.INSTANCE.get().getResources();
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, resources.getDisplayMetrics());
//    }


    public static int getDisplayWidthInPx(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return 0;

    }

    public static int getDisplayHeightInPx(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.y;
        }
        return 0;
    }

//    public static int getStatusBarDimensionPx() {
//        int statusBarHeight = 0;
//        Resources res = AppGlobals.INSTANCE.get().getResources();
//        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            statusBarHeight = res.getDimensionPixelSize(resourceId);
//        }
//        return statusBarHeight;
//    }
}
