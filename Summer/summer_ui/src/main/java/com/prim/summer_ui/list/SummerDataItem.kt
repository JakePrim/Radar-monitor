package com.prim.summer_ui.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @desc 列表的item的抽象类
 * @author sufulu
 * @time 4/24/21 - 9:22 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
abstract class SummerDataItem<DATA, VH : RecyclerView.ViewHolder>(data: DATA) {
    var mData: DATA? = null

    /**
     * 延迟初始化
     */
    private lateinit var adapter: SummerAdapter

    init {
        this.mData = data
    }

    /**
     * 绑定数据
     */
    abstract fun onBindData(holder: RecyclerView.ViewHolder, position: Int)

    /**
     * 定义item的类型
     */
    abstract fun viewType(): Int

    /**
     * 获取item对应的布局的id
     */
    open fun getItemLayoutRes(): Int {
        return -1
    }

    /**
     * 不写布局文件，自己取new View实现,返回item的视图view
     */
    open fun getItemView(parent: ViewGroup): View? {
        return null
    }

    fun setAdapter(adapter: SummerAdapter) {
        this.adapter = adapter
    }

    /**
     * 刷新列表
     */
    fun refreshItem() {
        adapter.refreshItem(this)
    }

    /**
     * 从列表上移除
     */
    fun removeItem() {
        adapter.removeItem(this)
    }

    /**
     * 列表spanSize的设置
     */
    open fun spanSize(): Int {
        return 0
    }
}