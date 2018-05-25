package picker.prim.com.primpicker_core.ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import picker.prim.com.primpicker_core.R;
import picker.prim.com.primpicker_core.entity.Directory;


/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/25 0025
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DirectoryAdapter extends CursorAdapter {
    private final Drawable mPlaceholder;

    public DirectoryAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        TypedArray ta = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.album_thumbnail_placeholder});
        mPlaceholder = ta.getDrawable(0);
        ta.recycle();
    }

    public DirectoryAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

        TypedArray ta = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.album_thumbnail_placeholder});
        mPlaceholder = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lib_directory_item_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Directory directory = Directory.valueOf(cursor);
        ((TextView) view.findViewById(R.id.tv_dirs_name)).setText(directory.getDisplayName(context));
        ((TextView) view.findViewById(R.id.tv_dirs_count)).setText(String.valueOf(directory.getmCount()));

        Glide.with(context)
                .load(Uri.fromFile(new File(directory.getmCoverPath())))
                .asBitmap()
                .override(context.getResources().getDimensionPixelSize(R
                        .dimen.media_grid_size), context.getResources().getDimensionPixelSize(R
                        .dimen.media_grid_size))
                .placeholder(mPlaceholder)
                .centerCrop()
                .into(((ImageView) view.findViewById(R.id.iv_dirs_item)));
    }
}
