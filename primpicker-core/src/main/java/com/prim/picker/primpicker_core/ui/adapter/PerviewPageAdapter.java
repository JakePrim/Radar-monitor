package com.prim.picker.primpicker_core.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.prim.picker.primpicker_core.entity.MediaItem;
import com.prim.picker.primpicker_core.ui.PerviewItemFragment;

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

    private long baseId = 0;

    private static final String TAG = "PerviewPageAdapter";

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
        if (mediaItems != null) {
            mediaItems.addAll(itemList);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        return baseId + position;
    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        if (object instanceof View) {
//            container.removeView((View) object);
//        }
//    }

    public void deleteItems(MediaItem item) {
        mediaItems.remove(item);
        notifyDataSetChanged();
    }

    public MediaItem getMediaItem(int position) {
        return mediaItems.get(position);
    }

    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += getCount() + n;
        Log.e(TAG, "notifyChangeInPosition: " + baseId);
    }
}
