package picker.prim.com.primpicker_core.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import picker.prim.com.primpicker_core.R;
import picker.prim.com.primpicker_core.engine.ImageEngine;
import picker.prim.com.primpicker_core.entity.MediaItem;
import picker.prim.com.primpicker_core.entity.SelectSpec;
import picker.prim.com.primpicker_core.utils.PathUtils;

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

    private ImageView iv_media_thumbnail, iv_media_gif;

    private PrimCheckBox media_select_cb;

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

    private long maxDuration, selectVideoMaxSizeKB, selectVideoMaxSizeM;

    private void init(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.lib_item_grid_layout, this, true);
        iv_media_thumbnail = (ImageView) findViewById(R.id.iv_media_thumbnail);
        media_select_cb = (PrimCheckBox) findViewById(R.id.media_select_cb);
        media_tv_time = (TextView) findViewById(R.id.media_tv_time);
        media_select_view = findViewById(R.id.media_select_view);
        iv_media_gif = (ImageView) findViewById(R.id.iv_media_gif);
        iv_media_thumbnail.setOnClickListener(this);
        media_select_view.setOnClickListener(this);
        maxDuration = SelectSpec.getInstance().selectVideoMaxDuration;
        selectVideoMaxSizeKB = SelectSpec.getInstance().selectVideoMaxSizeKB;
        selectVideoMaxSizeM = SelectSpec.getInstance().selectVideoMaxSizeM;
        media_select_cb.setOnClickListener(this);
        media_select_cb.setOnCheckedChangeListener(new PrimCheckBox.OnCheckedChangeListener() {
            @Override
            public void change(boolean isCheck) {
                if (isCheck) {
                    media_select_view.setVisibility(VISIBLE);
                } else {
                    media_select_view.setVisibility(GONE);
                }
            }
        });
    }

    private boolean Interception(Context context) {
        if (media_select_cb.getAlpha() == 0.5f) {
            setChecked(false);
            Toast.makeText(context, "最多选择只能选择" + SelectSpec.getInstance().maxSelected + "个文件", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (maxDuration != 0 &&
                PathUtils.getVideoTime(PathUtils.getPath(context, mediaItem.getContentUri())) > maxDuration) {
            setChecked(false);
            Toast.makeText(context, "所选视频需小于" + PathUtils.timeParse(maxDuration) + "分钟", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (selectVideoMaxSizeKB != 0 && mediaItem.size > PathUtils.sizeKbtoByte(selectVideoMaxSizeKB)) {
            setChecked(false);
            Toast.makeText(context, "所选择视频需小于" + selectVideoMaxSizeKB + "KB", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (selectVideoMaxSizeM != 0 && mediaItem.size > PathUtils.sizeMtoByte(selectVideoMaxSizeM)) {
            setChecked(false);
            Toast.makeText(context, "所选择视频需小于" + selectVideoMaxSizeM + "M", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    public void setCheckBox(boolean flag) {
        if (!flag) {
            media_select_cb.setAlpha(0.5f);
        } else {
            media_select_cb.setAlpha(1.0f);
        }
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
        ImageEngine imageLoader = SelectSpec.getInstance().imageLoader;
        if (imageLoader == null) {
            throw new RuntimeException("most interface ImageEngine");
        }
        imageLoader.loadImage(getContext(), perImgInfo.mResize, perImgInfo.mResize, null, iv_media_thumbnail, mediaItem.getContentUri());
        if (mediaItem.isGif()) {
            iv_media_gif.setVisibility(VISIBLE);
        } else {
            iv_media_gif.setVisibility(GONE);
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
            if (!SelectSpec.getInstance().isPerViewEnable || !SelectSpec.getInstance().isClickItemPerOrSelect) {
                if (Interception(getContext())) {
                    return;
                }
            }
            if (mOnMediaGridItemClick != null && mOnMediaGridItemClick.get() != null) {
                mOnMediaGridItemClick.get().clickItem(iv_media_thumbnail, media_select_cb, mediaItem, perImgInfo.viewHolder);
            }
        } else if (i == R.id.media_select_view) {
            if (!SelectSpec.getInstance().isPerViewEnable || !SelectSpec.getInstance().isClickItemPerOrSelect) {
                if (Interception(getContext())) {
                    return;
                }
            }
            if (mOnMediaGridItemClick != null && mOnMediaGridItemClick.get() != null) {
                mOnMediaGridItemClick.get().clickItem(iv_media_thumbnail, media_select_cb, mediaItem, perImgInfo.viewHolder);
            }
        } else if (i == R.id.media_select_cb) {
            if (Interception(getContext()))
                return;
            boolean checked = media_select_cb.isChecked();
            if (mOnMediaGridItemClick != null && mOnMediaGridItemClick.get() != null) {
                mOnMediaGridItemClick.get().clickSelectItem(checked, media_select_cb, mediaItem, perImgInfo.viewHolder);
            }
        }
    }

    public interface OnMediaGridItemClick {
        void clickItem(ImageView imageView, PrimCheckBox checkBox, MediaItem item, RecyclerView.ViewHolder viewHolder);

        void clickSelectItem(boolean isCheck, PrimCheckBox checkBox, MediaItem item, RecyclerView.ViewHolder viewHolder);
    }

    private WeakReference<OnMediaGridItemClick> mOnMediaGridItemClick;

    public void setOnMediaGridItemClick(OnMediaGridItemClick onMediaGridItemClick) {
        mOnMediaGridItemClick = new WeakReference<>(onMediaGridItemClick);
    }
}
