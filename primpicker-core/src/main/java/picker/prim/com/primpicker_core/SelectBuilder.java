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
 * 创建日期：5/24 0024
 * 描    述：文件选择配置
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

    /**
     * 设置图片加载器,此处自己实现 ImageEngine 接口,
     * 好处:本库不用再导入图片加载库了，防止项目中图片加载库冲突或多个图片加载库
     *
     * @param imageLoader
     *         图片加载器
     *
     * @return SelectBuilder
     */
    public SelectBuilder setImageLoader(ImageEngine imageLoader) {
        selectSpec.imageLoader = imageLoader;
        return this;
    }

    /**
     * 设置是否只显示一种类型的文件
     * 如设置的文件选择类型为 {@link MimeType} ofImage  setShowSingleMediaType 为true 则只显示图片的文件，其他文件不显示
     *
     * @param showSingleMediaType
     *         true 只显示单个类型的文件
     *
     * @return
     */
    public SelectBuilder setShowSingleMediaType(boolean showSingleMediaType) {
        selectSpec.showSingleMediaType = showSingleMediaType;
        return this;
    }

    /**
     * 设置最大选择的文件个数
     *
     * @param maxSelected
     *         最大的个数
     *
     * @return SelectBuilder
     */
    public SelectBuilder setMaxSelected(int maxSelected) {
        selectSpec.maxSelected = maxSelected;
        return this;
    }

    /**
     * 设置文件的展示行数
     *
     * @param spanCount
     *         行数
     *
     * @return SelectBuilder
     */
    public SelectBuilder setSpanCount(int spanCount) {
        selectSpec.spanCount = spanCount;
        return this;
    }

    /**
     * 设置是否可以拍照和录像
     *
     * @param capture
     *         true 会在第一个显示view false 不显示
     *
     * @return SelectBuilder
     */
    public SelectBuilder setCapture(boolean capture) {
        selectSpec.capture = capture;
        return this;
    }

    /**
     * 设置是否压缩视频和图片,压缩需要自己实现,此库没有实现该功能
     *
     * @param compress
     *         true 压缩 false 不压缩
     *
     * @return SelectBuilder
     */
    public SelectBuilder setCompress(boolean compress) {
        selectSpec.compress = compress;
        return this;
    }

    /**
     * 设置所选视频的最大时长
     *
     * @param duration
     *         毫秒
     *
     * @return SelectBuilder
     */
    public SelectBuilder setSelectVideoMaxDurationS(long duration) {
        selectSpec.selectVideoMaxDuration = duration;
        return this;
    }

    /**
     * 设置所选视频文件的最大的大小
     *
     * @param size
     *         KB
     *
     * @return SelectBuilder
     */
    public SelectBuilder setSelectVideoMaxSizeKB(long size) {
        selectSpec.selectVideoMaxSizeKB = size;
        return this;
    }

    /**
     * 设置所选视频文件的最大的大小
     *
     * @param size
     *         M
     *
     * @return SelectBuilder
     */
    public SelectBuilder setSelectVideoMaxSizeM(long size) {
        selectSpec.selectVideoMaxSizeM = size;
        return this;
    }

    /**
     * 设置预览界面可否进行选择和取消选择文件的操作,并与选择页面同步
     *
     * @param allow
     *         true 允许 false 不允许
     *
     * @return SelectBuilder
     */
    public SelectBuilder setPerAllowSelect(boolean allow) {
        selectSpec.isPerAllowSelect = allow;
        return this;
    }

    /**
     * 设置点击item 预览点击的item 其他的无法预览
     * 注意此方法和{@link #setClickItemPerOrSelect(boolean) {@link #setPerViewEnable(boolean)}}
     * 两个方法相关setClickItemPerOrSelect setPerViewEnable 都为true时此方法生效，否则此方法不生效
     *
     * @param single
     *         true 只预览点击的item false 点击item如果可以进入预览页 则可以预览所有文件
     *
     * @return SelectBuilder
     */
    public SelectBuilder setPerClickShowSingle(boolean single) {
        selectSpec.isPerClickShowSingle = single;
        return this;
    }

    /**
     * 在之前的数据基础上追加数据
     * 此数据是从选择文件中返回的数据{@link PrimPicker}.obtainItemsResult()
     * 本库没有存储此数据，主要是如果外部对文件数据删除，库中无法修改数据.
     * 所以从选择文件得到的数据,你可以做任何操作，然后将这些数据传入即可.
     *
     * @param mediaItems
     *         之前选择的数据
     *
     * @return SelectBuilder
     */
    public SelectBuilder setDefaultItems(ArrayList<MediaItem> mediaItems) {
        selectSpec.mediaItems = mediaItems;
        return this;
    }

    /**
     * 设置选择器是否具备预览功能,默认开启预览功能
     *
     * @param enable
     *         true 开启预览功能 false 不开启预览功能
     *
     * @return SelectBuilder
     */
    public SelectBuilder setPerViewEnable(boolean enable) {
        selectSpec.isPerViewEnable = enable;
        return this;
    }

    /**
     * 设置点击item 是预览还是选中
     *
     * @param enable
     *         true 点击item预览item ; FALSE 点击item选中item
     *
     * @return
     */
    public SelectBuilder setClickItemPerOrSelect(boolean enable) {
        selectSpec.isClickItemPerOrSelect = enable;
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
