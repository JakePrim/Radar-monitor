package com.prim.summer_ui.list

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException
import java.lang.reflect.ParameterizedType

/**
 * @desc adapter的封装类
 * @author sufulu
 * @time 4/24/21 - 9:29 PM
 * @contact sufululove@gmail.com
 * @name Summer
 * @version 1.0.0
 */
class SummerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * 布局加载器
     */
    private var mInflater: LayoutInflater? = null

    /**
     * 数据源
     */
    private var dataSets = ArrayList<SummerDataItem<*, RecyclerView.ViewHolder>>()

    /**
     * item类型
     */
    private var typeArrays = SparseArray<SummerDataItem<*, RecyclerView.ViewHolder>>()

    init {
        this.mInflater = LayoutInflater.from(context)
    }

    /**
     * 添加item
     */
    fun addItem(index: Int, item: SummerDataItem<*, RecyclerView.ViewHolder>, notify: Boolean) {
        if (index > 0) {
            dataSets.add(index, item)
        } else {
            dataSets.add(item)
        }
        //得到要更新的位置
        val notifyPos = if (index > 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }
    }

    /**
     * 向列表添加集合
     */
    fun addItems(items: List<SummerDataItem<*, RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = dataSets.size
        dataSets.addAll(items)
        if (notify) {
            notifyItemRangeChanged(start, items.size)
        }
    }

    /**
     * 从列表移除item
     */
    fun removeItem(position: Int): SummerDataItem<*, RecyclerView.ViewHolder>? {
        if (position > 0 && position < dataSets.size) {
            val remove = dataSets.removeAt(position)
            notifyItemRemoved(position)
            return remove
        }
        return null
    }

    fun removeItem(item: SummerDataItem<*, *>) {
        if (dataSets.contains(item)) {
            val index = dataSets.indexOf(item)
            removeItem(index)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val dataItem = dataSets[position]
        var type = dataItem.viewType()
        if (type == 0) {
            //表示没用设置viewType的类型值 默认获取class的hashCode作为类型值
            type = dataItem.javaClass.hashCode()
        }
        //如果没有添加到typeArrays中存储Type 则进行添加
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //根据typeArrays获取item
        val dataItem = typeArrays[viewType]
        var view: View? = dataItem.getItemView(parent)
        if (view == null) {
            //表示没有设置getItemView 获取layoutRes
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                //layoutRes也没有设置抛出异常
                RuntimeException("dataItem:" + dataItem.javaClass.name + "must override getItemView or getItemLayoutRes")
            }
            view = mInflater!!.inflate(layoutRes, parent, false)
        }
        return createViewHolderInternal(dataItem.javaClass, view)
    }

    private fun createViewHolderInternal(
        javaClass: Class<SummerDataItem<*, RecyclerView.ViewHolder>>,
        view: View?
    ): RecyclerView.ViewHolder {
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            //获取泛型的类型参数
            val arguments = superClass.actualTypeArguments
            for (argument in arguments) {
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(
                        argument
                    )
                ) {
                    return argument.getConstructor(View::class.java)
                        .newInstance(view) as RecyclerView.ViewHolder
                }
            }
        }
        //如果泛型实例化失败则返回一个默认的ViewHolder对象
        return object : RecyclerView.ViewHolder(view!!) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataItem = dataSets[position]
        dataItem.onBindData(holder, position)
    }

    override fun getItemCount(): Int {
        return dataSets.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size) {
                        val dataItem = dataSets[position]
                        val spanSize = dataItem.spanSize()
                        return if (spanSize <= 0) spanCount else spanSize
                    }
                    return spanCount
                }
            }
        }
    }

    fun refreshItem(summerDataItem: SummerDataItem<*, *>) {
        val position = dataSets.indexOf(summerDataItem)
        if (position > 0 && position < dataSets.size - 1) {
            notifyItemChanged(position)
        }
    }

}