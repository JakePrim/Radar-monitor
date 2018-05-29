package picker.prim.com.primpicker_core.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

import picker.prim.com.primpicker_core.entity.MediaItem;
import picker.prim.com.primpicker_core.ui.PerviewItemFragment;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/29 0029
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PerviewPageAdapter extends FragmentPagerAdapter {
    ArrayList<MediaItem> mediaItems = new ArrayList<>();

    public PerviewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PerviewItemFragment.newInstance(mediaItems.get(position));
    }

    @Override
    public int getCount() {
        return mediaItems.size();
    }

    public void addAllItems(List<MediaItem> itemList) {
        if (mediaItems != null)
            mediaItems.addAll(itemList);
    }

    public MediaItem getMediaItem(int position) {
        return mediaItems.get(position);
    }
}
