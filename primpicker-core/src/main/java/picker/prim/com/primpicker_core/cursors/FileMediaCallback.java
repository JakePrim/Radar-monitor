package picker.prim.com.primpicker_core.cursors;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.lang.ref.WeakReference;

import picker.prim.com.primpicker_core.entity.Directory;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：获取数据参考知乎开源项目的代码
 * 修订历史：
 * ================================================
 */
public class FileMediaCallback implements LoaderManager.LoaderCallbacks<Cursor> {
    private WeakReference<Context> mContext;
    private WeakReference<MediaCallback> mMediaCallback;
    private LoaderManager mLoaderManager;
    private static final int LOADER_ID = 2;
    private static final String ARGS_DIRECTORY = "args_directory";
    private static final String ARGS_ENABLE_CAPTURE = "args_enable_capture";

    public void onCreate(FragmentActivity activity, MediaCallback mediaCallback) {
        mContext = new WeakReference<Context>(activity);
        mMediaCallback = new WeakReference<>(mediaCallback);
        mLoaderManager = activity.getSupportLoaderManager();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        Directory directory = args.getParcelable(ARGS_DIRECTORY);
        if (directory == null) {
            return null;
        }
        return FileMediaLoader.newInstance(mContext.get(), directory, directory.isAll() && args.getBoolean(ARGS_ENABLE_CAPTURE, false));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }
        if (mMediaCallback != null && mMediaCallback.get() != null) {
            mMediaCallback.get().onMediaLoad(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }
        if (mMediaCallback != null && mMediaCallback.get() != null) {
            mMediaCallback.get().onMediaReset();
        }
    }

    public void onDestory() {
        if (mLoaderManager != null) {
            mLoaderManager.destroyLoader(LOADER_ID);
        }
        mMediaCallback = null;
    }

    public void load(@NonNull Directory directory) {
        load(directory, false);
    }

    public void load(@NonNull Directory directory, boolean capture) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_DIRECTORY, directory);
        args.putBoolean(ARGS_ENABLE_CAPTURE, capture);
        if (mLoaderManager.getLoader(LOADER_ID) != null) {
            mLoaderManager.restartLoader(LOADER_ID, args, this);
        } else {
            mLoaderManager.initLoader(LOADER_ID, args, this);
        }
    }

    public interface MediaCallback {
        void onMediaLoad(Cursor cursor);

        void onMediaReset();
    }
}
