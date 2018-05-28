package picker.prim.com.primpicker_core.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import picker.prim.com.primpicker_core.Constance;
import picker.prim.com.primpicker_core.utils.PathUtils;
import picker.prim.com.primpicker_core.R;
import picker.prim.com.primpicker_core.entity.Directory;
import picker.prim.com.primpicker_core.entity.MediaItem;

import static picker.prim.com.primpicker_core.ui.PrimPickerActivity.REQUEST_CODE_PREVIEW;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/25 0025
 * 描    述：预览界面
 * 修订历史：
 * ================================================
 */
public class PerviewActivity extends AppCompatActivity {

    private ImageView iv_per_thumbnail, btn_video_play;

    public static void newInstance(Activity activity, Directory directory, MediaItem item) {
        Intent intent = new Intent(activity, PerviewActivity.class);
        intent.putExtra(Constance.EXTRA_DATA, directory);
        intent.putExtra(Constance.EXTRA_DATA_ITEM, item);
        activity.startActivityForResult(intent, REQUEST_CODE_PREVIEW);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_perview_layout);
        iv_per_thumbnail = (ImageView) findViewById(R.id.iv_per_thumbnail);
        btn_video_play = (ImageView) findViewById(R.id.btn_video_play);
        final MediaItem item = getIntent().getParcelableExtra(Constance.EXTRA_DATA_ITEM);
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
                        Toast.makeText(PerviewActivity.this, "App Not supporting video perview", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            btn_video_play.setVisibility(View.GONE);
        }
        Point size = PathUtils.getBitmapSize(item.getContentUri(), this);
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
}
