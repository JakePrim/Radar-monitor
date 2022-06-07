package com.fbe.lib_dataitem

import android.content.Context
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

/**
 * @author sufulu
 * DataBinding的Adapter基类，该类适用于多种类型的item，单类型item不适用,
 * 单类型若需要被多类型复用可以使用MultipleAdapter
 * 单类型只是在某个类中使用BaseDataBindingAdapter
 */
open class DataBindingMultipleAdapter(
    val context: Context,
    val lifecycleOwner: LifecycleOwner? = null
) :
    RecyclerView.Adapter<ViewHolder>() {

    // 布局指示器
    private val mInflater by lazy {
        LayoutInflater.from(context)
    }
    private var recyclerViewRef: WeakReference<RecyclerView>? = null
    private var dataSets = ArrayList<DataItem<*, *, out ViewHolder>>()

    //private var typeArrays = SparseArray<HiDataItem<*, out ViewHolder>>()
    private val typePositions = SparseIntArray();
    private var headers = SparseArray<View>()
    private var footers = SparseArray<View>()
    private var BASE_ITEM_TYPE_HEADER = 1000000
    private var BASE_ITEM_TYPE_FOOTER = 2000000

    private var provider: ViewModelProvider? = null

    private var applicationProvider: ViewModelProvider? = null

    /**
     * 设置全局作用域的ViewModelScope，用于组件通信
     */
    fun setApplicationViewModelProvider(provider: ViewModelProvider) {
        this.applicationProvider = provider
    }

    /**
     * 设置ViewModelScope用于item类型的ViewModel的生命周期和
     * 调用Adapter的Activity/Fragment生命周期相同,同时可以用于item之间的通信
     */
    fun setViewModelProvider(provider: ViewModelProvider) {
        this.provider = provider
    }

    fun getViewModelProvider(): ViewModelProvider? {
        return this.provider
    }

    fun getApplicationViewModelProvider(): ViewModelProvider? {
        return this.applicationProvider
    }

    override fun getItemId(position: Int): Long {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return super.getItemId(position)
        }
        return dataSets[position].getItemId() ?: super.getItemId(position)
    }

    fun addHeaderView(view: View) {
        //没有添加过
        if (headers.indexOfValue(view) < 0) {
            //2
            headers.put(BASE_ITEM_TYPE_HEADER++, view)
            notifyItemInserted(headers.size() - 1)
        }
    }

    fun removeHeaderView(view: View) {
        val indexOfValue = headers.indexOfValue(view)
        if (indexOfValue < 0) return
        headers.removeAt(indexOfValue)
        notifyItemRemoved(indexOfValue)
    }

    fun addFooterView(view: View) {
        //说明这个fgooterview 没有添加过
        if (footers.indexOfValue(view) < 0) {
            footers.put(BASE_ITEM_TYPE_FOOTER++, view)
            notifyItemInserted(itemCount)
        }
    }

    fun removeFooterView(view: View) {
        //0 1  2
        val indexOfValue = footers.indexOfValue(view)
        if (indexOfValue < 0) return
        footers.removeAt(indexOfValue)
        //position代表的是在列表中分位置
        notifyItemRemoved(indexOfValue + getHeaderSize() + getOriginalItemSize())
    }

    fun getHeaderSize(): Int {
        return headers.size()
    }

    fun getFooterSize(): Int {
        return footers.size()
    }

    fun getOriginalItemSize(): Int {
        return dataSets.size
    }

    /**
     *在指定为上添加HiDataItem
     */
    fun addItemAt(
        index: Int,
        dataItem: DataItem<*, *, out ViewHolder>,
        notify: Boolean = true
    ) {
        if (index >= 0) {
            dataSets.add(index, dataItem)
        } else {
            dataSets.add(dataItem)
        }

        val notifyPos = if (index >= 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }

        dataItem.setAdapter(this)
    }

    fun setItems(items: List<DataItem<*, *, out ViewHolder>>) {
        dataSets.clear()
        items.forEach { dataItem ->
            dataSets.add(dataItem)
            dataItem.setAdapter(this)
        }
        notifyDataSetChanged()
    }

    /**
     * 往现有集合的尾部逐年items集合
     */
    fun addItems(items: List<DataItem<*, *, out ViewHolder>>, notify: Boolean = true) {
        if (items.isNullOrEmpty()) return
        val start = dataSets.size
        items.forEach { dataItem ->
            dataSets.add(dataItem)
            dataItem.setAdapter(this)
        }
        if (notify) {
            //如果从0开始添加数据，调用notifyItemRangeInserted 会自动滚动到底部
            if (start == 0) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeInserted(start, items.size)
            }
        }
    }

    fun addItem(item: DataItem<*, *, out ViewHolder>) {
        val start = dataSets.size
        dataSets.add(item)
        item.setAdapter(this)
        notifyItemRangeInserted(start, 1)
    }

    /**
     * 从指定位置上移除item
     */
    fun removeItemAt(index: Int): DataItem<*, *, out ViewHolder>? {
        if (index >= 0 && index < dataSets.size) {
            val remove: DataItem<*, *, out ViewHolder> = dataSets.removeAt(index)
            notifyItemRemoved(index)
            return remove
        } else {
            return null
        }
    }

    /**
     * 获取列表的数据
     */
    fun getData(): List<DataItem<*, *, out ViewHolder>> {
        return dataSets
    }

    /**
     * 移除多个item集合
     * @param start 从哪里开始
     * @param items 要移除的集合个数
     * 5 1 4
     * 5-1 4
     */
    fun removeItems(start: Int, size: Int) {
        if (start >= dataSets.size) return
        val dSize = dataSets.size - start
        if (size > dSize) return
        val iterator = dataSets.iterator()
        var i = 0
        var s = 0
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (i < start) {
                i++
            } else {
                if (s < size) {
                    iterator.remove()
                    s++
                }
            }
        }
        notifyItemRangeRemoved(start, size)
    }

    /**
     * 移除指定item
     */
    fun removeItem(dataItem: DataItem<*, *, out ViewHolder>) {
        val index: Int = dataSets.indexOf(dataItem)
        removeItemAt(index)
    }

    /**
     * 指定刷新 某个item的数据
     */
    fun refreshItem(dataItem: DataItem<*, *, out ViewHolder>) {
        val indexOf = dataSets.indexOf(dataItem)
        notifyItemChanged(indexOf)
    }

    /**
     * 局部刷新某个item的数据
     */
    fun refreshItem(dataItem: DataItem<*, *, out ViewHolder>, payload: Any) {
        val indexOf = dataSets.indexOf(dataItem)
        notifyItemChanged(indexOf, payload)
    }

    /**
     * 以每种item类型的class.hashcode为 该item的viewType
     *
     * 这里把type存储起来，是为了onCreateViewHolder方法能够为不同类型的item创建不同的viewholder
     */
    override fun getItemViewType(position: Int): Int {
        if (isHeaderPosition(position)) {
            return headers.keyAt(position)
        }
        if (isFooterPosition(position)) {
            //footer的位置 应该计算一下  position =6 , headercount =1  itemcoun=5 =,footersize=1
            val footerPosition = position - getHeaderSize() - getOriginalItemSize()
            return footers.keyAt(footerPosition)
        }

        val itemPosition = position - getHeaderSize()
        val dataItem = dataSets[itemPosition]
        val type = dataItem.javaClass.hashCode()

        //按照原来的写法 相同的viewType仅仅只在第一次，会把viewType和dataItem关联
//        if (typeArrays.indexOfKey(type) < 0) {
//            typeArrays.put(type, dataItem)
//        }
        typePositions.put(type, position)
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (headers.indexOfKey(viewType) >= 0) {
            val view = headers[viewType]
            return object : RecyclerView.ViewHolder(view) {}
        }
        if (footers.indexOfKey(viewType) >= 0) {
            val view = footers[viewType]
            return object : RecyclerView.ViewHolder(view) {}
        }

        //这会导致不同position，但viewType相同，获取到的dataItem始终是第一次关联的dataItem对象。
        //这就会导致通过getItemView创建的成员变量，只在第一个dataItem中，其它实例中无法生效

        //为了解决dataItem成员变量binding, 刷新之后无法被复用的问题
        val position = typePositions.get(viewType)
        val dataItem = dataSets[position]
        val vh = dataItem.onCreateViewHolder(parent)
        if (vh != null) return vh

        var view: View? = dataItem.getItemView(parent, lifecycleOwner)
        if (view == null) {
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                throw RuntimeException("dataItem:" + dataItem.javaClass.name + " must override getItemView or getItemLayoutRes")
            }
            //通过DataBinding绑定布局失败
            view = mInflater.inflate(layoutRes, parent, false)
        }

        return createViewHolderInternal(dataItem.javaClass, view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (isHeaderPosition(position) || isFooterPosition(position)) return
        val itemPosition = position - getHeaderSize()
        val dataItem = getItem(itemPosition)
        dataItem?.onBindViewHolder(holder, itemPosition)
    }

    /**
     * 局部刷新
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (isHeaderPosition(position) || isFooterPosition(position)) return

        val itemPosition = position - getHeaderSize()
        val dataItem = getItem(itemPosition)
        if (payloads.isNullOrEmpty()) {
            dataItem?.onBindViewHolder(holder, itemPosition)
        } else {
            dataItem?.onBindViewHolder(holder, itemPosition, payloads)
        }
    }

    private fun createViewHolderInternal(
        javaClass: Class<DataItem<*, *, out ViewHolder>>,
        view: View
    ): ViewHolder {
        //得到该Item的父类类型,即为HiDataItem.class。  class 也是type的一个子类。
        //type的子类常见的有 class，类泛型,ParameterizedType参数泛型 ，TypeVariable字段泛型
        //所以进一步判断它是不是参数泛型
        val superclass = javaClass.genericSuperclass
        if (superclass is ParameterizedType) {
            //得到它携带的泛型参数的数组
            val arguments = superclass.actualTypeArguments
            //挨个遍历判断 是不是想要的 RecyclerView.ViewHolder 子类 类型的。
            for (argument in arguments) {
                if (argument is Class<*> && ViewHolder::class.java.isAssignableFrom(argument)) {
                    try {
                        //如果是，则使用反射 实例化类上标记的实际的泛型对象
                        //这里需要  try-catch 一把
                        val viewHolder = argument.getConstructor(View::class.java)
                            .newInstance(view) as ViewHolder
                        return viewHolder
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return object : BaseDataBindingViewHolder(view) {}
    }

    private fun isFooterPosition(position: Int): Boolean {
        // 10->  4+ 4.
        return position >= getHeaderSize() + getOriginalItemSize()
    }

    private fun isHeaderPosition(position: Int): Boolean {
        // 5 --> 4 3 2 1
        return position < headers.size()
    }

    override fun getItemCount(): Int {
        return dataSets.size + getHeaderSize() + getFooterSize()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerViewRef = WeakReference(recyclerView)
        /**
         * 为列表上的item 适配网格布局
         */
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (isHeaderPosition(position) || isFooterPosition(position)) {
                        return spanCount
                    }
                    val itemPosition = position - getHeaderSize()
                    if (itemPosition < dataSets.size) {
                        val dataItem = getItem(itemPosition)
                        if (dataItem != null) {
                            val spanSize = dataItem.getSpanSize()
                            return if (spanSize <= 0) spanCount else spanSize
                        }
                    }
                    return spanCount
                }
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerViewRef?.clear()
    }

    open fun getAttachRecyclerView(): RecyclerView? {
        return recyclerViewRef?.get()
    }

    fun getItem(position: Int): DataItem<*, *, ViewHolder>? {
        if (position < 0 || position >= dataSets.size)
            return null
        return dataSets[position] as DataItem<*, *, ViewHolder>
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        val recyclerView = getAttachRecyclerView()
        if (recyclerView != null) {
            //瀑布流的item占比适配
            val position = recyclerView.getChildAdapterPosition(holder.itemView)
            val isHeaderFooter = isHeaderPosition(position) || isFooterPosition(position)
            val itemPosition = position - getHeaderSize()
            val dataItem = getItem(itemPosition) ?: return
            val lp = holder.itemView.layoutParams
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                if (isHeaderFooter) {
                    lp.isFullSpan = true
                    return
                }
                val spanSize = dataItem.getSpanSize()
                if (spanSize == manager!!.spanCount) {
                    lp.isFullSpan = true
                }
            }
            dataItem.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        val position = holder.adapterPosition
        if (isHeaderPosition(position) || isFooterPosition(position))
            return
        val itemPosition = position - getHeaderSize()
        val dataItem = getItem(itemPosition) ?: return
        dataItem.onViewDetachedFromWindow(holder)
    }

    fun clearItems() {
        dataSets.clear()
        notifyDataSetChanged()
    }
}