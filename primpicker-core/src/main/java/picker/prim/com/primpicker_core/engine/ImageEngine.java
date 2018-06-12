package picker.prim.com.primpicker_core.engine;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/29 0029
 * 描    述：image loader interface 需自己实现
 * 修订历史：
 * ================================================
 */
public interface ImageEngine {
    void loadImageThumbnail(Context context, int resize, Drawable placeholder, ImageView view, Uri uri);

    void loadImage(Context context, int resizeX, int resizeY, Drawable placeholder, ImageView view, Uri uri);

    void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView view, Uri uri);

    void loadGifImage(Context context, int resizeX, int resizeY, Drawable placeholder, ImageView view, Uri uri);
}
