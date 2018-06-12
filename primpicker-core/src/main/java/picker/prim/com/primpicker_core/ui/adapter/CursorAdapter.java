package picker.prim.com.primpicker_core.ui.adapter;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class CursorAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    private Cursor cursor;
    private int mRowIDColumn;

    public CursorAdapter(Cursor cursor) {
        //stableId解决RecyclerView的notify方法使得图片加载时不闪烁
        setHasStableIds(true);
        setCursor(cursor);
    }

    public void setCursor(Cursor newCursor) {
        if (newCursor == cursor) {
            return;
        }

        if (newCursor != null) {
            cursor = newCursor;
            mRowIDColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(0, getItemCount());
            cursor = null;
            mRowIDColumn = -1;
        }
    }

    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public int getItemCount() {
        if (isDataVaild(cursor)) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        if (!isDataVaild(cursor)) {
            throw new IllegalStateException("Cannot lookup item id when cursor is in invalid state.");
        }
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Could not move cursor to position " + position
                    + " when trying to get an item id");
        }
        return cursor.getLong(mRowIDColumn);
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        if (!isDataVaild(cursor)) {
            throw new IllegalStateException("Cannot bind ViewHolder this cursor is in invalid state.");
        }
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Could not move cursor to position " + position
                    + " when trying to get an item id");
        }
        onBindViewHolder(holder, cursor, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Could not move cursor to position " + position
                    + " when trying to get an item id");
        }
        return getItemViewType(position, cursor);
    }

    protected abstract int getItemViewType(int position, Cursor cursor);

    protected abstract void onBindViewHolder(T holder, Cursor cursor, int position);


    public boolean isDataVaild(Cursor cursor) {
        return cursor != null && !cursor.isClosed();
    }
}
