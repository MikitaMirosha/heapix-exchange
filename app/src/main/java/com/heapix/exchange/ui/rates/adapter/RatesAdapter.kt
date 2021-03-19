package com.heapix.exchange.ui.rates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.exchange.R
import com.heapix.exchange.base.adapters.BaseListAdapter
import com.heapix.exchange.base.adapters.BaseViewHolder

class RatesAdapter(val baseCode: String) : BaseListAdapter<Pair<String, Double>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Pair<String, Double>> {
        return RatesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_currency_card, parent, false),
            baseCode
        )
    }

}
