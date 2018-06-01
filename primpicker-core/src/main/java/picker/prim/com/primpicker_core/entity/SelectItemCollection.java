package picker.prim.com.primpicker_core.entity;

import android.content.Context;
import android.net.Uri;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import picker.prim.com.primpicker_core.utils.PathUtils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/25 0025
 * 描    述：选择的item集合管理类
 * 修订历史：
 * ================================================
 */
public class SelectItemCollection {
    private WeakReference<Context> mContext;

    private Set<MediaItem> mediaItems;

    public SelectItemCollection(Context context) {
        mContext = new WeakReference<Context>(context);
        mediaItems = new LinkedHashSet<>();
    }

    public void setDefaultItems(List<MediaItem> items) {
        mediaItems.addAll(items);
    }

    public boolean add(MediaItem mediaItem) {
        return mediaItems.add(mediaItem);
    }

    public boolean remove(MediaItem mediaItem) {
        return mediaItems.remove(mediaItem);
    }

    public boolean isEmpty() {
        return mediaItems == null || mediaItems.isEmpty();
    }

    public boolean isSelected(MediaItem item) {
        return mediaItems.contains(item);
    }

    public ArrayList<MediaItem> asListOfItem() {
        return new ArrayList<>(mediaItems);
    }

    public List<Uri> asListOfUri() {
        List<Uri> uriList = new ArrayList<>();
        for (MediaItem mediaItem : mediaItems) {
            uriList.add(mediaItem.getContentUri());
        }
        return uriList;
    }

    public List<String> asListOfString() {
        List<String> stringList = new ArrayList<>();
        for (MediaItem mediaItem : mediaItems) {
            stringList.add(PathUtils.getPath(mContext.get(), mediaItem.getContentUri()));
        }
        return stringList;
    }

    public boolean maxSelectedRached() {
        return mediaItems.size() == currentSetMaxSelected();
    }

    private int currentSetMaxSelected() {
        SelectSpec instance = SelectSpec.getInstance();
        return instance.maxSelected;
    }

    public int count() {
        return mediaItems.size();
    }

    public int checkNumOf(MediaItem item) {
        int indexOf = new ArrayList<>(mediaItems).indexOf(item);
        return indexOf == -1 ? Integer.MAX_VALUE : indexOf + 1;
    }

    public void clear() {
        mediaItems.clear();
    }


}
