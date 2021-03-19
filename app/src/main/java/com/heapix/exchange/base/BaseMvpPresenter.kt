package com.heapix.exchange.base

import android.content.SharedPreferences
import androidx.annotation.StringRes
import com.arellomobile.mvp.MvpPresenter
import com.heapix.exchange.MyApp
import com.heapix.exchange.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.kodein.di.instance

abstract class BaseMvpPresenter<V : BaseMvpView> : MvpPresenter<V>() {

    protected val schedulers: SchedulerProvider by MyApp.kodein.instance()
    protected val preferences: SharedPreferences by MyApp.kodein.instance()
    private val compositeDisposable: CompositeDisposable by MyApp.kodein.instance()

    override fun onDestroy() {
        super.onDestroy()
        clearDisposable()
    }

    private fun clearDisposable() = compositeDisposable.clear()

    fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    fun removeDisposable(disposable: Disposable) = compositeDisposable.remove(disposable)

    protected fun showMessage(@StringRes text: Int) = viewState.showMessage(text)

    protected fun showMessage(text: String) = viewState.showMessage(text)

}
