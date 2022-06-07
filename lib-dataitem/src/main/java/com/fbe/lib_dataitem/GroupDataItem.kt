package com.fbe.lib_dataitem

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * GroupDataItem 是组合DataItem 可以是多个DataItem共用一个data，组合成一个大的DataItem
 * 多个DataItem组合，更加灵活最大化复用。
 */
abstract class GroupDataItem<B : ViewDataBinding, DATA, VH : RecyclerView.ViewHolder>(data: DATA) :
    DataItem<B, DATA, VH>(data) {

}