package com.heapix.exchange.ui.rates

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.exchange.MyApp
import com.heapix.exchange.base.BaseMvpPresenter
import com.heapix.exchange.net.repo.ExchangeRatesRepo
import com.heapix.exchange.net.responses.ExchangeRatesResponse
import io.reactivex.Observable
import org.joda.time.format.DateTimeFormat
import org.kodein.di.instance

@InjectViewState
class RatesPresenter : BaseMvpPresenter<RatesView>() {

    private val exchangeRatesRepo: ExchangeRatesRepo by MyApp.kodein.instance()

    fun onCreate(currencyCodeClickObservable: Observable<Pair<String, Double>>) {
        checkBaseCodeInStorage()

        setupOnCurrencyCodeClickListener(currencyCodeClickObservable)
    }

    companion object {
        private const val BASE_CODE = "USD"

        private const val INPUT_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z"
        private const val OUTPUT_PATTERN = "yyyy-MM-dd HH:mm"
    }

    private fun checkBaseCodeInStorage() {
        if (isBaseCodeInStorage()) {
            getExchangeRatesAndUpdateUi(getBaseCode())
        } else {
            viewState.hideRatesScreen()
            viewState.showBaseCodeSelectingView()

            getExchangeRatesAndUpdateUi(BASE_CODE)
        }
    }

    private fun getExchangeRatesAndUpdateUi(baseCode: String) {
        addDisposable(
            exchangeRatesRepo.getExchangeRates(baseCode)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        setupTimeLastUpdateUtc(it.timeLastUpdateUtc)
                        setupTimeNextUpdateUtc(it.timeNextUpdateUtc)

                        updateSelectedBaseCode(it.baseCode)

                        updateCurrencyCardList(it)
                        updateCurrencyCodeList(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun setupOnCurrencyCodeClickListener(currencyCodeClickObservable: Observable<Pair<String, Double>>) {
        addDisposable(
            currencyCodeClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        saveBaseCode(it.first)

                        setupBaseCodeAndUpdateUi(it.first)

                        saveBaseCode(it.first)
                        viewState.updateBaseCode(getBaseCode())
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun isBaseCodeInStorage(): Boolean = exchangeRatesRepo.isBaseCodeInStorage()

    private fun saveBaseCode(baseCode: String) = exchangeRatesRepo.saveBaseCode(baseCode)

    fun getBaseCode() = exchangeRatesRepo.getBaseCode()

    private fun setupTimeLastUpdateUtc(timeLastUpdateUtc: String?) {
        viewState.updateTimeLastUpdateUtc(
            DateTimeFormat
                .forPattern(OUTPUT_PATTERN)
                .print(
                    DateTimeFormat
                        .forPattern(INPUT_PATTERN)
                        .parseDateTime(timeLastUpdateUtc)
                )
        )
    }

    private fun setupTimeNextUpdateUtc(timeNextUpdateUtc: String?) {
        viewState.updateTimeNextUpdateUtc(
            DateTimeFormat
                .forPattern(OUTPUT_PATTERN)
                .print(
                    DateTimeFormat
                        .forPattern(INPUT_PATTERN)
                        .parseDateTime(timeNextUpdateUtc)
                )
        )
    }

    private fun updateSelectedBaseCode(baseCode: String?) {
        viewState.updateSelectedBaseCode(baseCode ?: "")
    }

    private fun updateCurrencyCardList(conversionRates: ExchangeRatesResponse) {
        conversionRates.conversionRates?.toList()?.let {
            viewState.updateCurrencyCardList(it)
        }
    }

    private fun updateCurrencyCodeList(conversionRates: ExchangeRatesResponse) {
        conversionRates.conversionRates?.toList()?.let {
            viewState.updateCurrencyCodeList(it)
        }
    }

    private fun setupBaseCodeAndUpdateUi(baseCode: String) {
        getExchangeRatesAndUpdateUi(baseCode)

        toggleCurrencyCodeList()

        viewState.showRatesScreen()
    }

    fun toggleCurrencyCodeList() = viewState.toggleCurrencyCodeList()

    fun onConvertingButtonClicked() = viewState.openConvertingActivity()

}