package picker.prim.com.primpicker_core.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lxy on 2018/6/5.
 */
public class ScrollViewPager extends ViewPager{
    
    private boolean isScrollEnable = true;
    
    public ScrollViewPager(Context context) {
        super(context);
    }

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void setScrollEnable(boolean isScrollEnable){
        this.isScrollEnable = isScrollEnable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScrollEnable && super.onTouchEvent(ev);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isScrollEnable){
            super.scrollTo(x, y);
        }
    }
}
