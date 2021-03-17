package com.heapix.exchange.ui.converting

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.exchange.MyApp
import com.heapix.exchange.base.BaseMvpPresenter
import com.heapix.exchange.model.KeyboardModel
import com.heapix.exchange.net.repo.ExchangeRatesRepo
import com.heapix.exchange.net.repo.KeyboardRepo
import com.heapix.exchange.net.repo.PairExchangeRepo
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

    private lateinit var pairExchangeList: PairExchangeResponse

    private var isSwitchClicked = false

    private var isBaseCodeClicked = false
    private var isTargetCodeClicked = false

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
        private const val DEFAULT_RATE = 0.0
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

    private fun getPairExchangeAndUpdateUi(baseCode: String?, targetCode: String?) {
        addDisposable(
            pairExchangeRepo.getPairExchange(baseCode, targetCode, DEFAULT_RATE)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        pairExchangeList = it

                        updateCodes(it.baseCode, it.targetCode)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun getExchangeRatesAndUpdateUi() {
        addDisposable(
            exchangeRatesRepo.getExchangeRates(exchangeRatesRepo.getBaseCode())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        viewState.updateCurrencyCodeList(it)
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
                            isBaseCodeClicked -> {
                                exchangeRatesRepo.saveBaseCode(it.first)
                                setupBaseCodeAndHideList(it.first)

                                getPairExchangeAndUpdateUi(
                                    exchangeRatesRepo.getBaseCode(),
                                    pairExchangeRepo.getTargetCode()
                                )
                            }

                            isTargetCodeClicked -> {
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

    private fun updateCodes(baseCode: String?, targetCode: String?) {
        viewState.updateBaseCode(baseCode)
        viewState.updateTargetCode(targetCode)
    }

    private fun setupBaseCodeAndHideList(baseCode: String?) {
        viewState.updateBaseCode(baseCode)
        viewState.toggleCurrencyCodeList()
    }

    private fun setupTargetCodeAndHideList(targetCode: String?) {
        viewState.toggleCurrencyCodeList()
        viewState.updateTargetCode(targetCode)
    }

    fun onSwitchCurrencyClicked() {
        if (isSwitchClicked.not()) {
            isSwitchClicked = true

            viewState.switchRateValues()

            getPairExchangeAndUpdateUi(
                pairExchangeRepo.getTargetCode(),
                exchangeRatesRepo.getBaseCode()
            )
        } else {
            isSwitchClicked = false

            viewState.switchRateValues()

            getPairExchangeAndUpdateUi(
                exchangeRatesRepo.getBaseCode(),
                pairExchangeRepo.getTargetCode()
            )
        }
    }

    fun onBaseCodeClicked() {
        isBaseCodeClicked = true
        isTargetCodeClicked = false

        viewState.toggleCurrencyCodeList()
    }

    fun onTargetCodeClicked() {
        isTargetCodeClicked = true
        isBaseCodeClicked = false

        viewState.toggleCurrencyCodeList()
    }

    fun onTextChanged(text: String?) {
        if (text?.length != 0) {
            viewState.updateConversionResult(countChangedText(text))
        } else {
            viewState.clearValues()
        }
    }

    private fun countChangedText(text: String?): Double? {

        return text?.toDouble()?.let {
            pairExchangeList.conversionRate
                ?.times(it)
                ?.toBigDecimal()
                ?.round(MathContext(PRECISION, RoundingMode.UP))
                ?.toDouble()
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