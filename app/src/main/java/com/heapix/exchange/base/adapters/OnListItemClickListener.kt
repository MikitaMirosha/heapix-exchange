package com.heapix.exchange.base.adapters

import android.view.View

interface OnListItemClickListener<in T> {
    fun onItemClick(item: T, view: View)
}