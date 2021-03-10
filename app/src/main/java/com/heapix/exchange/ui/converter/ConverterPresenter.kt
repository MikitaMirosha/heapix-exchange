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
        getConversionRatesAndUpdateUi()
    }

    private fun getStandardResponseAndUpdateUi() {
        addDisposable(
            standardExchangeRepo.getStandardResponse()
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

    private fun getConversionRatesAndUpdateUi() {
        addDisposable(
            standardExchangeRepo.getStandardResponse()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
//                        viewState.updateConverter() TODO
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