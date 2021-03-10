package com.heapix.exchange.ui.converter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.exchange.R
import com.heapix.exchange.base.adapters.BaseListAdapter
import com.heapix.exchange.base.adapters.BaseViewHolder
import com.heapix.exchange.net.responses.StandardExchangeResponse
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ConverterAdapter : BaseListAdapter<StandardExchangeResponse>() {

    private val standardExchangePublishSubject: PublishSubject<StandardExchangeResponse> =
        PublishSubject.create()
    val currencyCardClickObservable: Observable<StandardExchangeResponse> =
        standardExchangePublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<StandardExchangeResponse> {
        return ConverterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_currency_card, parent, false),
            standardExchangePublishSubject
        )
    }

}
