package picker.prim.com.primpicker_core.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import picker.prim.com.primpicker_core.R;
import picker.prim.com.primpicker_core.entity.MediaItem;
import picker.prim.com.primpicker_core.entity.SelectSpec;
import picker.prim.com.primpicker_core.ui.MediaGridView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SelectAdapter extends CursorAdapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_CAPTURE = 0x01;
    private static final int VIEW_TYPE_MEDIA = 0x02;
    private WeakReference<Context> mContext;
    private RecyclerView recyclerView;
    private SelectSpec selectSpec;


    public SelectAdapter(Context context, RecyclerView recyclerView) {
        super(null);
        mContext = new WeakReference<>(context);
        selectSpec = SelectSpec.getInstance();
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MEDIA) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_grid_item_layout, parent, false);
            return new MediaViewHolder(inflate);
        } else if (viewType == VIEW_TYPE_CAPTURE) {
            return null;
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
            mediaViewHolder.mediaGridView.bindPerImgInfo(new MediaGridView.PerImgInfo(getImageResize(mContext.get()), null, holder));
            mediaViewHolder.mediaGridView.bindMediaItem(item);
        }
    }

    private int mImageResize;

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

    private static class MediaViewHolder extends RecyclerView.ViewHolder {

        private MediaGridView mediaGridView;

        public MediaViewHolder(View itemView) {
            super(itemView);
            mediaGridView = (MediaGridView) itemView;
        }
    }

}
