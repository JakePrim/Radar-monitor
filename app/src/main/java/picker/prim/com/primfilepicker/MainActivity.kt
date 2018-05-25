package picker.prim.com.primfilepicker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import picker.prim.com.primpicker_core.PrimPicker
import picker.prim.com.primpicker_core.entity.MimeType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PrimPicker
                .with(this)
                .choose(MimeType.ofVideo())
                .setSpanCount(3)
                .setMaxSelected(4)
                .showSingleMediaType(true)
                .forResult(1001)
    }
}
