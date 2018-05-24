package picker.prim.com.primpicker_core.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import picker.prim.com.primpicker_core.Constance;
import picker.prim.com.primpicker_core.R;
import picker.prim.com.primpicker_core.cursors.FileMediaCallback;
import picker.prim.com.primpicker_core.entity.Directory;
import picker.prim.com.primpicker_core.entity.SelectSpec;
import picker.prim.com.primpicker_core.ui.adapter.SelectAdapter;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：文件选择fragment
 * 修订历史：
 * ================================================
 */
public class PrimSelectFragment extends Fragment implements FileMediaCallback.MediaCallback {

    private RecyclerView recyclerView;

    private FileMediaCallback fileMediaCallback = new FileMediaCallback();

    private SelectAdapter adapter;

    public static PrimSelectFragment newInstance(Directory directory) {
        PrimSelectFragment fragment = new PrimSelectFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constance.EXTRA_DATA, directory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Directory directory = getArguments().getParcelable(Constance.EXTRA_DATA);
        adapter = new SelectAdapter(getActivity(), recyclerView);
        int spanCount = SelectSpec.getInstance().spanCount;
        /**
         * setHasFixedSize 的作用就是确保尺寸是通过用户输入从而确保RecyclerView的尺寸是一个常数。
         * RecyclerView 的Item宽或者高不会变。
         * 每一个Item添加或者删除都不会变。
         * 如果你没有设置setHasFixedSized没有设置的代价将会是非常昂贵的。
         * 因为RecyclerView会需要而外计算每个item的size，
         */
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        int space = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new GridItemDecoration(spanCount, space, false));
        recyclerView.setAdapter(adapter);
        fileMediaCallback.onCreate(getActivity(), this);
        fileMediaCallback.load(directory, SelectSpec.getInstance().capture);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fileMediaCallback.onDestory();
    }

    @Override
    public void onMediaLoad(Cursor cursor) {
        adapter.setCursor(cursor);
    }

    @Override
    public void onMediaReset() {
        adapter.setCursor(null);
    }
}
