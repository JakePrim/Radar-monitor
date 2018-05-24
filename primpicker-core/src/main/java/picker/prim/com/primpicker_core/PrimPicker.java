package picker.prim.com.primpicker_core;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;
import java.util.Set;

import picker.prim.com.primpicker_core.entity.MimeType;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：文件选择器的总入口
 * 修订历史：
 * ================================================
 */
public final class PrimPicker {
    private WeakReference<Activity> mContext;
    private WeakReference<Fragment> mFragment;

    public PrimPicker(Activity activity) {
        this(activity, null);
    }

    public PrimPicker(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    public PrimPicker(Activity activity, Fragment fragment) {
        this.mContext = new WeakReference<Activity>(activity);
        this.mFragment = new WeakReference<Fragment>(fragment);
    }

    public static PrimPicker with(Activity activity) {
        return new PrimPicker(activity);
    }

    public static PrimPicker with(Fragment fragment) {
        return new PrimPicker(fragment);
    }

    public SelectBuilder choose(Set<MimeType> types) {
        return new SelectBuilder(this, types);
    }

    Activity getActivity() {
        return mContext != null ? mContext.get() : null;
    }

    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }
}
