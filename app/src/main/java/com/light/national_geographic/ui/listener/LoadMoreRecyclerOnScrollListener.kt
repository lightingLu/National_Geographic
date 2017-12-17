package com.light.national_geographic.ui.listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * 创建日期：2017/12/17 on 23:23
 * @author ludaguang
 * @version 1.0
 * 类说明：
 */
abstract class LoadMoreRecyclerOnScrollListener(private val mLayoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var isLoading: Boolean = false
    private var visibleThreshold = 1
    internal var fistVisableItem: Int = 0
    internal var visableItemCount: Int = 0
    internal var totalItemCount: Int = 0

    private var currentPage = 1
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visableItemCount = recyclerView!!.childCount
        if (mLayoutManager is LinearLayoutManager) {
            totalItemCount = mLayoutManager.itemCount
            fistVisableItem = mLayoutManager.findFirstVisibleItemPosition()

        }
        if (mLayoutManager is StaggeredGridLayoutManager) {
            totalItemCount = mLayoutManager.itemCount
            var lastPosition = mLayoutManager.findLastVisibleItemPositions(kotlin.IntArray(mLayoutManager.spanCount))
            fistVisableItem = getMinPosition(lastPosition)
        }
        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false
                previousTotal = totalItemCount
            }
        }
        if (!isLoading && totalItemCount > visableItemCount && totalItemCount - visableItemCount <= fistVisableItem + visibleThreshold) {
            currentPage++
            onLoadMore(currentPage)
            isLoading = true
        }

    }

    private fun getMinPosition(lastPosition: IntArray): Int {
        var size = lastPosition.size
        var minPosition = Integer.MAX_VALUE
        for (i in 0..size - 1) {
            minPosition = Math.min(minPosition, lastPosition[i])

        }
        return minPosition

    }

    abstract fun onLoadMore(currentPage: Int)

}