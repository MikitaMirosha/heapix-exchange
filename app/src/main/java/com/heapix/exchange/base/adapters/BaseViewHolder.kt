package com.heapix.exchange.base.adapters

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.heapix.exchange.MyApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.extensions.LayoutContainer
import org.kodein.di.instance

abstract class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    private val compositeDisposable: CompositeDisposable by MyApp.kodein.instance()

    abstract fun bind(model: T)

    override val containerView: View?
        get() = itemView

    val context: Context
        get() = itemView.context

    fun setOnClickListener(block: () -> Unit) {
        itemView.setOnClickListener {
            block()
        }
    }

    open fun recycle() = compositeDisposable.clear()

    fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    fun getColor(@ColorRes color: Int) = ContextCompat.getColor(itemView.context, color)
}