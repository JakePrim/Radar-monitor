package com.prim.picker.primpicker_core.entity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import static com.prim.picker.primpicker_core.ui.PrimPickerActivity.ACTION_IMAGE_CAPTURE;
import static com.prim.picker.primpicker_core.ui.PrimPickerActivity.ACTION_VIDEO_CAPTURE;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/28 0028
 * 描    述：拍照 或 录像的跳转处理
 * 修订历史：
 * ================================================
 */
public class CaptureCollection {
    private final WeakReference<Activity> mContext;

    public CaptureCollection(Activity activity) {
        mContext = new WeakReference<Activity>(activity);
    }

    private boolean hasCamera(Context context) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void captureIntent() {
        if (!hasCamera(mContext.get())) return;
        if (SelectSpec.getInstance().onlyShowVideos()) {
            openCamera();
        } else {
            openCapture();
        }
    }

    private static Uri cameraUri;

    private static String imagePaths;

    /** 打开照相机拍照 */
    public void openCapture() {
        try {
            Intent take_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imagePaths = Environment.getExternalStorageDirectory().getPath()
                    + "/temp/" + (System.currentTimeMillis() + ".jpg");
            // 必须确保文件夹路径存在，否则拍照后无法完成回调
            File vFile = new File(imagePaths);
            if (!vFile.exists()) {
                File vDirPath = vFile.getParentFile();
                vDirPath.mkdirs();
            } else {
                if (vFile.exists()) {
                    vFile.delete();
                }
            }
            cameraUri = getUriForFile(mContext.get(), mContext.get().getPackageName(), vFile);
            take_intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
            take_intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                List<ResolveInfo> resInfoList = mContext.get().getPackageManager()
                        .queryIntentActivities(take_intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    mContext.get().grantUriPermission(packageName, cameraUri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
            mContext.get().startActivityForResult(take_intent, ACTION_IMAGE_CAPTURE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 打开照相机录像 */
    public void openCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//            intent.setAction("android.media.action.VIDEO_CAPTURE");
            intent.addCategory("android.intent.category.DEFAULT");
            // 保存录像到指定的路径
            imagePaths = Environment.getExternalStorageDirectory().getPath()
                    + "/temp/" + (System.currentTimeMillis() + ".mp4");
            // 必须确保文件夹路径存在，否则拍照后无法完成回调
            File vFile = new File(imagePaths);
            if (!vFile.exists()) {
                File vDirPath = vFile.getParentFile();
                vDirPath.mkdirs();
            } else {
                if (vFile.exists()) {
                    vFile.delete();
                }
            }
            cameraUri = getUriForFile(mContext.get(), mContext.get().getPackageName(), vFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                List<ResolveInfo> resInfoList = mContext.get().getPackageManager()
                        .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    mContext.get().grantUriPermission(packageName, cameraUri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
            mContext.get().startActivityForResult(intent, ACTION_VIDEO_CAPTURE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Uri getUriForFile(Context context, String name, File vFile) {
        Uri cameraUri;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            cameraUri = Uri.fromFile(vFile);
        } else {
            cameraUri = FileProvider.getUriForFile(context, name, vFile);
        }
        return cameraUri;
    }

    public static Uri getUri() {
        return cameraUri;
    }

    public static String getPath() {
        return imagePaths;
    }

    /**
     * 更新相册
     */
    public void refreshFile() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imagePaths);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.get().sendBroadcast(intent);
    }

    /**
     * 更新相册图片
     */
    public void updateImg() {
        if (TextUtils.isEmpty(imagePaths)) {
            return;
        }
        File file = new File(imagePaths);
        ContentValues localContentValues = new ContentValues();

        localContentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());

        localContentValues.put(MediaStore.Images.Media.TITLE, file.getName());

        localContentValues.put(MediaStore.Images.Media.DISPLAY_NAME, file.getName());

        localContentValues.put(MediaStore.Images.Media.DESCRIPTION, "save image ---");

        localContentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        localContentValues.put(MediaStore.Images.Media.SIZE, file.length());

        localContentValues.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());

        localContentValues.put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis());

        localContentValues.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());

        localContentValues.put(MediaStore.Images.Media.ORIENTATION, Integer.valueOf(0));

        ContentResolver localContentResolver = mContext.get().getContentResolver();

        Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        localContentResolver.insert(localUri, localContentValues);
    }

    /**
     * 更新相册的视频
     */
    public void updateVideo() {
        if (TextUtils.isEmpty(imagePaths)) {
            return;
        }
        File file = new File(imagePaths);
        ContentValues localContentValues = new ContentValues();

        localContentValues.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());

        localContentValues.put(MediaStore.Video.Media.TITLE, file.getName());

        localContentValues.put(MediaStore.Video.Media.DISPLAY_NAME, file.getName());

        localContentValues.put(MediaStore.Video.Media.DESCRIPTION, "save video ---");

        localContentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");

        localContentValues.put(MediaStore.Video.Media.SIZE, file.length());

        localContentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());

        localContentValues.put(MediaStore.Video.Media.DATE_MODIFIED, System.currentTimeMillis());

        localContentValues.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis());

        localContentValues.put(MediaStore.Video.Media.DURATION, getRingDuring(file));

        Uri localUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        ContentResolver localContentResolver = mContext.get().getContentResolver();

        localContentResolver.insert(localUri, localContentValues);
    }

    private static final String TAG = "CaptureCollection";


    public long getRingDuring(File file) {
        MediaPlayer meidaPlayer = new MediaPlayer();
        long time = 0;
        try {
            meidaPlayer.setDataSource(file.getPath());
            meidaPlayer.prepare();
            time = meidaPlayer.getDuration();//获得了视频的时长（以毫秒为单位）
            meidaPlayer.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return time;
    }
}
