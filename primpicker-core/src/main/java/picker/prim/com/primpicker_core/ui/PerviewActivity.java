package picker.prim.com.primpicker_core.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import picker.prim.com.primpicker_core.Constance;
import picker.prim.com.primpicker_core.R;
import picker.prim.com.primpicker_core.cursors.FileMediaCallback;
import picker.prim.com.primpicker_core.entity.Directory;
import picker.prim.com.primpicker_core.entity.MediaItem;
import picker.prim.com.primpicker_core.entity.SelectItemCollection;
import picker.prim.com.primpicker_core.entity.SelectSpec;
import picker.prim.com.primpicker_core.ui.adapter.PerviewPageAdapter;

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
public class PerviewActivity extends AppCompatActivity implements View.OnClickListener
        , ViewPager.OnPageChangeListener,
        FileMediaCallback.MediaCallback {

    private ImageView iv_picker_back;

    private TextView btn_next;

    private ViewPager viewpager;

    private CheckBox cb_select;

    private PerviewPageAdapter adapter;

    private ArrayList<MediaItem> mediaItems;

    private MediaItem item;

    private static final String TAG = "PerviewActivity";

    private boolean isPer;

    private Directory directory;

    private FileMediaCallback fileMediaCallback;

    private SelectItemCollection selectItemCollection = new SelectItemCollection(this);

    public static void newInstance(Activity activity, Directory directory, ArrayList<MediaItem> items, MediaItem item, boolean isPer) {
        Intent intent = new Intent(activity, PerviewActivity.class);
        intent.putExtra(Constance.EXTRA_DATA, directory);
        intent.putExtra(Constance.IS_PER, isPer);
        intent.putParcelableArrayListExtra(Constance.EXTRA_DATA_ITEMS, items);
        intent.putExtra(Constance.EXTRA_DATA_ITEM, item);
        activity.startActivityForResult(intent, REQUEST_CODE_PREVIEW);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_perview_layout);
        item = getIntent().getParcelableExtra(Constance.EXTRA_DATA_ITEM);
        mediaItems = getIntent().getParcelableArrayListExtra(Constance.EXTRA_DATA_ITEMS);
        directory = getIntent().getParcelableExtra(Constance.EXTRA_DATA);
        isPer = getIntent().getBooleanExtra(Constance.IS_PER, false);
        fileMediaCallback = new FileMediaCallback();
        iv_picker_back = (ImageView) findViewById(R.id.iv_picker_back);
        btn_next = (TextView) findViewById(R.id.btn_next);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        cb_select = (CheckBox) findViewById(R.id.cb_select);
        adapter = new PerviewPageAdapter(getSupportFragmentManager());
        viewpager.addOnPageChangeListener(this);
        viewpager.setAdapter(adapter);
        fileMediaCallback.onCreate(this, this);
        btn_next.setOnClickListener(this);
        cb_select.setOnClickListener(this);
        iv_picker_back.setOnClickListener(this);
        if (mediaItems != null && mediaItems.size() > 0) {
            selectItemCollection.setDefaultItems(mediaItems);
            checkNextBtn();
        }
        if (isPer) {
            adapter.addAllItems(mediaItems);
            adapter.notifyDataSetChanged();
            cb_select.setChecked(true);
        } else {
            fileMediaCallback.load(directory);
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkNextBtn() {
        if (selectItemCollection.count() > 0) {
            btn_next.setEnabled(true);
            btn_next.setTextColor(getResources().getColor(R.color.color_ffffff));
            btn_next.setText(getResources().getString(R.string.str_next_text) + "(" + selectItemCollection.count() + ")");
        } else {
            btn_next.setEnabled(false);
            btn_next.setTextColor(getResources().getColor(R.color.color_666666));
            btn_next.setText(getResources().getString(R.string.str_next_text));
        }
    }

    @Override
    public void onBackPressed() {
        sendBackResult(false);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_picker_back) {
            onBackPressed();
        } else if (id == R.id.btn_next) {
            sendBackResult(true);
            finish();
        } else if (id == R.id.cb_select) {
            if (cb_select.getAlpha() == 0.5f) {
                cb_select.setChecked(false);
                Toast.makeText(this, "最多选择只能选择" + SelectSpec.getInstance().maxSelected + "个文件", Toast.LENGTH_SHORT).show();
                return;
            }
            MediaItem mediaItem = adapter.getMediaItem(viewpager.getCurrentItem());
            int checkNumOf = selectItemCollection.checkNumOf(mediaItem);
            if (checkNumOf == Integer.MAX_VALUE) {
                selectItemCollection.add(mediaItem);
            } else {
                selectItemCollection.remove(mediaItem);
            }
            checkNextBtn();
        }
    }

    protected void sendBackResult(boolean apply) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constance.EXTRA_DATA_ITEMS, selectItemCollection.asList());
        intent.putExtra(Constance.APPLY, apply);
        setResult(Activity.RESULT_OK, intent);
    }

    /** 检查选择的状态 防止勾选错乱 */
    private void checkSelectState(MediaItem item) {
        boolean checkNumOf = selectItemCollection.isSelected(item);
        if (checkNumOf) {
            cb_select.setAlpha(1.0f);
            cb_select.setChecked(true);
        } else {
            if (selectItemCollection.maxSelectedRached()) {
                cb_select.setAlpha(0.5f);
                cb_select.setChecked(false);
            } else {
                cb_select.setAlpha(1.0f);
                cb_select.setChecked(false);
            }
        }
    }

    private int mPreviousPos = -1;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        PerviewPageAdapter adapter = (PerviewPageAdapter) viewpager.getAdapter();
        MediaItem mediaItem = adapter.getMediaItem(position);
        checkSelectState(mediaItem);
        mPreviousPos = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private boolean isLoadPosition;

    @Override
    public void onMediaLoad(Cursor cursor) {
        mediaItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            mediaItems.add(MediaItem.valueOf(cursor));
        }
        adapter.addAllItems(mediaItems);
        adapter.notifyDataSetChanged();
        if (!isLoadPosition) {
            isLoadPosition = true;
            int indexOf = mediaItems.indexOf(item);
            viewpager.setCurrentItem(indexOf, false);
            mPreviousPos = indexOf;
        }
    }

    @Override
    public void onMediaReset() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fileMediaCallback.onDestory();
    }
}
