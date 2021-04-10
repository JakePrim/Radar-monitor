package com.prim.summer_ui.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.prim.base_lib.utils.DisplayUtils
import com.prim.summer_ui.R

/**
 * @desc
 * @author sufulu
 * @time 4/7/21 - 4:57 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
class KtCircleIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), BannerIndicator<FrameLayout> {

    //静态属性和静态方法
    companion object {
        private const val VWS = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /**
     * 正常下的指示点
     */
    @DrawableRes
    private val mPointNormal = R.drawable.shape_point_normal

    /**
     * 选中的指示点
     */
    @DrawableRes
    private val mPointSelect = R.drawable.shape_point_select;

    /**
     * 指示点的左右内间距
     */
    private var mPointLeftRightPadding: Int = 0

    /**
     * 指示点的上下内间距
     */
    private var mPointTopBottomPadding: Int = 0

    init {
        //初始化
        mPointLeftRightPadding = DisplayUtils.dp2px(5f, getContext().resources)
        mPointTopBottomPadding = DisplayUtils.dp2px(15f, getContext().resources)
    }

    override fun get(): FrameLayout {
        return this
    }

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0) {
            return
        }
        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL
        var imageView: ImageView
        val params = LinearLayout.LayoutParams(VWS, VWS)
        params.gravity = Gravity.CENTER_VERTICAL
        params.setMargins(
            mPointLeftRightPadding,
            mPointTopBottomPadding,
            mPointLeftRightPadding,
            mPointTopBottomPadding
        )
        for (i in 0 until count) {
            imageView = ImageView(context)
            imageView.layoutParams = params
            if (i == 0) {
                imageView.setImageResource(mPointSelect)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            groupView.addView(imageView)
        }
        val layoutParams = LayoutParams(VWS, VWS)
        layoutParams.gravity = Gravity.CENTER or Gravity.BOTTOM
        addView(groupView, layoutParams)
    }

    override fun onPointChange(current: Int, count: Int) {
        //获取到groupView
        val group = getChildAt(0) as ViewGroup //as 转换为ViewGroup
        //获取子view
        //获取子view
        for (i in 0 until group.childCount) {
            val imageView = group.getChildAt(i) as ImageView
            if (i == current) {
                //选中的指示点
                imageView.setImageResource(mPointSelect)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            imageView.requestLayout()
        }
    }

}