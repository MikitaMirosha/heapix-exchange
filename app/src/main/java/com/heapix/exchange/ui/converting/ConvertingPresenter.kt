package com.heapix.exchange.ui.converting

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.exchange.ConvertingCodeState
import com.heapix.exchange.MyApp
import com.heapix.exchange.base.BaseMvpPresenter
import com.heapix.exchange.model.KeyboardModel
import com.heapix.exchange.net.repo.ExchangeRatesRepo
import com.heapix.exchange.net.repo.KeyboardRepo
import com.heapix.exchange.net.repo.PairExchangeRepo
import com.heapix.exchange.net.responses.ExchangeRatesResponse
import com.heapix.exchange.net.responses.PairExchangeResponse
import io.reactivex.Observable
import org.kodein.di.instance
import java.math.MathContext
import java.math.RoundingMode

@InjectViewState
class ConvertingPresenter : BaseMvpPresenter<ConvertingView>() {

    private val keyboardRepo: KeyboardRepo by MyApp.kodein.instance()
    private val pairExchangeRepo: PairExchangeRepo by MyApp.kodein.instance()
    private val exchangeRatesRepo: ExchangeRatesRepo by MyApp.kodein.instance()

    private lateinit var pairExchangeResponse: PairExchangeResponse

    private var isSwitchClicked = true

    fun onCreate(
        keyboardButtonClickObservable: Observable<KeyboardModel>,
        currencyCodeClickObservable: Observable<Pair<String, Double>>
    ) {
        checkTargetCodeInStorage()

        getKeyboardAndUpdateUi()
        getExchangeRatesAndUpdateUi()

        setupOnKeyboardButtonClickListener(keyboardButtonClickObservable)
        setupOnCurrencyCodeClickListener(currencyCodeClickObservable)
    }

    companion object {
        private const val PRECISION = 6
    }

    private fun checkTargetCodeInStorage() {
        if (isTargetCodeInStorage()) {
            getPairExchangeAndUpdateUi(
                exchangeRatesRepo.getBaseCode(),
                pairExchangeRepo.getTargetCode()
            )
        } else {
            getPairExchangeAndUpdateUi(
                exchangeRatesRepo.getBaseCode(),
                exchangeRatesRepo.getBaseCode()
            )
        }
    }

    private fun getKeyboardAndUpdateUi() {
        viewState.updateKeyboard(keyboardRepo.getKeyNumbers())
    }

    private fun getExchangeRatesAndUpdateUi() {
        addDisposable(
            exchangeRatesRepo.getExchangeRates(exchangeRatesRepo.getBaseCode())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        updateCurrencyCodeList(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun getPairExchangeAndUpdateUi(baseCode: String, targetCode: String) {
        addDisposable(
            pairExchangeRepo.getPairExchange(baseCode, targetCode)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        pairExchangeResponse = it

                        updateCodes(it.baseCode, it.targetCode)
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
                        when {
                            ConvertingCodeState.BASE_CODE.isClicked -> {
                                exchangeRatesRepo.saveBaseCode(it.first)
                                setupBaseCodeAndHideList(it.first)

                                getPairExchangeAndUpdateUi(
                                    exchangeRatesRepo.getBaseCode(),
                                    pairExchangeRepo.getTargetCode()
                                )
                            }
                            ConvertingCodeState.TARGET_CODE.isClicked -> {
                                pairExchangeRepo.saveTargetCode(it.first)
                                setupTargetCodeAndHideList(it.first)

                                getPairExchangeAndUpdateUi(
                                    exchangeRatesRepo.getBaseCode(),
                                    pairExchangeRepo.getTargetCode()
                                )
                            }
                        }
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun isTargetCodeInStorage(): Boolean = pairExchangeRepo.isTargetCodeInStorage()

    private fun updateCurrencyCodeList(exchangeRatesResponse: ExchangeRatesResponse) {
        exchangeRatesResponse.conversionRates?.toList()?.let {
            viewState.updateCurrencyCodeList(it)
        }
    }

    private fun updateCodes(baseCode: String?, targetCode: String?) {
        viewState.updateBaseCode(baseCode ?: "")
        viewState.updateTargetCode(targetCode ?: "")
    }

    private fun setupBaseCodeAndHideList(baseCode: String) {
        viewState.updateBaseCode(baseCode)
        viewState.toggleCurrencyCodeList()
    }

    private fun setupTargetCodeAndHideList(targetCode: String) {
        viewState.updateTargetCode(targetCode)
        viewState.toggleCurrencyCodeList()
    }

    fun onBackToRatesButtonClicked() = viewState.openRatesActivity()

    fun onSwitchCurrencyClicked() {
        isSwitchClicked = isSwitchClicked.not()

        getPairExchangeAndUpdateUi(
            if (isSwitchClicked) exchangeRatesRepo.getBaseCode() else pairExchangeRepo.getTargetCode(),
            if (isSwitchClicked) pairExchangeRepo.getTargetCode() else exchangeRatesRepo.getBaseCode()
        )

        viewState.switchRateValues()
    }

    fun onBaseCodeClicked() {
        ConvertingCodeState.BASE_CODE.isClicked = true
        ConvertingCodeState.TARGET_CODE.isClicked = false

        viewState.toggleCurrencyCodeList()
    }

    fun onTargetCodeClicked() {
        ConvertingCodeState.BASE_CODE.isClicked = false
        ConvertingCodeState.TARGET_CODE.isClicked = true

        viewState.toggleCurrencyCodeList()
    }

    fun onPrimaryFieldTextChanged(text: String?) {
        if (text?.length != 0) {
            viewState.updateSecondaryValueConversionResult(countPrimaryValue(text) ?: 0.0)
        } else {
            viewState.clearValues()
        }
    }

    fun onSecondaryFieldTextChanged(text: String?) {
        if (text?.length != 0) {
            viewState.updatePrimaryValueConversionResult(countSecondaryValue(text) ?: 0.0)
        } else {
            viewState.clearValues()
        }
    }

    private fun countPrimaryValue(text: String?): Double? {

        return text?.toDouble()?.let {
            pairExchangeResponse.conversionRate
                ?.times(it)
                ?.toBigDecimal()
                ?.round(MathContext(PRECISION, RoundingMode.UP))
                ?.toDouble()
        }
    }

    private fun countSecondaryValue(text: String?): Double? {

        return text?.toDouble()?.let {
            pairExchangeResponse.conversionRate?.let {
                text.toDouble()
                    .div(it)
                    .toBigDecimal()
                    .round(MathContext(PRECISION, RoundingMode.UP))
                    ?.toDouble()
            }
        }
    }

    fun onDotButtonClicked() = viewState.setupDot()

    fun onZeroButtonClicked() = viewState.setupZeroNumber()

    fun onBackspaceButtonClicked() = viewState.clearSingleNumber()

    fun onBackspaceButtonLongClicked(): Boolean {
        viewState.clearValues()
        return true
    }

}