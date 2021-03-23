package com.prim.summer_common.ui.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author prim
 * @version 1.0.0
 * @desc Fragment的基类
 * @time 3/23/21 - 1:45 PM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public abstract class SummerBaseFragment extends Fragment {
    private View layoutView;

    @LayoutRes
    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(getLayoutId(), container, false);
        return layoutView;
    }
}
