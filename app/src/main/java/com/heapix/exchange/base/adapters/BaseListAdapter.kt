package com.heapix.exchange.base.adapters

import androidx.recyclerview.widget.RecyclerView
import com.heapix.exchange.MyApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.kodein.di.instance

abstract class BaseListAdapter<T>(
    proposedItems: List<T> = ArrayList(),
    protected val listener: OnListItemClickListener<T>? = null,
    private val colourAlternateItems: Boolean = false
) : RecyclerView.Adapter<BaseViewHolder<T>>() {
    protected val items: ArrayList<T> = ArrayList(proposedItems)

    private val compositeDisposable: CompositeDisposable by MyApp.kodein.instance()

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        if (listener != null) {
            holder.setOnClickListener { listener.onItemClick(item, holder.itemView) }
        }
//        if (colourAlternateItems) {
//            holder.setPositionInList(position)
//        }
    }

    override fun onViewRecycled(holder: BaseViewHolder<T>) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    override fun getItemCount(): Int = items.size

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.indexOf(item))
    }

    fun removeItem(item: T) {
        notifyItemRemoved(items.indexOf(item))
        items.remove(item)
    }

    fun removeItem(position: Int): T {
        val item = items.removeAt(position)
        notifyItemRemoved(position)
        return item
    }

    open fun addItem(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val item = items.removeAt(fromPosition)
        if (toPosition >= items.size) {
            items.add(item)
        } else {
            items.add(toPosition, item)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun setItemsWithAnimation(newItems: List<T>) {
        val itemsToSet = newItems
        applyAndAnimateRemovals(itemsToSet)
        applyAndAnimateAdditions(itemsToSet)
        applyAndAnimateMovedItems(itemsToSet)
        notifyItemRangeChanged(0, itemsToSet.size)
    }

    open fun setItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getItemsList() = items

    open fun getItem(position: Int): T = items[position]

    protected fun getPosition(item: T): Int {
        return if (items.size > 0) {
            items.indexOf(item)
        } else -1
    }

    private fun applyAndAnimateRemovals(newItems: List<T>) {
        for (i in items.size - 1 downTo 0) {
            val item = items[i]
            if (!newItems.contains(item)) {
                removeItem(i)
            }
        }
    }

    private fun applyAndAnimateAdditions(newItems: List<T>) {
        var i = 0
        val count = newItems.size
        while (i < count) {
            val item = newItems[i]
            if (!items.contains(item)) {
                addItem(i, item)
            }
            i++
        }
    }

    private fun applyAndAnimateMovedItems(newItems: List<T>) {
        for (toPosition in newItems.indices.reversed()) {
            val model = newItems[toPosition]
            val fromPosition = items.indexOf(model)
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition)
            }
        }
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}