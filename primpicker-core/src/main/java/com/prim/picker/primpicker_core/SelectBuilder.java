package com.prim.picker.primpicker_core;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Set;

import com.prim.picker.primpicker_core.engine.ImageEngine;
import com.prim.picker.primpicker_core.entity.MediaItem;
import com.prim.picker.primpicker_core.entity.MimeType;
import com.prim.picker.primpicker_core.entity.SelectSpec;
import com.prim.picker.primpicker_core.ui.PrimPickerActivity;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SelectBuilder {
    private SelectSpec selectSpec;
    private PrimPicker primPicker;

    public SelectBuilder(PrimPicker primPicker, @NonNull Set<MimeType> mimeTypes) {
        this.primPicker = primPicker;
        selectSpec = SelectSpec.getCleanInstance();
        selectSpec.mimeTypes = mimeTypes;
    }

    public SelectBuilder setImageLoader(ImageEngine imageLoader) {
        selectSpec.imageLoader = imageLoader;
        return this;
    }

    public SelectBuilder showSingleMediaType(boolean showSingleMediaType) {
        selectSpec.showSingleMediaType = showSingleMediaType;
        return this;
    }

    public SelectBuilder setMaxSelected(int maxSelected) {
        selectSpec.maxSelected = maxSelected;
        return this;
    }

    public SelectBuilder setSpanCount(int spanCount) {
        selectSpec.spanCount = spanCount;
        return this;
    }

    public SelectBuilder setCapture(boolean capture) {
        selectSpec.capture = capture;
        return this;
    }

    /**
     * 是否进行压缩
     *
     * @param compress
     *         true 是 FALSE 否
     *
     * @return SelectBuilder
     */
    public SelectBuilder setCompress(boolean compress) {
        selectSpec.compress = compress;
        return this;
    }

    /**
     * 默认选择的数据
     *
     * @param mediaItems
     *         数据
     *
     * @return SelectBuilder
     */
    public SelectBuilder setDefaultItems(ArrayList<MediaItem> mediaItems) {
        selectSpec.mediaItems = mediaItems;
        return this;
    }

    public void forResult(int requestCode) {
        Activity activity = primPicker.getActivity();
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, PrimPickerActivity.class);
        Fragment fragment = primPicker.getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }
}
