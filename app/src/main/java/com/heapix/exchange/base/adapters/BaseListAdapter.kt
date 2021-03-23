package com.heapix.exchange.base.adapters

import androidx.recyclerview.widget.RecyclerView
import com.heapix.exchange.MyApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.kodein.di.instance

abstract class BaseListAdapter<T>(
    proposedItems: List<T> = ArrayList(),
    private val listener: OnListItemClickListener<T>? = null,
    private val colourAlternateItems: Boolean = false
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val compositeDisposable: CompositeDisposable by MyApp.kodein.instance()

    private val items: ArrayList<T> = ArrayList(proposedItems)

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        if (listener != null) {
            holder.setOnClickListener { listener.onItemClick(item, holder.itemView) }
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder<T>) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    override fun getItemCount(): Int = items.size

    private fun removeItem(position: Int): T {
        val item = items.removeAt(position)
        notifyItemRemoved(position)
        return item
    }

    private fun moveItem(fromPosition: Int, toPosition: Int) {
        val item = items.removeAt(fromPosition)
        if (toPosition >= items.size) {
            items.add(item)
        } else {
            items.add(toPosition, item)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    open fun setItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getItemsList() = items

    open fun getItem(position: Int): T = items[position]

    fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)
}