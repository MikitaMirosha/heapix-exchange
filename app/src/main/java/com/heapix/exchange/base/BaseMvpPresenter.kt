package com.heapix.exchange.base

import android.content.SharedPreferences
import androidx.annotation.StringRes
import com.arellomobile.mvp.MvpPresenter
import com.heapix.exchange.MyApp
import com.heapix.exchange.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.kodein.di.instance
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseMvpPresenter<V : BaseMvpView> : MvpPresenter<V>() {

    private val compositeDisposable: CompositeDisposable by MyApp.kodein.instance()

    //    protected val api: Api by  MyApp.kodein.instance()
    protected val preferences: SharedPreferences by MyApp.kodein.instance()
    protected val schedulers: SchedulerProvider by MyApp.kodein.instance()

    init {
//        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()

//        EventBus.getDefault().unregister(this)
        clearDisposable()
    }

    //    @Subscribe
    fun onEvent(anyEvent: Any) {

        //default method
    }

    private fun clearDisposable() {
        compositeDisposable.clear()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun removeDisposable(disposable: Disposable) {
        compositeDisposable.remove(disposable)
    }

//    protected fun askPermissions(vararg permissions: String, listener: OnPermissionsListener) {
//        viewState.requestPermissions(*permissions, listener = listener)
//    }

    protected fun showMessage(@StringRes text: Int) {
        viewState.showMessage(text)
    }

    protected fun showMessage(text: String) {
        viewState.showMessage(text)
    }

    fun processNetworkException(it: Throwable?, showMessage: Boolean = true): Boolean {
        if (it is HttpException) {
            if (it.code() == 401 || it.code() == 403) {
//                EventBus.getDefault().post(ProtocolExceptionEvent())

                return true
            }
        }

        if (it is SocketTimeoutException) {
            if (showMessage)
//                showMessage(R.string.socketTimeoutException)

                return true
        } else if (it is ConnectException || it is UnknownHostException) {
            if (showMessage)
//                showMessage(R.string.connectException)

                return true
        }

        return false
    }

}
