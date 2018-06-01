package com.prim.picker.primpicker_core.ui.view;


import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import com.prim.picker.primpicker_core.utils.PathUtils;
import com.prim.picker.primpicker_core.R;
import com.prim.picker.primpicker_core.entity.Directory;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/25 0025
 * 描    述：文件夹选择弹窗
 * 修订历史：
 * ================================================
 */
public class DirectorySpinner {
    private ListPopupWindow popupWindow;

    private CursorAdapter mAdapter;

    private TextView mSelected;

    public DirectorySpinner(@NonNull Context context) {
        popupWindow = new ListPopupWindow(context);
        popupWindow.setModal(true);////模态框，设置为true响应物理键
        float density = context.getResources().getDisplayMetrics().density;//获取屏幕的密度
        popupWindow.setContentWidth((int) (216 * density));
        popupWindow.setHorizontalOffset((int) (16 * density));
        popupWindow.setVerticalOffset((int) (-48 * density));
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setSelectItem(parent.getContext(), position);
                if (mDirsItemListener != null && mDirsItemListener.get() != null) {
                    mDirsItemListener.get().onDirItemSelected(parent, view, position, id);
                }
            }
        });

    }

    public void setSelectItem(Context context, int position) {
        popupWindow.setSelection(position);
        onItemSelected(context, position);
    }

    private WeakReference<OnDirsItemSelectedListener> mDirsItemListener;

    public void setOnDirsItemSelectedListener(OnDirsItemSelectedListener onDirsItemSelectedListener) {
        mDirsItemListener = new WeakReference<OnDirsItemSelectedListener>(onDirsItemSelectedListener);
    }

    public interface OnDirsItemSelectedListener {
        void onDirItemSelected(AdapterView<?> parent, View view, int position, long id);
    }

    /**
     * 设置当前的文件夹名称
     *
     * @param context
     * @param position
     */
    private void onItemSelected(Context context, int position) {
        popupWindow.dismiss();
        Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);
        Directory album = Directory.valueOf(cursor);
        String displayName = album.getDisplayName(context);
        if (mSelected.getVisibility() == View.VISIBLE) {
            mSelected.setText(displayName);
        } else {
            if (PathUtils.hasICS()) {
                mSelected.setAlpha(0.0f);
                mSelected.setVisibility(View.VISIBLE);
                mSelected.setText(displayName);
                mSelected.animate().alpha(1.0f).setDuration(context.getResources().getInteger(
                        android.R.integer.config_longAnimTime)).start();
            } else {
                mSelected.setVisibility(View.VISIBLE);
                mSelected.setText(displayName);
            }

        }
    }

    public void setAdapter(CursorAdapter cursorAdapter) {
        popupWindow.setAdapter(cursorAdapter);
        mAdapter = cursorAdapter;
    }

    public void setPopupAnchorView(View view) {
        popupWindow.setAnchorView(view);
    }

    private static final int MAX_SHOWN_COUNT = 6;

    public void setSelectTextView(TextView textView) {
        mSelected = textView;
        mSelected.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemHeight = v.getResources().getDimensionPixelSize(R.dimen.album_item_height);
                popupWindow.setHeight(mAdapter.getCount() > MAX_SHOWN_COUNT ? itemHeight * MAX_SHOWN_COUNT
                                : itemHeight * mAdapter.getCount());
                popupWindow.show();
            }
        });
        mSelected.setOnTouchListener(popupWindow.createDragToOpenListener(mSelected));
    }
}
