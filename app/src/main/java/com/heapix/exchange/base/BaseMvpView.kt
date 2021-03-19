package com.heapix.exchange.base

import com.arellomobile.mvp.MvpView

interface BaseMvpView : MvpView {
    fun showMessage(resId: Int)
    fun showMessage(msg: String?)
    fun handleRestError(e: Throwable)
}