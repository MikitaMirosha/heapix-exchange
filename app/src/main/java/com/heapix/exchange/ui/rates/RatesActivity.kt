package com.heapix.exchange.ui.rates

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.exchange.R
import com.heapix.exchange.base.BaseMvpActivity
import com.heapix.exchange.ui.converting.ConvertingActivity
import com.heapix.exchange.ui.converting.adapter.currencycode.CurrencyCodeAdapter
import com.heapix.exchange.ui.rates.adapter.RatesAdapter
import com.heapix.exchange.utils.extensions.hide
import com.heapix.exchange.utils.extensions.show
import kotlinx.android.synthetic.main.activity_rates.*
import kotlinx.android.synthetic.main.item_currency_card.*
import kotlinx.android.synthetic.main.view_base_code_selecting.*
import kotlinx.android.synthetic.main.view_currency_code_list.*

class RatesActivity : BaseMvpActivity(), RatesView {

    @InjectPresenter
    lateinit var ratesPresenter: RatesPresenter

    private lateinit var ratesAdapter: RatesAdapter
    private lateinit var currencyCodeAdapter: CurrencyCodeAdapter

    override fun getLayoutId(): Int = R.layout.activity_rates

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        initListeners()

        setupRatesAdapter()
        setupCurrencyCodeAdapter()

        ratesPresenter.onCreate(currencyCodeAdapter.currencyCodeClickObservable)
    }

    private fun initListeners() {
        vTvSelectInitialBaseCodeButton.setOnClickListener {
            ratesPresenter.toggleCurrencyCodeList()
        }

        vLlSelectBaseCodeButton.setOnClickListener {
            ratesPresenter.toggleCurrencyCodeList()
        }

        vConvertingButton.setOnClickListener {
            ratesPresenter.onConvertingButtonClicked()
        }
    }

    private fun setupRatesAdapter() {
        ratesAdapter = RatesAdapter(ratesPresenter.getBaseCode())
        vRvCurrencyCardList.adapter = ratesAdapter
    }

    private fun setupCurrencyCodeAdapter() {
        currencyCodeAdapter = CurrencyCodeAdapter()
        vRvCurrencyCodeList.adapter = currencyCodeAdapter
    }

    override fun updateBaseCode(baseCode: String) {
        vTvBaseCode.text = baseCode
    }

    override fun updateSelectedBaseCode(baseCode: String) {
        vTvSelectedBaseCode.text = baseCode
    }

    override fun updateTimeLastUpdateUtc(timeLastUpdateUtc: String) {
        vTvTimeLastUpdateUtc.text = timeLastUpdateUtc
    }

    override fun updateTimeNextUpdateUtc(timeNextUpdateUtc: String) {
        vTvTimeNextUpdateUtc.text = timeNextUpdateUtc
    }

    override fun updateCurrencyCardList(currencyCardList: List<Pair<String, Double>>) =
        ratesAdapter.setItems(currencyCardList)

    override fun updateCurrencyCodeList(currencyCodeList: List<Pair<String, Double>>) =
        currencyCodeAdapter.setItems(currencyCodeList)

    override fun toggleCurrencyCodeList() = vCodeListBottomSheet.toggle()

    override fun showRatesScreen() = vClRatesScreen.show()

    override fun hideRatesScreen() = vClRatesScreen.hide()

    override fun showBaseCodeSelectingView() = vLtBaseCodeSelecting.show()

    override fun hideBaseCodeSelectingView() = vLtBaseCodeSelecting.hide()

    override fun openConvertingActivity() {
        finish()
        startActivity(Intent(this, ConvertingActivity::class.java))
    }

}