package picker.prim.com.primpicker_core.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import picker.prim.com.primpicker_core.Constance;
import picker.prim.com.primpicker_core.R;
import picker.prim.com.primpicker_core.entity.MediaItem;
import picker.prim.com.primpicker_core.entity.SelectSpec;
import picker.prim.com.primpicker_core.utils.PathUtils;
import picker.prim.com.primpicker_core.utils.PhotoMetadataUtils;

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

    private MediaItem item;

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

    private static final String TAG = "PerviewItemFragment";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        item = getArguments().getParcelable(Constance.EXTRA_DATA);

        if (item == null) return;

        initView(view);

        bindView();

    }

    private void initView(View container) {
        iv_per_thumbnail = (ImageView) container.findViewById(isVideoType() ? R.id.iv_per_thumbnail_video : R.id.iv_per_thumbnail_photo);
        btn_video_play = (ImageView) container.findViewById(R.id.btn_video_play);
    }

    private void bindView() {

        iv_per_thumbnail.setVisibility(View.VISIBLE);

        if (isVideoType()) {
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


        Point size = PhotoMetadataUtils.getBitmapSize(item.getContentUri(), getActivity());

        Log.e(TAG, "bindView: " + size);

        if (item.isGif()) {
            SelectSpec.getInstance().imageLoader.loadGifImage(getActivity(), size.x, size.y, null, iv_per_thumbnail, item.getContentUri());
        } else {
            SelectSpec.getInstance().imageLoader.loadImage(getActivity(), size.x, size.y, null, iv_per_thumbnail, item.getContentUri());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private boolean isVideoType() {
        return item.isVideo();
    }
}
