package com.fbe.lib_dataitem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


/**
 * 单类型的adapter封装
 */
abstract class BaseDataBindingAdapter<M : Any, B : ViewDataBinding>(
    val context: Context,
    diffCallback: DiffUtil.ItemCallback<M> = object : DiffUtil.ItemCallback<M>() {
        override fun areItemsTheSame(oldItem: M, newItem: M): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: M, newItem: M): Boolean {
            return false
        }
    }
) :
    ListAdapter<M, RecyclerView.ViewHolder>(diffCallback) {
    protected var mOnItemClickListener: OnItemClickListener<M>? = null
    protected var mOnItemLongClickListener: OnItemLongClickListener<M>? = null

    open fun setOnItemClickListener(onItemClickListener: OnItemClickListener<M>?) {
        mOnItemClickListener = onItemClickListener
    }

    open fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<M>?) {
        mOnItemLongClickListener = onItemLongClickListener
    }

    interface OnItemClickListener<M> {
        fun onItemClick(viewId: Int, item: M, position: Int)
    }

    interface OnItemLongClickListener<M> {
        fun onItemLongClick(viewId: Int, item: M, position: Int)
    }

    override fun submitList(list: List<M>?) {
        super.submitList(list) {
            super.submitList(
                list?.let { ArrayList(it) } ?: ArrayList()
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            getLayoutResId(viewType), parent, false
        )
        val holder = BaseBindingViewHolder(binding.root)
        holder.itemView.setOnClickListener { v: View? ->
            if (mOnItemClickListener != null) {
                val position = holder.bindingAdapterPosition
                mOnItemClickListener!!.onItemClick(holder.itemView.id, getItem(position), position)
            }
        }
        holder.itemView.setOnLongClickListener { v: View? ->
            if (mOnItemLongClickListener != null) {
                val position = holder.bindingAdapterPosition
                mOnItemLongClickListener!!.onItemLongClick(
                    holder.itemView.id,
                    getItem(position),
                    position
                )
                return@setOnLongClickListener true
            }
            false
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding: B? = DataBindingUtil.getBinding(holder.itemView)
        binding?.let {
            onBindItem(binding, getItem(position), holder)
            binding.executePendingBindings()
        }
    }

    @LayoutRes
    protected abstract fun getLayoutResId(viewType: Int): Int

    /**
     * 注意：
     * RecyclerView 中的数据有位置改变（比如删除）时一般不会重新调用 onBindViewHolder() 方法，除非这个元素不可用。
     * 为了实时获取元素的位置，我们通过 ViewHolder.getBindingAdapterPosition() 方法。
     *
     * @param binding .
     * @param item    .
     * @param holder  .
     */
    protected abstract fun onBindItem(binding: B, item: M, holder: RecyclerView.ViewHolder?)

    class BaseBindingViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}