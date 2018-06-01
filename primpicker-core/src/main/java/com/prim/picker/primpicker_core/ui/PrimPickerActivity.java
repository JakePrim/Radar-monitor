package com.prim.picker.primpicker_core.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.prim.picker.primpicker_core.Constance;
import com.prim.picker.primpicker_core.R;
import com.prim.picker.primpicker_core.cursors.FileLoaderCallback;
import com.prim.picker.primpicker_core.cursors.FileLoaderHelper;
import com.prim.picker.primpicker_core.entity.CaptureCollection;
import com.prim.picker.primpicker_core.entity.Directory;
import com.prim.picker.primpicker_core.entity.MediaItem;
import com.prim.picker.primpicker_core.entity.SelectItemCollection;
import com.prim.picker.primpicker_core.entity.SelectSpec;
import com.prim.picker.primpicker_core.listener.OpenCaptureListener;
import com.prim.picker.primpicker_core.ui.adapter.DirectoryAdapter;
import com.prim.picker.primpicker_core.ui.adapter.SelectAdapter;
import com.prim.picker.primpicker_core.ui.view.DirectorySpinner;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：选择文件的activity 这里参考了部分知乎的开源项目代码
 * 修订历史：
 * ================================================
 */
public class PrimPickerActivity extends AppCompatActivity implements FileLoaderCallback.LoaderCallback,
        View.OnClickListener,
        PrimSelectFragment.OnSelectFragmentListener,
        SelectAdapter.OnSelectItemListener,
        DirectorySpinner.OnDirsItemSelectedListener,
        OpenCaptureListener {

    private ImageView iv_picker_back;

    private TextView tv_picker_type;

    private TextView btn_next;

    private CheckBox cb_compress;

    private static final String TAG = "PrimPickerActivity";

    private FrameLayout container, layout_empty;

    private SelectItemCollection selectItemCollection = new SelectItemCollection(this);
    ;

    private Directory directory;

    private RelativeLayout layout_bottom;

    private DirectorySpinner directorySpinner;

    private DirectoryAdapter directoryAdapter;

    private CaptureCollection captureCollection = new CaptureCollection(this);

    public static final String EXTRA_RESULT_SELECTION = "extra_result_selection";
    public static final String EXTRA_RESULT_SELECTION_PATH = "extra_result_selection_path";
    public static final String EXTRA_RESULT_COMPRESS = "extra_result_compress";
    public static final String EXTRA_RESULT_ITEMS = "extra_result_items";

    public static final int ACTION_IMAGE_CAPTURE = 792;
    public static final int ACTION_VIDEO_CAPTURE = 337;
    public boolean isCompress;
    public static final int REQUEST_CODE_PREVIEW = 601;
    private TextView tv_pre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (SelectSpec.getInstance().isPreview) {
            PerviewActivity.newInstance(this, directory, SelectSpec.getInstance().mediaItems, null, true);
            return;
        }
        iv_picker_back = (ImageView) findViewById(R.id.iv_picker_back);
        layout_bottom = (RelativeLayout) findViewById(R.id.layout_bottom);
        tv_picker_type = (TextView) findViewById(R.id.tv_picker_type);
        tv_pre = (TextView) findViewById(R.id.tv_pre);
        btn_next = (TextView) findViewById(R.id.btn_next);
        cb_compress = (CheckBox) findViewById(R.id.cb_compress);
        container = (FrameLayout) findViewById(R.id.container);
        layout_empty = (FrameLayout) findViewById(R.id.layout_empty);
        FileLoaderHelper.getInstance().onCreate(this, this);
        FileLoaderHelper.getInstance().onRestoreInstanceState(savedInstanceState);
        FileLoaderHelper.getInstance().getLoadDirs();
        directoryAdapter = new DirectoryAdapter(this, null, false);
        directorySpinner = new DirectorySpinner(this);
        directorySpinner.setOnDirsItemSelectedListener(this);
        directorySpinner.setSelectTextView(tv_picker_type);
        directorySpinner.setPopupAnchorView(findViewById(R.id.layout_top));
        directorySpinner.setAdapter(directoryAdapter);
        iv_picker_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        tv_pre.setOnClickListener(this);
        cb_compress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCompress = isChecked;
            }
        });
        isCompress = SelectSpec.getInstance().compress;
        if (SelectSpec.getInstance().onlyShowVideos() && isCompress) {
            cb_compress.setVisibility(View.VISIBLE);
        } else {
            cb_compress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FileLoaderHelper.getInstance().onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileLoaderHelper.getInstance().onDestory();
    }

    @Override
    public void loadFinish(final Cursor data) {
        directoryAdapter.swapCursor(data);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                data.moveToPosition(0);
                directory = Directory.valueOf(data);
                if (directory.isAll() && SelectSpec.getInstance().capture) {
                    directory.addCaptureCount();
                }
                setData(directory);

            }
        });
    }

    private void setData(Directory directory) {
        tv_picker_type.setText(directory.getDisplayName(this));
        if (directory.isAll() && directory.isEmpty()) {
            container.setVisibility(View.GONE);
            layout_empty.setVisibility(View.VISIBLE);
        } else {
            container.setVisibility(View.VISIBLE);
            layout_empty.setVisibility(View.GONE);
            PrimSelectFragment fragment = PrimSelectFragment.newInstance(directory);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, PrimSelectFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void loadReset() {
        directoryAdapter.swapCursor(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == ACTION_IMAGE_CAPTURE) {
            //更新相册
            captureCollection.updateImg();
            //拍照回来后重新 加载数据
            FileLoaderHelper.getInstance().getLoadDirs();
        } else if (requestCode == ACTION_VIDEO_CAPTURE) {
            //更新相册
            captureCollection.updateVideo();
            //录制视频完成回来后重新载入数据
            FileLoaderHelper.getInstance().getLoadDirs();
        } else if (requestCode == REQUEST_CODE_PREVIEW) {
            ArrayList<MediaItem> items = data.getParcelableArrayListExtra(Constance.EXTRA_DATA_ITEMS);
            boolean apply = data.getBooleanExtra(Constance.APPLY, false);
            selectItemCollection.clear();
            selectItemCollection.setDefaultItems(items);
            if (apply) {//预览选择完成
                selectFinish(selectItemCollection);
            } else {//预览选择返回
                if (SelectSpec.getInstance().isPreview) {
                    selectFinish(selectItemCollection);
                } else {
                    Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(PrimSelectFragment.class.getSimpleName());
                    if (fragmentByTag instanceof PrimSelectFragment) {
                        ((PrimSelectFragment) fragmentByTag).refresh();
                    }
                    onUpdate();
                }
            }
        }
    }

    private void selectFinish(SelectItemCollection selectItemCollection) {
        Intent result = new Intent();
        ArrayList<Uri> selectedUris = (ArrayList<Uri>) selectItemCollection.asListOfUri();
        result.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selectedUris);
        ArrayList<String> selectedPaths = (ArrayList<String>) selectItemCollection.asListOfString();
        result.putStringArrayListExtra(EXTRA_RESULT_SELECTION_PATH, selectedPaths);
        result.putExtra(EXTRA_RESULT_COMPRESS, isCompress);
        result.putParcelableArrayListExtra(EXTRA_RESULT_ITEMS, selectItemCollection.asListOfItem());
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_picker_back) {
            finish();
        } else if (i == R.id.btn_next) {
            selectFinish(selectItemCollection);
        } else if (i == R.id.tv_pre) {
            PerviewActivity.newInstance(this, directory, selectItemCollection.asListOfItem(), null, true);
        }
    }

    @Override
    public SelectItemCollection getSelectItemCollection() {
        return selectItemCollection;
    }

    @Override
    public void itemClick(View view, MediaItem item, int position) {
        PerviewActivity.newInstance(this, directory, selectItemCollection.asListOfItem(), item, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onUpdate() {
        if (selectItemCollection.isEmpty()) {
            btn_next.setTextColor(getResources().getColor(R.color.color_666666));
            btn_next.setEnabled(false);
            btn_next.setText(getResources().getString(R.string.str_next_text));
            tv_pre.setEnabled(false);
            tv_pre.setTextColor(getResources().getColor(R.color.color_8c8c8c));
        } else {
            btn_next.setEnabled(true);
            tv_pre.setEnabled(true);
            tv_pre.setTextColor(getResources().getColor(R.color.color_000000));
            btn_next.setTextColor(getResources().getColor(R.color.color_ffffff));
            btn_next.setText(getResources().getString(R.string.str_next_text) + "(" + selectItemCollection.count() + ")");
        }
    }

    @Override
    public void onDirItemSelected(AdapterView<?> parent, View view, int position, long id) {
        directoryAdapter.getCursor().moveToPosition(position);
        directory = Directory.valueOf(directoryAdapter.getCursor());
        if (directory.isAll() && SelectSpec.getInstance().capture) {
            directory.addCaptureCount();
        }
        setData(directory);
    }

    @Override
    public void capture() {
        captureCollection.captureIntent();
    }
}
