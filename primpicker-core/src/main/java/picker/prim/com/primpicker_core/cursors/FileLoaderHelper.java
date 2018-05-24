package picker.prim.com.primpicker_core.cursors;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import picker.prim.com.primpicker_core.Constance;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：加载文件数据的辅助类
 * 修订历史：
 * ================================================
 */
public class FileLoaderHelper {

    private static FileLoaderHelper mFileLoaderHelper = null;

    private FileLoaderHelper() {
        fileLoaderCallback = new FileLoaderCallback();
    }

    public static FileLoaderHelper getInstance() {
        if (mFileLoaderHelper == null) {
            synchronized (FileLoaderHelper.class) {
                if (mFileLoaderHelper == null) {
                    mFileLoaderHelper = new FileLoaderHelper();
                }
            }
        }
        return mFileLoaderHelper;
    }

    private FileLoaderCallback fileLoaderCallback;


    public void onCreate(FragmentActivity activity, FileLoaderCallback.LoaderCallback loaderCallback) {
        fileLoaderCallback.onCreate(activity, loaderCallback);
    }

    public void getLoadDirs() {
        fileLoaderCallback.loadAlbums();
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        fileLoaderCallback.onRestoreInstanceState(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        fileLoaderCallback.onSaveInstanceState(outState);
    }

    public void onDestory() {
        fileLoaderCallback.onDestroy();
    }

}
