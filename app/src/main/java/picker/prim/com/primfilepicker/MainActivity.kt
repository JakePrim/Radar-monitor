package picker.prim.com.primfilepicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import picker.prim.com.primpicker_core.PrimPicker
import picker.prim.com.primpicker_core.entity.MimeType

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_video.setOnClickListener({
            goVideo()
        })

        btn_img.setOnClickListener({
            goImg()
        })

        btn_all.setOnClickListener({
            goAll()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val str = StringBuffer()
            str.append("返回结果:")
            for (uri in PrimPicker.obtainUriResult(data)) {
                str.append(uri).append("\n")
            }

            if (PrimPicker.obtainCompressResult(data)) {
                str.append("压缩视频")
            } else {
                str.append("不要压缩视频")
            }
            result.text = str.toString()
        }
    }

    fun goImg() {
        var span = et_spanCount.text.toString()
        var max = et_maxCount.text.toString()
        if (TextUtils.isEmpty(span)) {
            span = "3"
        }

        if (TextUtils.isEmpty(max)) {
            max = "1"
        }
        PrimPicker
                .with(this)
                .choose(MimeType.ofImage())
                .setSpanCount(Integer.parseInt(span))
                .setMaxSelected(Integer.parseInt(max))
                .showSingleMediaType(true)
                .forResult(1001)
    }

    fun goVideo() {
        var span = et_spanCount.text.toString()
        var max = et_maxCount.text.toString()
        if (TextUtils.isEmpty(span)) {
            span = "3"
        }

        if (TextUtils.isEmpty(max)) {
            max = "1"
        }
        PrimPicker
                .with(this)
                .choose(MimeType.ofVideo())
                .setSpanCount(Integer.parseInt(span))
                .setMaxSelected(Integer.parseInt(max))
                .showSingleMediaType(true)
                .forResult(1001)
    }

    fun goAll() {
        var span = et_spanCount.text.toString()
        var max = et_maxCount.text.toString()
        if (TextUtils.isEmpty(span)) {
            span = "3"
        }

        if (TextUtils.isEmpty(max)) {
            max = "1"
        }
        PrimPicker
                .with(this)
                .choose(MimeType.ofAll())
                .setSpanCount(Integer.parseInt(span))
                .setMaxSelected(Integer.parseInt(max))
                .showSingleMediaType(true)
                .forResult(1001)
    }
}
