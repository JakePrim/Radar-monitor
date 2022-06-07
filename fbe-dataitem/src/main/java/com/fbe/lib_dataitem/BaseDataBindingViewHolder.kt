package com.fbe.lib_dataitem

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class BaseDataBindingViewHolder(val view: View) : RecyclerView.ViewHolder(view),
    LayoutContainer {
    override val containerView: View?
        get() = view
}