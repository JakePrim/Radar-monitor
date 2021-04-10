package com.prim.honorkings.demo.banner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.bumptech.glide.Glide
import com.prim.honorkings.R
import com.prim.summer_ui.banner.core.BannerMo
import com.prim.summer_ui.banner.core.SummerBanner
import com.prim.summer_ui.banner.indicator.BannerIndicator
import com.prim.summer_ui.banner.indicator.CircleIndicator
import com.prim.summer_ui.banner.indicator.KtCircleIndicator
import com.prim.summer_ui.banner.indicator.NumberIndicator

class BannerActivity : AppCompatActivity() {
    private var urls = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )

    private var autoPlay: Boolean = false

    private var indicator: BannerIndicator<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)
        initView(KtCircleIndicator(this), false)
        val change = findViewById<TextView>(R.id.change)
        val auto_play: Switch = findViewById<Switch>(R.id.auto_play)
        auto_play.setOnCheckedChangeListener { _, isChecked ->
            autoPlay = isChecked
            initView(indicator, autoPlay)
        }
        change.setOnClickListener {
            if (indicator is CircleIndicator) {
                initView(NumberIndicator(this), autoPlay)
            } else {
                initView(KtCircleIndicator(this), autoPlay)
            }
        }
    }

    private fun initView(indicator: BannerIndicator<*>?, autoPlay: Boolean) {
        this.indicator = indicator
        val banner = findViewById<SummerBanner>(R.id.banner)

        var moList: MutableList<BannerMo> = ArrayList()
        for (i in urls.indices) {
            val mo = com.prim.honorkings.demo.banner.BannerMo()
            mo.url = urls[i % urls.size]
            moList.add(mo)
        }
        //添加指示器
        banner.setIndicator(indicator)
        banner.setAutoPlay(autoPlay)
        banner.setIntervalTime(2000)
        //自定义布局
        banner.setBannerData(R.layout.banner_item_layout, moList)
        //数据绑定与框架层解耦
        banner.setBannerAdapter { viewHolder, bannerMo, position ->
            val image: ImageView = viewHolder.findViewById(R.id.iv_image)
            val title: TextView = viewHolder.findViewById(R.id.tv_title)
            Glide.with(this@BannerActivity).load(bannerMo.url).into(image)
            title.text = bannerMo.url
        }

    }
}