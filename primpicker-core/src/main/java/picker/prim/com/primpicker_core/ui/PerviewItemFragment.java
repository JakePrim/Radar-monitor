package picker.prim.com.primpicker_core.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import picker.prim.com.primpicker_core.Constance;
import picker.prim.com.primpicker_core.R;
import picker.prim.com.primpicker_core.entity.MediaItem;
import picker.prim.com.primpicker_core.utils.PathUtils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/29 0029
 * 描    述：预览fragment
 * 修订历史：
 * ================================================
 */
public class PerviewItemFragment extends Fragment {

    private ImageView iv_per_thumbnail, btn_video_play;

    public static PerviewItemFragment newInstance(MediaItem item) {
        PerviewItemFragment fragment = new PerviewItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constance.EXTRA_DATA, item);
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
        return inflater.inflate(R.layout.lib_perview_item_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_per_thumbnail = (ImageView) view.findViewById(R.id.iv_per_thumbnail);
        btn_video_play = (ImageView) view.findViewById(R.id.btn_video_play);
        final MediaItem item = getArguments().getParcelable(Constance.EXTRA_DATA);
        if (item == null) return;
        if (item.isVideo()) {
            btn_video_play.setVisibility(View.VISIBLE);
            btn_video_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(item.uri, "video/*");
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "App Not supporting video perview", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            btn_video_play.setVisibility(View.GONE);
        }
        Point size = PathUtils.getBitmapSize(item.getContentUri(), getActivity());
        if (item.isGif()) {
            Glide.with(this)
                    .load(item.getContentUri())
                    .asGif()
                    .override(size.x, size.y)
                    .priority(Priority.HIGH)
                    .fitCenter()
                    .into(iv_per_thumbnail);
        } else {
            Glide.with(this)
                    .load(item.getContentUri())
                    .override(size.x, size.y)
                    .priority(Priority.HIGH)
                    .fitCenter()
                    .into(iv_per_thumbnail);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
