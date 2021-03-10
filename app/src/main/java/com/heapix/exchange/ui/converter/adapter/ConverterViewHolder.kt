package com.heapix.exchange.ui.converter.adapter

import android.view.View
import com.heapix.exchange.base.adapters.BaseViewHolder
import com.heapix.exchange.net.responses.StandardExchangeResponse
import io.reactivex.subjects.PublishSubject

class ConverterViewHolder(
    itemView: View,
    private val standardExchangePublishSubject: PublishSubject<StandardExchangeResponse>
) : BaseViewHolder<StandardExchangeResponse>(itemView) {

    override fun bind(model: StandardExchangeResponse) {
//        setupCurrencyCode(model)

        setupClickListener(model)
    }

//    private fun setupCurrencyCode(standardExchangeResponse: StandardExchangeResponse) {
//        vTvCurrencyCode.text = standardExchangeResponse.baseCode
//    }

    private fun setupClickListener(standardExchangeResponse: StandardExchangeResponse) {
        setOnClickListener {
            standardExchangePublishSubject.onNext(standardExchangeResponse)
        }
    }

}