package com.heapix.exchange.ui.converting

import com.heapix.exchange.base.BaseMvpView
import com.heapix.exchange.model.KeyboardModel

interface ConvertingView : BaseMvpView {
    fun updateKeyboard(keyboardModelList: MutableList<KeyboardModel>)
    fun updateCurrencyCodes(currencyCodeList: List<Pair<String, Double>>)
    fun updateBaseCode(baseCode: String?)
    fun updateTargetCode(targetCode: String?)
    fun updateConversionResult(conversionResult: Double?)

    fun toggleCurrencyCodeList()

    fun setupComma()
    fun setupZeroNumber()
    fun setupKeyboardNumber(keyboardNumber: String?)

    fun clearSingleNumber()
}