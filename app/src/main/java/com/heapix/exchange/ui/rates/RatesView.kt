package com.heapix.exchange.ui.rates

import com.heapix.exchange.base.BaseMvpView

interface RatesView : BaseMvpView {
    fun updateBaseCode(baseCode: String?)

    fun updateTimeLastUpdateUtc(timeLastUpdateUtc: String?)
    fun updateTimeNextUpdateUtc(timeNextUpdateUtc: String?)

    fun updateCurrencyCardList(currencyCardList: List<Pair<String, Double>>)
    fun updateCurrencyCodeList(currencyCodeList: List<Pair<String, Double>>)

    fun toggleCurrencyCodeList()
    fun hideBaseCodeSelectingView()

    fun openConvertingActivity()
}