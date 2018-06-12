## PrimPicker

### PrimPicker is What ?

PrimPicker is a load local image and video selector for Android。

You Can :

- select images include JPG PNG GIF BMP WEBP
- select videos include MP4 3GP MPEG AVI
- you impl image loaders
- it in Activity or Fragment
- More to commission find out yourself


select | perview | picker
---|---|---
![device-2018-06-12-104844.png](https://upload-images.jianshu.io/upload_images/2005932-dd702636b15b56fa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) | ![device-2018-06-12-104907.png](https://upload-images.jianshu.io/upload_images/2005932-bf5d0e31d520f8d2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![device-2018-06-12-104805.png](https://upload-images.jianshu.io/upload_images/2005932-d3322e571e0b2905.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![device-2018-06-12-110014 [320i].gif](https://upload-images.jianshu.io/upload_images/2005932-09b81cd03a4cc341.gif?imageMogr2/auto-orient/strip)
### How Do I Use ?

Gradle:
```
repositories {
    jcenter()
}

dependencies {
    compile 'com.prim.lib:prim-picker:1.0.3'
}
```

Permission:


```
android.permission.READ_EXTERNAL_STORAGE
android.permission.WRITE_EXTERNAL_STORAGE
android.permission.CAMERA
```
如果运行Android 6.0+ 需要动态申请权限

Coding:

select file(选择文件)
```
          PrimPicker
                .with(this)
                .choose(MimeType.ofImage())
                .setSpanCount(3)
                .setMaxSelected(9)
                .setImageLoader(ImageLoader())
                .setShowSingleMediaType(true)
                .setCapture(true)
                .setDefaultItems(list)
                .lastGo(1001)
```

get select file date（获取选择文件的数据）


```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            list = PrimPicker.obtainItemsResult(data)
            pathlist = PrimPicker.obtainPathResult(data)
        }
    }
```

Get Uri or Path or MediaItem
```
 /** 获取返回值 uri */
    public static ArrayList<Uri> obtainUriResult(Intent data) {
        return data.getParcelableArrayListExtra(EXTRA_RESULT_SELECTION);
    }

    /** 获取返回值 string path */
    public static ArrayList<String> obtainPathResult(Intent data) {
        return data.getStringArrayListExtra(EXTRA_RESULT_SELECTION_PATH);
    }

    /** 获取返回值 boolean 是否压缩 如压缩需自己处理，本库没有实现该功能 */
    public static boolean obtainCompressResult(Intent data) {
        return data.getBooleanExtra(EXTRA_RESULT_COMPRESS, false);
    }

    /** 获取返回值 MediaItem 追加文件或预览文件时使用 */
    public static ArrayList<MediaItem> obtainItemsResult(Intent data) {
        return data.getParcelableArrayListExtra(EXTRA_RESULT_ITEMS);
    }
```

Add File(追加文件)

setDefaultItems(list) 选择文件得到的返回数据List<MediaItem> list 你可以对文件删除部分，想追加新的文件,将对list操作后的数据传入
```
         PrimPicker
                .with(this)
                .choose(MimeType.ofImage())
                .setSpanCount(3)
                .setMaxSelected(9)
                .setImageLoader(ImageLoader())
                .setShowSingleMediaType(true)
                .setCapture(true)
                .setDefaultItems(list)
                .lastGo(1001)
```

PerView File(对选择返回后的文件预览)

```
            PrimPicker
                    .with(this)
                    .preview(MimeType.ofImage())
                    .setImageLoader(ImageLoader())
                    .setPreviewItems(list)
                    .lastGo(1001)
```


More API(更多API)


```
/**
     * 设置是否可以拍照和录像
     *
     * @param capture
     *         true 会在第一个显示view false 不显示
     *
     * @return SelectBuilder
     */
    public SelectBuilder setCapture(boolean capture) {
        selectSpec.capture = capture;
        return this;
    }

    /**
     * 设置是否压缩视频和图片,压缩需要自己实现,此库没有实现该功能
     *
     * @param compress
     *         true 压缩 false 不压缩
     *
     * @return SelectBuilder
     */
    public SelectBuilder setCompress(boolean compress) {
        selectSpec.compress = compress;
        return this;
    }

    /**
     * 设置所选视频的最大时长
     *
     * @param duration
     *         毫秒
     *
     * @return SelectBuilder
     */
    public SelectBuilder setSelectVideoMaxDurationS(long duration) {
        selectSpec.selectVideoMaxDuration = duration;
        return this;
    }

    /**
     * 设置所选视频文件的最大的大小
     *
     * @param size
     *         KB
     *
     * @return SelectBuilder
     */
    public SelectBuilder setSelectVideoMaxSizeKB(long size) {
        selectSpec.selectVideoMaxSizeKB = size;
        return this;
    }

    /**
     * 设置所选视频文件的最大的大小
     *
     * @param size
     *         M
     *
     * @return SelectBuilder
     */
    public SelectBuilder setSelectVideoMaxSizeM(long size) {
        selectSpec.selectVideoMaxSizeM = size;
        return this;
    }

    /**
     * 设置预览界面可否进行选择和取消选择文件的操作,并与选择页面同步
     *
     * @param allow
     *         true 允许 false 不允许
     *
     * @return SelectBuilder
     */
    public SelectBuilder setPerAllowSelect(boolean allow) {
        selectSpec.isPerAllowSelect = allow;
        return this;
    }

    /**
     * 设置点击item 预览点击的item 其他的无法预览
     * 注意此方法和{@link #setClickItemPerOrSelect(boolean) {@link #setPerViewEnable(boolean)}}
     * 两个方法相关setClickItemPerOrSelect setPerViewEnable 都为true时此方法生效，否则此方法不生效
     *
     * @param single
     *         true 只预览点击的item false 点击item如果可以进入预览页 则可以预览所有文件
     *
     * @return SelectBuilder
     */
    public SelectBuilder setPerClickShowSingle(boolean single) {
        selectSpec.isPerClickShowSingle = single;
        return this;
    }

    /**
     * 在之前的数据基础上追加数据
     * 此数据是从选择文件中返回的数据{@link PrimPicker}.obtainItemsResult()
     * 本库没有存储此数据，主要是如果外部对文件数据删除，库中无法修改数据.
     * 所以从选择文件得到的数据,你可以做任何操作，然后将这些数据传入即可.
     *
     * @param mediaItems
     *         之前选择的数据
     *
     * @return SelectBuilder
     */
    public SelectBuilder setDefaultItems(ArrayList<MediaItem> mediaItems) {
        selectSpec.mediaItems = mediaItems;
        return this;
    }

    /**
     * 设置选择器是否具备预览功能,默认开启预览功能
     *
     * @param enable
     *         true 开启预览功能 false 不开启预览功能
     *
     * @return SelectBuilder
     */
    public SelectBuilder setPerViewEnable(boolean enable) {
        selectSpec.isPerViewEnable = enable;
        return this;
    }

    /**
     * 设置点击item 是预览还是选中
     *
     * @param enable
     *         true 点击item预览item ; FALSE 点击item选中item
     *
     * @return
     */
    public SelectBuilder setClickItemPerOrSelect(boolean enable) {
        selectSpec.isClickItemPerOrSelect = enable;
        return this;
    }
```

### Thanks

[Matisse](https://github.com/zhihu/Matisse) source code.


### License

```
Copyright 2018 JakePrim Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
