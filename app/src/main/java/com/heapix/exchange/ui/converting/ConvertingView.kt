package com.heapix.exchange.ui.converting

import com.heapix.exchange.base.BaseMvpView
import com.heapix.exchange.model.KeyboardModel

interface ConvertingView : BaseMvpView {
    fun updateBaseCode(baseCode: String?)
    fun updateTargetCode(targetCode: String?)

    fun updateConversionResult(conversionResult: Double?)
    fun updateKeyboard(keyboardModelList: MutableList<KeyboardModel>)
    fun updateCurrencyCodeList(currencyCodeList: List<Pair<String, Double>>)

    fun setupDot()
    fun setupZeroNumber()
    fun setupKeyboardNumber(keyboardNumber: String?)

    fun clearValues()
    fun clearSingleNumber()

    fun switchRateValues()
    fun toggleCurrencyCodeList()
}