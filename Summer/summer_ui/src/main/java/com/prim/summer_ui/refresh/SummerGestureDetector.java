package com.prim.summer_ui.refresh;


import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;

/**
 * @author prim
 * @version 1.0.0
 * @desc 手势监听的代理类
 * @time 3/29/21 - 4:42 PM
 * @contact https://jakeprim.cn
 * @name Summer
 */
public class SummerGestureDetector implements OnGestureListener {
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
