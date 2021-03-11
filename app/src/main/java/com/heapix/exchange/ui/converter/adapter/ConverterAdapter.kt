package com.heapix.exchange.ui.converter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.exchange.R
import com.heapix.exchange.base.adapters.BaseListAdapter
import com.heapix.exchange.base.adapters.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ConverterAdapter : BaseListAdapter<Pair<String, Double>>() {

    private val standardExchangePublishSubject: PublishSubject<Pair<String, Double>> =
        PublishSubject.create()
    val currencyCardClickObservable: Observable<Pair<String, Double>> =
        standardExchangePublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Pair<String, Double>> {
        return ConverterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_currency_card, parent, false),
            standardExchangePublishSubject
        )
    }

}
