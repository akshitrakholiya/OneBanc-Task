package com.akshit.onebanc.utilities

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        if (!isLoading() && !isLastPage() && lastVisibleItemPosition >= totalItemCount - 2) {
            loadMoreItems()
        }
    }
}