package picker.prim.com.primpicker_core.entity;

import java.util.Set;

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

    private void reset() {
        mimeTypes = null;
        spanCount = 3;
        maxSelected = 1;
        capture = false;
        compress = false;
        showSingleMediaType = false;
        thumbnailScale = 0.5f;
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
