package picker.prim.com.primpicker_core.entity;

import java.util.ArrayList;
import java.util.Set;

import picker.prim.com.primpicker_core.engine.ImageEngine;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SelectSpec {
    public SelectSpec() {
    }

    public static SelectSpec getInstance() {
        return Holder.INSTANCE;
    }

    public static SelectSpec getCleanInstance() {
        SelectSpec instance = getInstance();
        instance.reset();
        return instance;
    }

    /** 选择文件的类型 */
    public Set<MimeType> mimeTypes;

    /** 一行显示几个 */
    public int spanCount;

    /** 最多选择多少个 */
    public int maxSelected;

    /** 是否显示拍照或录像 */
    public boolean capture;

    /** 是否进行压缩 */
    public boolean compress;

    /** 仅显示一种类型 */
    public boolean showSingleMediaType;

    /** 缩略图的比例 */
    public float thumbnailScale;

    /** 图片加载器 */
    public ImageEngine imageLoader;

    /** 设置选择视频的最大时长 毫秒 */
    public long selectVideoMaxDuration;

    /** 设置选择视频的最大大小 kb */
    public long selectVideoMaxSizeKB;

    /** 设置选择视频的最大大小 M */
    public long selectVideoMaxSizeM;

    /** 设置预览页是否允许有选择功能 若为:FALSE 只能预览 且选择按钮隐藏 */
    public boolean isPerAllowSelect;

    /** 设置点击图片或视频 若为 true :只浏览单个的视频或图片 */
    public boolean isPerClickShowSingle;

    /** 设置默认选择的items */
    public ArrayList<MediaItem> mediaItems;

    /** 预览选择的图片 */
    public boolean isPreview;

    /** 预览的第几个图片 */
    public int perviewCurrentItem;

    /** 是否开启预览 */
    public boolean isPerViewEnable;

    /** 设置点击item true 预览点击的item false 选中点击的item */
    public boolean isClickItemPerOrSelect;

    private void reset() {
        mediaItems = null;
        mimeTypes = null;
        spanCount = 3;
        maxSelected = 1;
        capture = false;
        compress = false;
        isPreview = false;
        showSingleMediaType = false;
        isPerViewEnable = true;
        isPerAllowSelect = true;
        isClickItemPerOrSelect = true;
        isPerClickShowSingle = false;
        thumbnailScale = 0.5f;
        selectVideoMaxSizeKB = 0;
        perviewCurrentItem = 0;
        selectVideoMaxSizeM = 0;
        selectVideoMaxDuration = 0;
    }

    public boolean onlyShowVideos() {
        return showSingleMediaType && MimeType.ofVideo().containsAll(mimeTypes);
    }

    public boolean onlyShowImgs() {
        return showSingleMediaType && MimeType.ofImage().containsAll(mimeTypes);
    }

    private static final class Holder {
        private static final SelectSpec INSTANCE = new SelectSpec();
    }
}
