package picker.prim.com.primpicker_core.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import picker.prim.com.primpicker_core.R;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/24 0024
 * 描    述：文件夹 整体
 * 修订历史：
 * ================================================
 */
public class Directory implements Parcelable {
    protected Directory(Parcel in) {
        mId = in.readString();
        mCoverPath = in.readString();
        mDisplayName = in.readString();
        mCount = in.readLong();
    }

    public static final Creator<Directory> CREATOR = new Creator<Directory>() {
        @Override
        public Directory createFromParcel(Parcel in) {
            return new Directory(in);
        }

        @Override
        public Directory[] newArray(int size) {
            return new Directory[size];
        }
    };

    private final String mId;
    private final String mCoverPath;
    private final String mDisplayName;
    private long mCount;

    public static final String ALBUM_ID_ALL = String.valueOf(-1);
    public static final String ALBUM_NAME_ALL = "All";

    public Directory(String mId, String mCoverPath, String mDisplayName, long mCount) {
        this.mId = mId;
        this.mCoverPath = mCoverPath;
        this.mDisplayName = mDisplayName;
        this.mCount = mCount;
    }

    public static Directory valueOf(Cursor cursor) {
        return new Directory(
                cursor.getString(cursor.getColumnIndex("bucket_id")),
                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
                cursor.getString(cursor.getColumnIndex("bucket_display_name")),
                cursor.getLong(cursor.getColumnIndex("count")));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCoverPath);
        dest.writeString(mDisplayName);
        dest.writeLong(mCount);
    }

    public String getmId() {
        return mId;
    }

    public String getmCoverPath() {
        return mCoverPath;
    }

    public String getmDisplayName() {
        return mDisplayName;
    }

    public long getmCount() {
        return mCount;
    }

    public void addCaptureCount() {
        mCount++;
    }

    public String getDisplayName(Context context) {
        if (isAll()) {
            return "All Media";
        }
        return mDisplayName;
    }

    public boolean isAll() {
        return ALBUM_ID_ALL.equals(mId);
    }

    public boolean isEmpty() {
        return mCount == 0;
    }

    @Override
    public String toString() {
        return "ID:" + mId + " | mCoverPath:" + mCoverPath + " | mDisplayName:" + mDisplayName + " | mCount:" + mCount;
    }
}
