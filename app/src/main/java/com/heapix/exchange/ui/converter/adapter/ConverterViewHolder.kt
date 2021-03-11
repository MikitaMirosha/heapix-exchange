package com.heapix.exchange.ui.converter.adapter

import android.view.View
import com.heapix.exchange.MyApp
import com.heapix.exchange.base.adapters.BaseViewHolder
import com.heapix.exchange.net.repo.StandardExchangeRepo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_currency_card.*
import org.kodein.di.instance
import kotlin.math.roundToInt

class ConverterViewHolder(
    itemView: View,
    private val standardExchangePublishSubject: PublishSubject<Pair<String, Double>>
) : BaseViewHolder<Pair<String, Double>>(itemView) {

    private val standardExchangeRepo: StandardExchangeRepo by MyApp.kodein.instance()

    override fun bind(model: Pair<String, Double>) {
        setupCurrencyCode(model)
        setupCurrencyCodeConversion(model)
        setupBaseRate(model)
        setupBaseCode()
    }

    companion object {
        private const val ONE_CONVENTIONAL_UNIT = 1
        private const val DECIMAL_PLACE = 10000.0
    }

    private fun setupCurrencyCode(conversionRate: Pair<String, Double>) {
        vTvCurrencyCode.text = conversionRate.first
    }

    private fun setupCurrencyCodeConversion(conversionRate: Pair<String, Double>) {
        vTvCurrencyCodeConversion.text = conversionRate.first
    }

    private fun setupBaseRate(conversionRate: Pair<String, Double>) {
        vTvBaseRate.text =
            (((ONE_CONVENTIONAL_UNIT / conversionRate.second) * DECIMAL_PLACE)
                .roundToInt() / DECIMAL_PLACE)
                .toString()
    }

    private fun setupBaseCode() {
        vTvBaseCode.text = standardExchangeRepo.getBaseCode()
    }

}