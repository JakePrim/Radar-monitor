package com.fbe.lib_dataitem

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * 可以实现预加载列表的RecyclerView，如果没有预加载需要不要使用
 */
open class DRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private var loadMoreScrollListener: OnScrollListener? = null
    private var footerView: View? = null
    private var isLoadingMore: Boolean = false

    inner class LoadMoreScrollListener(val prefetchSize: Int, val callback: () -> Unit) :
        OnScrollListener() {
        //咱们这里的强转，因为前面 会有前置检查
        val hiAdapter = adapter as DataBindingMultipleAdapter
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            //需要根据当前的滑动状态  已决定要不要添加footer view ，要不执行上拉加载分页的动作
            Log.e(
                "DRecyclerView",
                "onScrollStateChanged: isLoadingMore:${isLoadingMore} isNoMore:${isNoMore}"
            )
            if (isLoadingMore || isNoMore) {
                return
            }
            //咱们需要判断当前类表上 已经显示的 item的个数 ，如果列表上已显示的item的数量小于0
            val totalItemCount = hiAdapter.itemCount
            if (totalItemCount <= 0)
                return

            //此时，咱们需要在滑动状态为 拖动状态时，就要判断要不要添加footer
            //目的就是为了防止列表滑动到底部了但是 footerview 还没显示出来，
            //1. 依旧需要判断列表是否能够滑动,那么问题来了，如何判断RecyclerView ，是否可以继续向下滑动
            val canScrollVertical = recyclerView.canScrollVertically(1)

            //还有一种情况,canScrollVertical 检查他能不能欧股继续向下滑动，
            //特殊情况，列表已经滑动到底部了，但是分页失败了。
            val lastVisibleItem = findLastVisibleItem(recyclerView)
            val firstVisibleItem = findFirstVisibleItem(recyclerView)
            Log.e("DRecyclerView", "onScrollStateChanged: lastVisibleItem:${lastVisibleItem}")
            if (lastVisibleItem <= 0)
                return
            //列表不可滑动,但列表没有撑满屏幕,此时lastVisibleItem就等于最后一条item,为了避免这种能情况，还需要加firstVisibleItem!=0
            val arriveBottom =
                lastVisibleItem >= totalItemCount - 1 && firstVisibleItem > 0
            //可以向下滑动，或者当前已经滑动到最底下了，此时在拖动列表，那也是允许分页的
            if (newState == SCROLL_STATE_DRAGGING && (canScrollVertical || arriveBottom)) {
                addFooterView()
            }

            //不能在 滑动停止了，才去添加footer view
            if (newState != SCROLL_STATE_IDLE) {
                return
            }

            //预加载,就是 不需要等待 滑动到最后一个item的时候，就出发下一页的加载动作
            val arrivePrefetchPosition = totalItemCount - lastVisibleItem <= prefetchSize
            Log.e(
                "DRecyclerView",
                "onScrollStateChanged: arrivePrefetchPosition：${arrivePrefetchPosition}"
            )
            if (!arrivePrefetchPosition) {
                return
            }
            Log.e("DRecyclerView", "onScrollStateChanged: 开始加载下一页")
            isLoadingMore = true
            callback()
        }

        private fun addFooterView() {
            val footerView = getFooterView()
            //但是，这里有个坑。。。在一些边界场景下。会出现多次添加的情况， 添加之前先 remove --》hiAdapter。.removeFooterView()

            //主要是为了避免 removeFooterView 不及时，在边界场景下可能会出现，footerView还没从recyclervIEW上移除掉，但我们又调用了addFooterView，
            //造成的重复添加的情况，此时会抛出 add view must call removeview form it parent first exception
            if (footerView.parent != null) {
                footerView.post {
                    addFooterView()
                }
            } else {
                hiAdapter.addFooterView(footerView)
            }
        }

        private fun getFooterView(): View {
            if (footerView == null) {
                footerView = LayoutInflater.from(context)
                    .inflate(R.layout.layout_footer_loading, this@DRecyclerView, false)

            }
            return footerView!!
        }

        private fun findLastVisibleItem(recyclerView: RecyclerView): Int {
            when (val layoutManager = recyclerView.layoutManager) {
                //layoutManager is GridLayoutManager
                is LinearLayoutManager -> {
                    return layoutManager.findLastVisibleItemPosition()
                }
                is StaggeredGridLayoutManager -> {
                    return layoutManager.findLastVisibleItemPositions(null)[0]
                }
            }
            return -1
        }

        private fun findFirstVisibleItem(recyclerView: RecyclerView): Int {
            when (val layoutManager = recyclerView.layoutManager) {
                //layoutManager is GridLayoutManager
                is LinearLayoutManager -> {
                    return layoutManager.findFirstVisibleItemPosition()
                }
                is StaggeredGridLayoutManager -> {
                    return layoutManager.findFirstVisibleItemPositions(null)[0]
                }
            }
            return -1
        }
    }

    fun enableLoadMore(callback: () -> Unit, prefetchSize: Int) {
        Log.e("DRecyclerView", "${adapter}")
        if (adapter !is DataBindingMultipleAdapter) {
            Log.e("DRecyclerView", "enableLoadMore must use DataBindingMultipleAdapter")
            return
        }

        loadMoreScrollListener = LoadMoreScrollListener(prefetchSize, callback)
        addOnScrollListener(loadMoreScrollListener!!)
    }

    fun disableLoadMore() {
        if (adapter !is DataBindingMultipleAdapter) {
            Log.e("DRecyclerView", "disableLoadMore must use BaseDataBindingAdapter")
            return
        }

        val hiAdapter = adapter as DataBindingMultipleAdapter
        footerView?.let {
            if (footerView!!.parent != null) {
                hiAdapter.removeFooterView(footerView!!)
            }
        }

        loadMoreScrollListener?.let {
            removeOnScrollListener(loadMoreScrollListener!!)
            loadMoreScrollListener = null
            footerView = null
            isLoadingMore = false
        }
    }

    fun isLoading(): Boolean {
        return isLoadingMore
    }

    // 是否有更多的数据 true表示没有更多数据
    var isNoMore = false

    /**
     * 触发刷新操作，将更多加载的状态重置
     */
    fun resetLoadStatue() {
        isNoMore = false
        footerView?.let {
            it.findViewById<View>(R.id.footer_loading).visibility = VISIBLE
            it.findViewById<TextView>(R.id.footer_loading_text).text = "正在加载..."
        }
    }

    fun loadFinished(success: Boolean) {
        if (adapter !is DataBindingMultipleAdapter) {
            Log.e("DRecyclerView", "loadFinished must use hiadapter")
            return
        }
        isLoadingMore = false
        val hiAdapter = adapter as DataBindingMultipleAdapter
        if (!success) {
            isNoMore = false
            footerView?.let {
                if (footerView!!.parent != null) {
                    hiAdapter.removeFooterView(footerView!!)
                }
            }
        } else {
            isNoMore = true
            if (footerView == null) {
                footerView = LayoutInflater.from(context)
                    .inflate(R.layout.layout_footer_loading, this@DRecyclerView, false)
                hiAdapter.addFooterView(footerView!!)
            }
            footerView?.let {
                it.findViewById<View>(R.id.footer_loading).visibility = GONE
                it.findViewById<TextView>(R.id.footer_loading_text).text = "没有更多了"
            }
        }
    }
}