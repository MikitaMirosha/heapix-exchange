package com.heapix.exchange.ui.converting

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.exchange.MyApp
import com.heapix.exchange.base.BaseMvpPresenter
import com.heapix.exchange.model.KeyboardModel
import com.heapix.exchange.net.repo.KeyboardRepo
import com.heapix.exchange.net.repo.PairExchangeRepo
import com.heapix.exchange.net.repo.StandardExchangeRepo
import io.reactivex.Observable
import org.kodein.di.instance

@InjectViewState
class ConvertingPresenter : BaseMvpPresenter<ConvertingView>() {

    private val keyboardRepo: KeyboardRepo by MyApp.kodein.instance()
    private val pairExchangeRepo: PairExchangeRepo by MyApp.kodein.instance()
    private val standardExchangeRepo: StandardExchangeRepo by MyApp.kodein.instance()

    //private lateinit var currencyCodeList: List<Pair<String, Double>>

    fun onCreate(
        keyboardButtonClickObservable: Observable<KeyboardModel>,
        currencyCodeClickObservable: Observable<Pair<String, Double>>
    ) {
        getKeyboardAndUpdateUi()
//        getPairResponseAndUpdateUi()
        getStandardResponseAndUpdateUi()

        setupOnKeyboardButtonClickListener(keyboardButtonClickObservable)
        setupOnCurrencyCodeClickListener(currencyCodeClickObservable)
    }

    private fun getKeyboardAndUpdateUi() {
        viewState.updateKeyboard(keyboardRepo.getAllKeyNumbers())
    }

    private fun getStandardResponseAndUpdateUi() {
        addDisposable(
            standardExchangeRepo.getStandardResponse("USD")
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        viewState.updateCurrencyCodes(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun setupOnKeyboardButtonClickListener(keyboardButtonClickObservable: Observable<KeyboardModel>) {
        addDisposable(
            keyboardButtonClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        viewState.setupKeyboardNumber(it.keyboardNumber.toString())
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
                        //standardExchangeRepo.saveBaseCode(it.first)

                        setupBaseCodeAndHideList(it.first)
                        //setupTargetCodeAndHideList(it.first)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun setupBaseCodeAndHideList(baseCode: String?) {
        viewState.toggleCurrencyCodeList()
        viewState.updateBaseCode(baseCode)
    }

    private fun setupTargetCodeAndHideList(targetCode: String?) {
        viewState.toggleCurrencyCodeList()
        viewState.updateTargetCode(targetCode)
    }

    fun onBaseCodeClicked() = viewState.toggleCurrencyCodeList()

    fun onTargetCodeClicked() = viewState.toggleCurrencyCodeList()

    fun onCommaButtonClicked() = viewState.setupComma()

    fun onZeroButtonClicked() = viewState.setupZeroNumber()

    fun onBackspaceButtonClicked() = viewState.clearSingleNumber()

}