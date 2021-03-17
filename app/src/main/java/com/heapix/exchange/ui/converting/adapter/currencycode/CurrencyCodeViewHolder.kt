package com.heapix.exchange.ui.converting.adapter.currencycode

import android.view.View
import com.heapix.exchange.base.adapters.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_currency_code.*

class CurrencyCodeViewHolder(
    itemView: View,
    private val currencyCodePublishSubject: PublishSubject<Pair<String, Double>>
) : BaseViewHolder<Pair<String, Double>>(itemView) {

    override fun bind(model: Pair<String, Double>) {
        setupCurrencyCodeItem(model)

        setupClickListener(model)
    }

    private fun setupCurrencyCodeItem(currencyCodeItem: Pair<String, Double>) {
        vTvCurrencyCodeItem.text = currencyCodeItem.first
    }

    private fun setupClickListener(currencyCodeItem: Pair<String, Double>) {
        setOnClickListener {
            currencyCodePublishSubject.onNext(currencyCodeItem)
        }
    }

}