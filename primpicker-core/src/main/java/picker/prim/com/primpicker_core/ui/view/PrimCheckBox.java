package picker.prim.com.primpicker_core.ui.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import picker.prim.com.primpicker_core.R;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/11 0011
 * 描    述：自定义CheckBox
 * 修订历史：
 * ================================================
 */
public class PrimCheckBox extends View implements Checkable {

    private final static float BOUNCE_VALUE = 0.2f;

    private Drawable checkDrawable;

    private Paint bitmapPaint;
    private Paint bitmapEraser;
    private Paint checkEraser;
    private Paint borderPaint;

    private Bitmap drawBitmap;
    private Bitmap checkBitmap;
    private Canvas bitmapCanvas;
    private Canvas checkCanvas;

    private float progress;
    private ObjectAnimator checkAnim;

    private boolean attachedToWindow;
    private boolean isChecked;

    private int size = 22;
    private int bitmapColor = 0xFF3F51B5;
    private int borderColor = 0xFFFFFFFF;

    public PrimCheckBox(Context context) {
        this(context, null);
    }

    public PrimCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrimCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CheckBox_Prim);
        size = ta.getDimensionPixelSize(R.styleable.CheckBox_Prim_size, dp(size));
        bitmapColor = ta.getColor(R.styleable.CheckBox_Prim_color_background, bitmapColor);
        borderColor = ta.getColor(R.styleable.CheckBox_Prim_color_border, borderColor);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapEraser = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapEraser.setColor(0);
        bitmapEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        checkEraser = new Paint(Paint.ANTI_ALIAS_FLAG);
        checkEraser.setColor(0);
        checkEraser.setStyle(Paint.Style.STROKE);
        checkEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dp(2));
        checkDrawable = context.getResources().getDrawable(R.drawable.check);
        setVisibility(VISIBLE);
        ta.recycle();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE && drawBitmap == null) {
            drawBitmap = Bitmap.createBitmap(dp(size), dp(size), Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(drawBitmap);
            checkBitmap = Bitmap.createBitmap(dp(size), dp(size), Bitmap.Config.ARGB_8888);
            checkCanvas = new Canvas(checkBitmap);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.getMode(Math.min(widthMeasureSpec, heightMeasureSpec)));
        super.onMeasure(newSpec, newSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getVisibility() != VISIBLE) {
            return;
        }
        checkEraser.setStrokeWidth(size);

        drawBitmap.eraseColor(0);
        float rad = getMeasuredWidth() / 2;

        float bitmapProgress = progress >= 0.5f ? 1.0f : progress / 0.5f;
        float checkProgress = progress < 0.5f ? 0.0f : (progress - 0.5f) / 0.5f;

        float p = isChecked ? progress : (1.0f - progress);

        if (p < BOUNCE_VALUE) {
            rad -= dp(2) * p;
        } else if (p < BOUNCE_VALUE * 2) {
            rad -= dp(2) - dp(2) * p;
        }

        borderPaint.setColor(borderColor);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, rad - dp(1), borderPaint);

        bitmapPaint.setColor(bitmapColor);

        bitmapCanvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, rad, bitmapPaint);
        bitmapCanvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, rad * (1 - bitmapProgress), bitmapEraser);
        canvas.drawBitmap(drawBitmap, 0, 0, null);

        checkBitmap.eraseColor(0);
        int w = checkDrawable.getIntrinsicWidth();
        int h = checkDrawable.getIntrinsicHeight();
        int x = (getMeasuredWidth() - w) / 2;
        int y = (getMeasuredHeight() - h) / 2;

        checkDrawable.setBounds(x, y, x + w, y + h);
        checkDrawable.draw(checkCanvas);
        checkCanvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, rad * (1 - checkProgress), checkEraser);

        canvas.drawBitmap(checkBitmap, 0, 0, null);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        attachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        attachedToWindow = false;
    }


    public void setProgress(float value) {
        if (progress == value) {
            return;
        }
        progress = value;
        invalidate();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getProgress() {
        return progress;
    }

    public void setCheckedColor(int value) {
        bitmapColor = value;
    }

    public void setBorderColor(int value) {
        borderColor = value;
        borderPaint.setColor(borderColor);
    }

    private void cancelAnim() {
        if (checkAnim != null) {
            checkAnim.cancel();
        }
    }

    private void addAnim(boolean isChecked) {
        checkAnim = ObjectAnimator.ofFloat(this, "progress", isChecked ? 1.0f : 0.0f);
        checkAnim.setDuration(300);
        checkAnim.start();
    }

    public void setChecked(boolean checked, boolean animated) {
        if (checked == isChecked) {
            return;
        }
        isChecked = checked;

        if (attachedToWindow && animated) {
            addAnim(checked);
        } else {
            cancelAnim();
            setProgress(checked ? 1.0f : 0.0f);
        }
    }


    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    @Override
    public void setChecked(boolean b) {
        setChecked(b, true);
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.change(b);
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    public int dp(float value) {
        if (value == 0) {
            return 0;
        }
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }

    public interface OnCheckedChangeListener {
        void change(boolean isCheck);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public OnCheckedChangeListener onCheckedChangeListener;
}
