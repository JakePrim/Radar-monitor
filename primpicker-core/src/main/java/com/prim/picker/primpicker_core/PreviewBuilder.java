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
 * 创建日期：6/1 0001
 * 描    述：预览
 * 修订历史：
 * ================================================
 */
public class PreviewBuilder {
    private SelectSpec selectSpec;
    private PrimPicker primPicker;

    public PreviewBuilder(PrimPicker primPicker, @NonNull Set<MimeType> mimeTypes) {
        this.primPicker = primPicker;
        this.selectSpec = SelectSpec.getCleanInstance();
        this.selectSpec.mimeTypes = mimeTypes;
        this.selectSpec.isPreview = true;
    }

    public PreviewBuilder setImageLoader(ImageEngine imageLoader) {
        this.selectSpec.imageLoader = imageLoader;
        return this;
    }

    public PreviewBuilder setPreviewItems(ArrayList<MediaItem> mediaItems) {
        this.selectSpec.mediaItems = mediaItems;
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
