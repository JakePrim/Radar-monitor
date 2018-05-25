package picker.prim.com.primpicker_core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.hardware.GeomagneticField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.lang.ref.WeakReference;

import picker.prim.com.primpicker_core.R;
import picker.prim.com.primpicker_core.entity.MediaItem;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：media item view
 * 修订历史：
 * ================================================
 */
public class MediaGridView extends FrameLayout implements View.OnClickListener {

    private MediaItem mediaItem;

    private ImageView iv_media_thumbnail;

    private CheckBox media_select_cb;

    private TextView media_tv_time;

    private View media_select_view;

    private static final String TAG = "MediaGridView";

    public MediaGridView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MediaGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MediaGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_grid_layout, this, true);

        iv_media_thumbnail = (ImageView) findViewById(R.id.iv_media_thumbnail);
        media_select_cb = (CheckBox) findViewById(R.id.media_select_cb);
        media_tv_time = (TextView) findViewById(R.id.media_tv_time);
        media_select_view = findViewById(R.id.media_select_view);

        iv_media_thumbnail.setOnClickListener(this);
        media_select_view.setOnClickListener(this);

        media_select_cb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = media_select_cb.isChecked();
                if (checked) {
                    media_select_view.setVisibility(VISIBLE);
                } else {
                    media_select_view.setVisibility(GONE);
                }
                if (mOnMediaGridItemClick != null && mOnMediaGridItemClick.get() != null) {
                    mOnMediaGridItemClick.get().clickSelectItem(checked, media_select_cb, mediaItem, perImgInfo.viewHolder);
                }
            }
        });

    }

    public void setCheckBox(boolean flag) {
        media_select_cb.setEnabled(flag);
    }

    public void setChecked(boolean checked) {
        media_select_cb.setChecked(checked);
    }

    public void bindMediaItem(MediaItem item) {
        this.mediaItem = item;
        setImage();
        setVideoTime();
    }

    private void setVideoTime() {
        if (mediaItem.isVideo()) {
            media_tv_time.setVisibility(VISIBLE);
            media_tv_time.setText(DateUtils.formatElapsedTime(mediaItem.duration / 1000));
        } else {
            media_tv_time.setVisibility(GONE);
        }
    }

    private void setImage() {
        if (mediaItem.isGif()) {
            Glide.with(getContext())
                    .load(mediaItem.getContentUri())
                    .asGif()
                    .override(perImgInfo.mResize, perImgInfo.mResize)
                    .centerCrop()
                    .into(iv_media_thumbnail);
        } else {
            Glide.with(getContext())
                    .load(mediaItem.getContentUri())
                    .asBitmap()
                    .override(perImgInfo.mResize, perImgInfo.mResize)
                    .centerCrop()
                    .into(iv_media_thumbnail);
        }
    }

    public static class PerImgInfo {
        int mResize;
        Drawable drawable;
        RecyclerView.ViewHolder viewHolder;

        public PerImgInfo(int mResize, Drawable drawable, RecyclerView.ViewHolder viewHolder) {
            this.mResize = mResize;
            this.drawable = drawable;
            this.viewHolder = viewHolder;
        }
    }

    private PerImgInfo perImgInfo;

    public void bindPerImgInfo(PerImgInfo imgInfo) {
        this.perImgInfo = imgInfo;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_media_thumbnail) {
            if (mOnMediaGridItemClick != null && mOnMediaGridItemClick.get() != null) {
                mOnMediaGridItemClick.get().clickItem(iv_media_thumbnail, mediaItem, perImgInfo.viewHolder);
            }
        } else if (i == R.id.media_select_view) {
            if (mOnMediaGridItemClick != null && mOnMediaGridItemClick.get() != null) {
                mOnMediaGridItemClick.get().clickItem(iv_media_thumbnail, mediaItem, perImgInfo.viewHolder);
            }
        }
    }

    public interface OnMediaGridItemClick {
        void clickItem(ImageView imageView, MediaItem item, RecyclerView.ViewHolder viewHolder);

        void clickSelectItem(boolean isCheck, CheckBox checkBox, MediaItem item, RecyclerView.ViewHolder viewHolder);
    }

    private WeakReference<OnMediaGridItemClick> mOnMediaGridItemClick;

    public void setOnMediaGridItemClick(OnMediaGridItemClick onMediaGridItemClick) {
        mOnMediaGridItemClick = new WeakReference<>(onMediaGridItemClick);
    }
}
