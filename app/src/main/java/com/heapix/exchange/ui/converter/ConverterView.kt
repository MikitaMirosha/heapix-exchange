package com.heapix.exchange.ui.converter

import com.heapix.exchange.base.BaseMvpView

interface ConverterView : BaseMvpView {
    fun setupTimeLastUpdateUtc(timeLastUpdateUtc: String?)
    fun setupTimeNextUpdateUtc(timeNextUpdateUtc: String?)

    fun updateCurrencyCards(standardExchangeList: List<Pair<String, Double>>)

    fun openConvertingActivity()
}