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

/**
 * Base class for all view holders to be used in concrete implementations of [BaseListAdapter]
 */
/**
 * Base class for all view holders to be used in concrete implementations of [BaseListAdapter]
 */
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

//    fun setSafeOnClickListener(block: () -> Unit) {
//        itemView.setSafeOnClickListener {
//            block()
//        }
//    }

//    fun image(path: String?): String? {
//        if (path == null)
//            return null
//
//        return BuildConfig.IMAGES_PATH + path
//    }

//    fun setPositionInList(position: Int) {
//        itemView.setBackgroundColor(
//            if ((position % 2) == 0) Color.WHITE else ContextCompat.getColor(
//                itemView.context,
//                R.color.grey
//            )
//        )
//    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    /**
     * Override this method to cancel any behaviour you don't want to continue after the view has been recycled
     * This is most likely async behaviour
     */
    open fun recycle() {
        compositeDisposable.clear()
    }

    fun getColor(@ColorRes color: Int) = ContextCompat.getColor(itemView.context, color)
}