package picker.prim.com.primpicker_core.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import picker.prim.com.primpicker_core.R;
import picker.prim.com.primpicker_core.entity.MediaItem;
import picker.prim.com.primpicker_core.entity.SelectItemCollection;
import picker.prim.com.primpicker_core.entity.SelectSpec;
import picker.prim.com.primpicker_core.listener.OpenCaptureListener;
import picker.prim.com.primpicker_core.ui.view.CaptureView;
import picker.prim.com.primpicker_core.ui.view.MediaGridView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：选择文件的adapter
 * 修订历史：
 * ================================================
 */
public class SelectAdapter extends CursorAdapter<RecyclerView.ViewHolder> implements MediaGridView.OnMediaGridItemClick {

    private static final int VIEW_TYPE_CAPTURE = 0x01;
    private static final int VIEW_TYPE_MEDIA = 0x02;
    private WeakReference<Context> mContext;
    private RecyclerView recyclerView;
    private SelectSpec selectSpec;
    private SelectItemCollection selectItemCollection;
    private WeakReference<OnSelectItemListener> mOnSelectItemListener;
    private int mImageResize;
    private static final String TAG = "SelectAdapter";

    public SelectAdapter(Context context, RecyclerView recyclerView, SelectItemCollection selectItemCollection) {
        super(null);
        mContext = new WeakReference<>(context);
        selectSpec = SelectSpec.getInstance();
        this.recyclerView = recyclerView;
        this.selectItemCollection = selectItemCollection;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MEDIA) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_grid_item_layout, parent, false);
            return new MediaViewHolder(inflate);
        } else if (viewType == VIEW_TYPE_CAPTURE) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_capture_item_layout, parent, false);
            return new MediaCaptureViewHolder(inflate);
        }
        return null;
    }

    @Override
    protected int getItemViewType(int position, Cursor cursor) {
        return MediaItem.valueOf(cursor).isCapture() ? VIEW_TYPE_CAPTURE : VIEW_TYPE_MEDIA;
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor, int position) {
        if (holder instanceof MediaViewHolder) {
            MediaViewHolder mediaViewHolder = (MediaViewHolder) holder;
            MediaItem item = MediaItem.valueOf(cursor);
            mediaViewHolder.mediaGridView.setOnMediaGridItemClick(this);
            mediaViewHolder.mediaGridView.bindPerImgInfo(new MediaGridView.PerImgInfo(getImageResize(mContext.get()), null, holder));
            mediaViewHolder.mediaGridView.bindMediaItem(item);
            checkSelectState(item, mediaViewHolder.mediaGridView);
        } else if (holder instanceof MediaCaptureViewHolder) {
            MediaCaptureViewHolder captureViewHolder = (MediaCaptureViewHolder) holder;
            captureViewHolder.captureView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContext.get() instanceof OpenCaptureListener) {
                        ((OpenCaptureListener) mContext.get()).capture();
                    }
                }
            });
        }
    }

    /** 检查选择的状态 防止勾选错乱 */
    private void checkSelectState(MediaItem item, MediaGridView mediaGridView) {
        boolean checkNumOf = selectItemCollection.isSelected(item);
        if (checkNumOf) {
            mediaGridView.setCheckBox(true);
            mediaGridView.setChecked(true);
        } else {
            if (selectItemCollection.maxSelectedRached()) {
                mediaGridView.setCheckBox(false);
                mediaGridView.setChecked(false);
            } else {
                mediaGridView.setCheckBox(true);
                mediaGridView.setChecked(false);
            }
        }
    }

    private int getImageResize(Context context) {
        if (mImageResize == 0) {
            RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
            int spanCount = ((GridLayoutManager) lm).getSpanCount();
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            int availableWidth = screenWidth - context.getResources().getDimensionPixelSize(
                    R.dimen.grid_spacing) * (spanCount - 1);
            mImageResize = availableWidth / spanCount;
            mImageResize = (int) (mImageResize * selectSpec.thumbnailScale);
        }
        return mImageResize;
    }

    @Override
    public void clickItem(ImageView imageView, MediaItem item, RecyclerView.ViewHolder viewHolder) {
        if (mOnSelectItemListener != null && mOnSelectItemListener.get() != null) {
            mOnSelectItemListener.get().itemClick(imageView, item, viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void clickSelectItem(boolean isCheck, CheckBox checkBox, MediaItem item, RecyclerView.ViewHolder viewHolder) {
        int checkNumOf = selectItemCollection.checkNumOf(item);
        if (checkNumOf == Integer.MAX_VALUE) {
            selectItemCollection.add(item);
            notifyCheckStateChanged();
        } else {
            selectItemCollection.remove(item);
            notifyCheckStateChanged();
        }
    }

    private void notifyCheckStateChanged() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyDataSetChanged();
                if (mOnSelectItemListener != null && mOnSelectItemListener.get() != null) {
                    mOnSelectItemListener.get().onUpdate();
                }
            }
        };
        handler.post(r);
    }

    private static class MediaViewHolder extends RecyclerView.ViewHolder {

        private MediaGridView mediaGridView;

        public MediaViewHolder(View itemView) {
            super(itemView);
            mediaGridView = (MediaGridView) itemView;
        }
    }

    private static class MediaCaptureViewHolder extends RecyclerView.ViewHolder {

        private CaptureView captureView;

        public MediaCaptureViewHolder(View itemView) {
            super(itemView);
            captureView = (CaptureView) itemView;
        }
    }

    public interface OnSelectItemListener {
        void itemClick(View view, MediaItem item, int position);

        void onUpdate();
    }

    public void registerSelectItemListener(OnSelectItemListener onSelectItemListener) {
        mOnSelectItemListener = new WeakReference<>(onSelectItemListener);
    }

    public void unRegisterSelectItemListener() {
        mOnSelectItemListener = null;
    }

}
