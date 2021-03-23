package com.prim.base_lib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Process;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class HiViewUtil {
    /**
     * 获取指定类型的子View
     *
     * @param group viewGroup
     * @param cls   如：RecyclerView.class
     * @param <T>
     * @return 指定类型的View
     */
    public static <T> T findTypeView(@Nullable ViewGroup group, Class<T> cls) {
        if (group == null) {
            return null;
        }
        Deque<View> deque = new ArrayDeque<>();
        deque.add(group);
        while (!deque.isEmpty()) {
            View node = deque.removeFirst();
            if (cls.isInstance(node)) {
                return cls.cast(node);
            } else if (node instanceof ViewGroup) {
                ViewGroup container = (ViewGroup) node;
                for (int i = 0, count = container.getChildCount(); i < count; i++) {
                    deque.add(container.getChildAt(i));
                }
            }
        }
        return null;
    }


    public static boolean isActivityDestroyed(Context context) {
        Activity activity = findActivity(context);
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return activity.isDestroyed() || activity.isFinishing();
            }

            return activity.isFinishing();
        }

        return true;
    }

    public static Activity findActivity(Context context) {
        //怎么判断context 是不是activity 类型的
        if (context instanceof Activity) return (Activity) context;
        else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }


    //检测是否是浅色主题
//    public static boolean lightMode() {
//        int mode = AppGlobals.INSTANCE.get().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//        return mode == Configuration.UI_MODE_NIGHT_NO;
//    }


    public static boolean inMainProcess(Application application) {
        int myPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : runningAppProcesses) {
            if (process.processName.equals(application.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
