package com.prim.picker.primpicker_core.cursors;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.lang.ref.WeakReference;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：文件加载回调
 * 修订历史：
 * ================================================
 */
public class FileLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

    private WeakReference<Context> mContext;

    private WeakReference<LoaderCallback> mLoaderCallback;

    private LoaderManager mLoaderManager;

    private static int MEDIA_TYPE = 1;

    private static final String STATE_CURRENT_SELECTION = "state_current_selection";

    private int mCurrentSelection;

    public void onCreate(FragmentActivity activity, LoaderCallback loaderCallback) {
        mContext = new WeakReference<Context>(activity);
        mLoaderCallback = new WeakReference<LoaderCallback>(loaderCallback);
        mLoaderManager = activity.getSupportLoaderManager();
    }

    public int getCurrentSelection() {
        return mCurrentSelection;
    }

    public void setStateCurrentSelection(int currentSelection) {
        mCurrentSelection = currentSelection;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }

        mCurrentSelection = savedInstanceState.getInt(STATE_CURRENT_SELECTION);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_CURRENT_SELECTION, mCurrentSelection);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        return FileLoader.newInstance(context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }
        if (mLoaderCallback != null && mLoaderCallback.get() != null) {
            mLoaderCallback.get().loadFinish(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }
        if (mLoaderCallback != null && mLoaderCallback.get() != null) {
            mLoaderCallback.get().loadReset();
        }
    }

    /**
     * load data
     */
    public void loadAlbums() {
        if (mLoaderManager.getLoader(MEDIA_TYPE) != null) {
            mLoaderManager.restartLoader(MEDIA_TYPE, null, this);
        } else {
            mLoaderManager.initLoader(MEDIA_TYPE, null, this);
        }
    }

    public void onDestroy() {
        if (mLoaderManager != null) {
            mLoaderManager.destroyLoader(MEDIA_TYPE);
        }
        mLoaderCallback = null;
    }

    public interface LoaderCallback {
        /**
         * onLoadFinished get Cursor
         *
         * @param data
         *         Cursor
         */
        void loadFinish(Cursor data);

        void loadReset();
    }
}
