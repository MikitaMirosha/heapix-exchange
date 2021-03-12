package com.heapix.exchange.ui.converting.adapter.currencycode

import android.view.View
import com.heapix.exchange.base.adapters.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_currency_code.*

class CurrencyCodeViewHolder(
    itemView: View,
    private val standardExchangePublishSubject: PublishSubject<Pair<String, Double>>
) : BaseViewHolder<Pair<String, Double>>(itemView) {

    override fun bind(model: Pair<String, Double>) {
        setupCurrencyCodeConversion(model)

        setupClickListener(model)
    }

    private fun setupCurrencyCodeConversion(currencyCode: Pair<String, Double>) {
        vTvCurrencyCodeItem.text = currencyCode.first
    }

    private fun setupClickListener(currencyCode: Pair<String, Double>) {
        setOnClickListener {
            standardExchangePublishSubject.onNext(currencyCode) // FIX!
        }
    }

}