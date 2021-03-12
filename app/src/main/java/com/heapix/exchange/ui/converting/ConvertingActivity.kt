package com.heapix.exchange.ui.converting

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.exchange.R
import com.heapix.exchange.base.BaseMvpActivity
import com.heapix.exchange.model.KeyboardModel
import com.heapix.exchange.ui.converting.adapter.currencycode.CurrencyCodeAdapter
import com.heapix.exchange.ui.converting.adapter.keyboard.KeyboardAdapter
import kotlinx.android.synthetic.main.activity_converting.*
import kotlinx.android.synthetic.main.view_currency_code_list.*
import kotlinx.android.synthetic.main.view_keyboard.*

class ConvertingActivity : BaseMvpActivity(), ConvertingView {

    @InjectPresenter
    lateinit var convertingPresenter: ConvertingPresenter

    private lateinit var keyboardAdapter: KeyboardAdapter
    private lateinit var currencyCodeAdapter: CurrencyCodeAdapter

    override fun getLayoutId(): Int = R.layout.activity_converting

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        initListeners()

        setupKeyboardAdapter()
        setupCurrencyCodeAdapter()

        convertingPresenter.onCreate(
            keyboardAdapter.keyboardButtonClickObservable,
            currencyCodeAdapter.currencyCodeClickObservable
        )
    }

    companion object {
        private const val COMMA = ","
        private const val ZERO_NUMBER = "0"
        private const val LAST_NUMBER = 1
    }

    private fun initListeners() {
        vTvBaseCodeConversion.setOnClickListener {
            convertingPresenter.onBaseCodeClicked()
        }

        vTvTargetCodeConversion.setOnClickListener {
            convertingPresenter.onTargetCodeClicked()
        }

        vFlCommaButton.setOnClickListener {
            convertingPresenter.onCommaButtonClicked()
        }

        vFlZeroButton.setOnClickListener {
            convertingPresenter.onZeroButtonClicked()
        }

        vFlBackspaceButton.setOnClickListener {
            convertingPresenter.onBackspaceButtonClicked()
        }
    }

    private fun setupKeyboardAdapter() {
        keyboardAdapter = KeyboardAdapter()
        vRvKeyboardButtons.adapter = keyboardAdapter
    }

    private fun setupCurrencyCodeAdapter() {
        currencyCodeAdapter = CurrencyCodeAdapter()
        vRvCurrencyCodeList.adapter = currencyCodeAdapter
    }

    private fun isCommaInputValid(value: String?): Boolean {
        return value?.trim { it <= ' ' }?.isNotEmpty() == true
                && value.contains(COMMA).not()
    }

    override fun updateKeyboard(keyboardModelList: MutableList<KeyboardModel>) =
        keyboardAdapter.setItems(keyboardModelList)

    override fun updateCurrencyCodes(currencyCodeList: List<Pair<String, Double>>) =
        currencyCodeAdapter.setItems(currencyCodeList)

    override fun updateBaseCode(baseCode: String?) {
        vTvBaseCodeConversion.text = baseCode
    }

    override fun updateTargetCode(targetCode: String?) {
        vTvTargetCodeConversion.text = targetCode
    }

    override fun updateConversionResult(conversionResult: Double?) {
        // TODO
    }

    override fun toggleCurrencyCodeList() = vCurrencyCodeListBottomSheet.toggle()

    override fun setupComma() {
        when {
            vEtPrimaryValue.isFocused -> {
                if (isCommaInputValid(vEtPrimaryValue.text.toString())) {
                    vEtPrimaryValue.append(COMMA)
                }
            }

            vEtSecondaryValue.isFocused -> {
                if (isCommaInputValid(vEtSecondaryValue.text.toString())) {
                    vEtSecondaryValue.append(COMMA)
                }
            }
        }
    }

    override fun setupZeroNumber() {
        when {
            vEtPrimaryValue.isFocused -> vEtPrimaryValue.append(ZERO_NUMBER)
            vEtSecondaryValue.isFocused -> vEtSecondaryValue.append(ZERO_NUMBER)
        }
    }

    override fun setupKeyboardNumber(keyboardNumber: String?) {
        when {
            vEtPrimaryValue.isFocused -> vEtPrimaryValue.append(keyboardNumber)
            vEtSecondaryValue.isFocused -> vEtSecondaryValue.append(keyboardNumber)
        }
    }

    override fun clearSingleNumber() {
        when {
            vEtPrimaryValue.isFocused -> {
                vEtPrimaryValue.setText(vEtPrimaryValue.text.toString().dropLast(LAST_NUMBER))
            }

            vEtSecondaryValue.isFocused -> {
                vEtSecondaryValue.setText(vEtSecondaryValue.text.toString().dropLast(LAST_NUMBER))
            }
        }
    }

}