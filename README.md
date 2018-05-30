# PrimFilePicker 文件选择器

### 调用方式:
```
 PrimPicker.with(this)
                .choose(MimeType.ofVideo())
                .setImageLoader(new ImageLoaderEngine(this))
                .setCapture(true)
                .showSingleMediaType(true)
                .setSpanCount(3)
                .setMaxSelected(1)
                .forResult(1818);
```

### 拿取选择的数据
```
 @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if ((requestCode == 1818)) {
                List<String> uriList = PrimPicker.obtainPathResult(data);
                boolean compressResult = PrimPicker.obtainCompressResult(data);
                PreviewVideoActivity.startPreviewActivity(this, uriList.get(0), compressResult);
            }
        }
    }
 ```
 
 ### 返回数据
  ```
  /** 获取返回值 uri */
    public static List<Uri> obtainUriResult(Intent data) {
        return data.getParcelableArrayListExtra(EXTRA_RESULT_SELECTION);
    }

    /** 获取返回值 string path */
    public static List<String> obtainPathResult(Intent data) {
        return data.getStringArrayListExtra(EXTRA_RESULT_SELECTION_PATH);
    }
     ```
