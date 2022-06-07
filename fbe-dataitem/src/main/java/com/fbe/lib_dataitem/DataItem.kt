package com.fbe.lib_dataitem

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.fbe.lib_dataitem.store.GlobalScopeViewModel
import com.fbe.lib_dataitem.store.ItemScopeViewModelStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 将列表的item进行抽离,可实现任意item类型组合使用，
 * 一种类型对应一个DataItem的实现类
 * 一种类型的item 对应一个DataBinding和一个ViewHolder
 */
abstract class DataItem<B : ViewDataBinding, DATA, VH : RecyclerView.ViewHolder>(var data: DATA? = null) {

    private var adapter: DataBindingMultipleAdapter? = null

    /**
     * 上下文环境的provider
     */
    private var mContextViewModelProvider: ViewModelProvider? =
        null //如果adapter没有设置默认使用自定义的ViewModelStore

    /**
     * application级别的provider
     */
    private var mApplicationViewModelProvider: ViewModelProvider? = null

    fun setScopeViewModelProvider(provider: ViewModelProvider) {
        this.mContextViewModelProvider = provider
    }

    protected lateinit var mBinding: B

    protected lateinit var mContext: Context

    fun onBindViewHolder(holder: VH, position: Int) {
        val binding = DataBindingUtil.getBinding<B>(holder.itemView)
        if (binding != null) {
            mBinding = binding
            mContext = binding.root.context
            binding.root.setOnClickListener { onItemClick() }
            binding.root.setOnLongClickListener { onLongItemClick() }
        }
        Log.e("TAG", "onBindViewHolder: 3")
        onBindData(binding, holder, position)
        binding?.executePendingBindings()
    }

    fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        val binding = DataBindingUtil.getBinding<B>(holder.itemView)
        if (binding != null) {
            mBinding = binding
            mContext = binding.root.context
            binding.root.setOnClickListener { onItemClick() }
            binding.root.setOnLongClickListener { onLongItemClick() }
        }
        onBindData(binding, holder, position, payloads)
        binding?.executePendingBindings()
    }

    open fun onLongItemClick(): Boolean {
        return false
    }

    /**
     * item绑定数据
     */
    abstract fun onBindData(binding: B?, holder: VH, position: Int)

    /**
     * 如果要实现局部刷新 需要覆盖重写该方法
     */
    open fun onBindData(
        binding: B?,
        holder: VH,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindData(binding, holder, position)
    }

    abstract fun getItemLayoutRes(): Int

    abstract fun getItemId(): Long?

    /**
     *返回该item的视图view
     */
    open fun getItemView(parent: ViewGroup, lifecycleOwner: LifecycleOwner?): View? {
        val layoutRes = getItemLayoutRes()
        if (layoutRes < 0) {
            throw RuntimeException("dataItem:" + javaClass.name + " must override getItemLayoutRes")
        }
        //通过DataBinding绑定布局
        val binding = DataBindingUtil.inflate<B>(
            LayoutInflater.from(parent.context),
            layoutRes,
            parent,
            false
        )
        binding.lifecycleOwner = lifecycleOwner
        this.mBinding = binding
        this.mContext = parent.context
        return binding.root
    }

    open fun onItemClick() {}

    fun setAdapter(adapter: DataBindingMultipleAdapter) {
        this.adapter = adapter
        this.mContextViewModelProvider =
            adapter.getViewModelProvider() ?: ViewModelProvider(ItemScopeViewModelStore())
        this.mApplicationViewModelProvider =
            adapter.getApplicationViewModelProvider() ?: ViewModelProvider(
                GlobalScopeViewModel.getInstance()
            )
    }

    /**
     * 刷新列表
     */
    fun refreshItem() {
        if (adapter != null) adapter!!.refreshItem(this)
    }

    /**
     * 从列表上移除
     */
    fun removeItem() {
        if (adapter != null) adapter!!.removeItem(this)
    }

    /**
     * 该item在列表上占几列,代表的宽度是沾满屏幕
     */
    open fun getSpanSize(): Int {
        return 0
    }

    /**
     * 该item被滑进屏幕
     */
    open fun onViewAttachedToWindow(holder: VH) {

    }

    /**
     * 该item被滑出屏幕
     */
    open fun onViewDetachedFromWindow(holder: VH) {

    }

    /**
     * item自己实现onCreateViewHolder
     */
    open fun onCreateViewHolder(parent: ViewGroup): VH? {
        return null
    }

    /**
     * 获取上下文环境的ViewModel：Fragment/Activity
     */
    fun <T : ViewModel> getContextScopeViewModel(modelClass: Class<T>): T {
        return mContextViewModelProvider!!.get(modelClass)
    }

    /**
     * 获取应用级别的ViewModel
     */
    fun <VM : ViewModel> getApplicationScopeViewModel(modelClass: Class<VM>): VM {
        return mApplicationViewModelProvider!!.get(modelClass)
    }

    /**
     * 在协程中执行 函数
     */
    protected fun launch(runnable: suspend CoroutineScope.() -> Unit) {
        mBinding.lifecycleOwner?.let {
            it.lifecycleScope.launch {
                runnable.invoke(this)
            }
        }
    }
}