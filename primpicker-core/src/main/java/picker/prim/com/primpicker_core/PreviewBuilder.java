package picker.prim.com.primpicker_core;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Set;

import picker.prim.com.primpicker_core.engine.ImageEngine;
import picker.prim.com.primpicker_core.entity.MediaItem;
import picker.prim.com.primpicker_core.entity.MimeType;
import picker.prim.com.primpicker_core.entity.SelectSpec;
import picker.prim.com.primpicker_core.ui.PrimPickerActivity;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/1 0001
 * 描    述：预览选择文件配置
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

    /**
     * 设置图片加载器,此处自己实现 ImageEngine 接口,
     * 好处:本库不用再导入图片加载库了，防止项目中图片加载库冲突或多个图片加载库
     *
     * @param imageLoader
     *         图片加载器
     *
     * @return PreviewBuilder
     */
    public PreviewBuilder setImageLoader(ImageEngine imageLoader) {
        this.selectSpec.imageLoader = imageLoader;
        return this;
    }

    /**
     * 设置预览的数据,此数据是从选择文件中返回的数据{@link PrimPicker}.obtainItemsResult()
     * 本库没有存储此数据，主要是如果外部对文件数据删除，库中无法修改数据.
     * 所以从选择文件得到的数据,你可以做任何操作，然后将这些数据传入即可.
     *
     * @param mediaItems
     *         ArrayList<MediaItem>
     *
     * @return PreviewBuilder
     */
    public PreviewBuilder setPreviewItems(ArrayList<MediaItem> mediaItems) {
        this.selectSpec.mediaItems = mediaItems;
        return this;
    }

    /**
     * 点击的是第几个图片
     *
     * @param position
     *         position
     *
     * @return PreviewBuilder
     */
    public PreviewBuilder setCurrentItem(int position) {
        this.selectSpec.perviewCurrentItem = position;
        return this;
    }

    public void lastGo(int requestCode) {
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
