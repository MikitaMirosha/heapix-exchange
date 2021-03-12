package com.heapix.exchange.ui.converter

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.exchange.R
import com.heapix.exchange.base.BaseMvpActivity
import com.heapix.exchange.ui.converter.adapter.ConverterAdapter
import com.heapix.exchange.ui.converting.ConvertingActivity
import kotlinx.android.synthetic.main.activity_converter.*
import org.joda.time.format.DateTimeFormat

class ConverterActivity : BaseMvpActivity(), ConverterView {

    @InjectPresenter
    lateinit var converterPresenter: ConverterPresenter

    private lateinit var converterAdapter: ConverterAdapter

    override fun getLayoutId(): Int = R.layout.activity_converter

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        initListeners()

        setupConverterAdapter()

        converterPresenter.onCreate()
    }

    companion object {
        private const val INPUT_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z"
        private const val OUTPUT_PATTERN = "yyyy-MM-dd HH:mm"
    }

    private fun initListeners() {
        vFloatingActionButton.setOnClickListener {
            converterPresenter.onFloatingActionButtonClicked()
        }
    }

    private fun setupConverterAdapter() {
        converterAdapter = ConverterAdapter()
        vRvCurrencyCards.adapter = converterAdapter
    }

    override fun setupTimeLastUpdateUtc(timeLastUpdateUtc: String?) {
        vTvTimeLastUpdateUtc.text = DateTimeFormat
            .forPattern(OUTPUT_PATTERN)
            .print(
                DateTimeFormat
                    .forPattern(INPUT_PATTERN)
                    .parseDateTime(timeLastUpdateUtc)
            )
    }

    override fun setupTimeNextUpdateUtc(timeNextUpdateUtc: String?) {
        vTvTimeNextUpdateUtc.text = DateTimeFormat
            .forPattern(OUTPUT_PATTERN)
            .print(
                DateTimeFormat
                    .forPattern(INPUT_PATTERN)
                    .parseDateTime(timeNextUpdateUtc)
            )
    }

    override fun updateCurrencyCards(currencyCardList: List<Pair<String, Double>>) =
        converterAdapter.setItems(currencyCardList)

    override fun openConvertingActivity() =
        startActivity(Intent(this, ConvertingActivity::class.java))

}