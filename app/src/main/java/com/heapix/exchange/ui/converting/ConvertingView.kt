package com.heapix.exchange.ui.converting

import com.heapix.exchange.base.BaseMvpView
import com.heapix.exchange.model.KeyboardModel

interface ConvertingView : BaseMvpView {
    fun updateKeyboard(keyNumberList: MutableList<KeyboardModel>)
}