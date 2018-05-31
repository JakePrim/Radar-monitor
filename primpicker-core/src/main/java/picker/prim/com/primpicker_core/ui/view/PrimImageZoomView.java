package picker.prim.com.primpicker_core.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/29 0029
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PrimImageZoomView extends android.support.v7.widget.AppCompatImageView implements
        View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = "PrimImageZoomView";

    private ScaleGestureDetector mScaleGestureDetector;

    /** 建立一个矩阵 主要进行缩放和移动 */
    private Matrix matrix = new Matrix();

    /** 用于存放矩阵的9个值 */
    private final float[] matrixValues = new float[9];

    /** 初始缩放比例 */
    private float initScale = 1.0f;

    /** 最大放大倍数 */
    private static final float SCALE_MAX = 5.0f;

    /** 最小的缩放倍数 */
    private static final float SCALE_MIN = 2.0f;

    private double mThouchSlop;
    private boolean isCheckBottomAndTop;
    private boolean isCheckLeftAndRight;
    private GestureDetector mGestureDetector;

    /** 判断是否正在自动缩放 */
    private boolean isAutoScale;

    public PrimImageZoomView(Context context) {
        this(context, null);
    }

    public PrimImageZoomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrimImageZoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /** 设置缩放类型 */
        this.setScaleType(ScaleType.MATRIX);
        /** 检测手势缩小 放大 */
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        /** 检测双击事件 */
        mGestureDetector = new GestureDetector(context, simpleOnGestureListener);
        /** 屏幕触摸监听 */
        setOnTouchListener(this);
        /** getScaledTouchSlop 可以表示为滑动的最小距离 手指移动大于这个距离才可以移动 */
        mThouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (isAutoScale) {
                return true;
            }

            float x = e.getX();
            float y = e.getY();
            if (getScale() < SCALE_MIN) {
                isAutoScale = true;
                PrimImageZoomView.this.postDelayed(new AutoScaleRunable(SCALE_MIN, x, y), 16);
            } else if (getScale() >= SCALE_MIN && getScale() < SCALE_MAX) {
                isAutoScale = true;
                PrimImageZoomView.this.postDelayed(new AutoScaleRunable(SCALE_MAX, x, y), 16);
            } else {
                isAutoScale = true;
                PrimImageZoomView.this.postDelayed(new AutoScaleRunable(initScale, x, y), 16);
            }
            return true;
        }
    };

    private class AutoScaleRunable implements Runnable {

        private float trageScale, tempScale;
        static final float BIGGER = 1.07f;
        static final float SMALLER = 0.93f;
        private float x, y;

        public AutoScaleRunable(float trageScale, float x, float y) {
            this.trageScale = trageScale;
            this.x = x;
            this.y = y;
            if (getScale() < trageScale) {
                tempScale = BIGGER;
            } else {
                tempScale = SMALLER;
            }
        }

        @Override
        public void run() {
            matrix.postScale(tempScale, tempScale, x, y);
            checkCenterScale();
            setImageMatrix(matrix);

            float scale = getScale();
            //如果值在合法范围内，继续缩放
            if (((tempScale > 1f) && (scale < trageScale))
                    || ((tempScale < 1f) && (trageScale < scale))) {
                PrimImageZoomView.this.postDelayed(this, 16);
            } else {
                final float deltaScale = trageScale / scale;
                matrix.postScale(deltaScale, deltaScale, x, y);
                checkCenterScale();
                setImageMatrix(matrix);
                isAutoScale = false;
            }
        }
    }

    private int lastPaintCount;

    private float lastX, lastY;

    private boolean isCanDrg;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);
        float x = 0, y = 0;
        //拿到触摸点的个数
        int pointerCount = event.getPointerCount();
        //得到多个触摸点的均值
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x = x / pointerCount;
        y = y / pointerCount;
        //每当触摸点发生变化时，改变lastx lasty
        if (pointerCount != lastPaintCount) {
            isCanDrg = false;
            lastX = x;
            lastY = y;
        }

        lastPaintCount = pointerCount;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getMatrixRectF().width() > getWidth() || getMatrixRectF().height() > getHeight()) {
                    //解决滑动冲突
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (getMatrixRectF().width() > getWidth() || getMatrixRectF().height() > getHeight()) {
                    //解决滑动冲突
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                float dx = x - lastX;
                float dy = y - lastY;
                if (!isCanDrg) {
                    isCanDrg = isCanDrg(dx, dy);
                }
                if (isCanDrg) {
                    // 获取缩放图片的 矩形 宽和高
                    RectF matrixRectF = getMatrixRectF();
                    if (getDrawable() != null) {
                        if (matrixRectF.left == 0 && dx > 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        if (matrixRectF.right == getWidth() && dx < 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        isCheckLeftAndRight = isCheckBottomAndTop = true;
                        //宽度小于屏幕宽度 禁止左右移动
                        if (matrixRectF.width() < getWidth()) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }

                        //图片高度小于屏幕高度 禁止上下滑动
                        if (matrixRectF.height() < getHeight()) {
                            dy = 0;
                            isCheckBottomAndTop = false;
                        }
                        matrix.postTranslate(dx, dy);
                        //检查边界
                        checkMatrixBounds();
                        setImageMatrix(matrix);
                    }
                }
                lastY = y;
                lastX = x;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                lastPaintCount = 0;
                break;
        }
        return true;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        postDelayed(new AutoScaleRunable(initScale, getHeight() / 2, getWidth() / 2), 16);
    }

    private void checkMatrixBounds() {
        RectF matrixRectF = getMatrixRectF();

        int width = getWidth();
        int height = getHeight();
        float delx = 0, dely = 0;
        //判断移动或缩放后，图片是否超出屏幕边界
        if (matrixRectF.top > 0 && isCheckBottomAndTop) {
            dely = -matrixRectF.top;
        }

        if (matrixRectF.bottom < height && isCheckBottomAndTop) {
            dely = height - matrixRectF.bottom;
        }

        if (matrixRectF.left > 0 && isCheckLeftAndRight) {
            delx = -matrixRectF.left;
        }

        if (matrixRectF.right < width && isCheckBottomAndTop) {
            delx = width - matrixRectF.right;
        }

        matrix.postTranslate(delx, dely);
    }

    private boolean isCanDrg(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mThouchSlop;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();//返回前一个伸缩事件至当前伸缩事件的伸缩比率  (getCurrentSpan()  / getPreviousSpan())
        Log.e(TAG, "onScale: scale --> " + scale);
        Log.e(TAG, "onScale: scaleFactor --> " + scaleFactor);
        if (getDrawable() == null) return true;

        /** 缩放范围的控制 在最大 SCALE_MAX 和 最小的缩放范围内进行缩放*/
        if ((scale < SCALE_MAX && scaleFactor > 1.0f) ||
                (scale > initScale && scaleFactor < 1.0f)) {
            /** 最小值判断 */
            if (scaleFactor * scale < initScale) {
                scaleFactor = initScale / scale;
            }

            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }

            /** 设置缩放比例 */
            matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkCenterScale();
            setImageMatrix(matrix);
        }

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 获取当前缩放比例
     * @return 当前的缩放比例
     */
    public float getScale() {
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    public void checkCenterScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        // 如果宽或高大于屏幕，则控制范围
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }
        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rectF.width() < width) {
            deltaX = width * 0.5f - rectF.right + 0.5f * rectF.width();
        }
        if (rectF.height() < height) {
            deltaY = height * 0.5f - rectF.bottom + 0.5f * rectF.height();
        }
        this.matrix.postTranslate(deltaX, deltaY);
    }

    @NonNull
    private RectF getMatrixRectF() {
        Matrix matrix = this.matrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    private boolean once = true;

    /** 布局完成获取图片的尺寸 */
    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }

            int width = getWidth();
            int height = getHeight();
            //拿到图片的宽和高
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            float scale = 1.0f;
            //如果图片的宽或高大于屏幕，则缩放至屏幕的宽和高
            if (intrinsicHeight <= height && intrinsicWidth > width) {
                scale = width * 1.0f / intrinsicWidth;
            }

            if (intrinsicHeight > height && intrinsicWidth <= width) {
                scale = height * 1.0f / intrinsicHeight;
            }

            if (intrinsicHeight > height && intrinsicWidth > width) {
                scale = Math.min(intrinsicWidth * 1.0f / width, intrinsicHeight * 1.0f / height);
            }

            initScale = scale;

            //将图片移动到屏幕中心
            matrix.postTranslate((width - intrinsicWidth) / 2, (height - intrinsicHeight) / 2);
            //设置缩放
            matrix.postScale(scale, scale, width / 2, height / 2);

            setImageMatrix(matrix);

            once = false;
        }
    }
}
