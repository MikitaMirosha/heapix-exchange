package com.heapix.exchange.ui.converting.adapter.currencycode

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.exchange.R
import com.heapix.exchange.base.adapters.BaseListAdapter
import com.heapix.exchange.base.adapters.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CurrencyCodeAdapter : BaseListAdapter<Pair<String, Double>>() {

    private val currencyCodePublishSubject: PublishSubject<Pair<String, Double>> =
        PublishSubject.create()
    val currencyCodeClickObservable: Observable<Pair<String, Double>> =
        currencyCodePublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Pair<String, Double>> {
        return CurrencyCodeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_currency_code, parent, false),
            currencyCodePublishSubject
        )
    }

}