package com.heapix.exchange.ui.converter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.exchange.MyApp
import com.heapix.exchange.base.BaseMvpPresenter
import com.heapix.exchange.net.repo.StandardExchangeRepo
import org.kodein.di.instance

@InjectViewState
class ConverterPresenter : BaseMvpPresenter<ConverterView>() {

    private val standardExchangeRepo: StandardExchangeRepo by MyApp.kodein.instance()

    fun onCreate() {
        getStandardResponseAndUpdateUi()
        getStandardResponseTimeAndUpdateUi()
    }

    private fun getStandardResponseAndUpdateUi() {
        addDisposable(
            standardExchangeRepo.getStandardResponse("USD")
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        standardExchangeRepo.saveBaseCode("USD")
                        viewState.updateCurrencyCards(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun getStandardResponseTimeAndUpdateUi() {
        addDisposable(
            standardExchangeRepo.getStandardResponseTime(standardExchangeRepo.getBaseCode())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        setupTimeUpdate(it.timeLastUpdateUtc, it.timeNextUpdateUtc)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun setupTimeUpdate(timeLastUpdateUtc: String?, timeNextUpdateUtc: String?) {
        viewState.setupTimeLastUpdateUtc(timeLastUpdateUtc)
        viewState.setupTimeNextUpdateUtc(timeNextUpdateUtc)
    }

    fun onFloatingActionButtonClicked() = viewState.openConvertingActivity()

}