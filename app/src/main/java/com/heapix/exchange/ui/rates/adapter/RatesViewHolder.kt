package com.heapix.exchange.ui.rates.adapter

import android.view.View
import com.heapix.exchange.base.adapters.BaseViewHolder
import kotlinx.android.synthetic.main.item_currency_card.*
import java.math.RoundingMode

class RatesViewHolder(
    itemView: View
) : BaseViewHolder<Pair<String, Double>>(itemView) {

    override fun bind(model: Pair<String, Double>) {
        setupBaseCode(model)
        setupBaseRate(model)
        setupCurrencyCode(model)
        setupCurrencyRate(model)
        setupCurrencyCodeConversion(model)
    }

    companion object {
        private const val ONE_CONVENTIONAL_UNIT = 1.0
        private const val SCALE_FOUR = 4
    }

    private fun setupBaseCode(conversionRate: Pair<String, Double>) {
//        vTvBaseCode.text = conversionRate.
    }

    private fun setupBaseRate(conversionRate: Pair<String, Double>) {
        vTvBaseRate.text = (ONE_CONVENTIONAL_UNIT.div(conversionRate.second))
            .toBigDecimal()
            .setScale(SCALE_FOUR, RoundingMode.UP)
            .toDouble()
            .toString()
    }

    private fun setupCurrencyCode(conversionRate: Pair<String, Double>) {
        vTvCurrencyCode.text = conversionRate.first
    }

    private fun setupCurrencyRate(conversionRate: Pair<String, Double>) {
        vTvCurrencyRate.text = conversionRate.second
            .toBigDecimal()
            .setScale(SCALE_FOUR, RoundingMode.UP)
            .toDouble()
            .toString()
    }

    private fun setupCurrencyCodeConversion(conversionRate: Pair<String, Double>) {
        vTvCurrencyCodeConversion.text = conversionRate.first
    }

}