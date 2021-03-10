package com.heapix.exchange.ui.converter

import com.heapix.exchange.base.BaseMvpView
import com.heapix.exchange.net.responses.StandardExchangeResponse

interface ConverterView : BaseMvpView {
    fun setupTimeLastUpdateUtc(timeLastUpdateUtc: String?)
    fun setupTimeNextUpdateUtc(timeNextUpdateUtc: String?)

    fun updateConverter(standardExchangeResponseList: MutableList<StandardExchangeResponse>)

    fun openConvertingActivity()
}