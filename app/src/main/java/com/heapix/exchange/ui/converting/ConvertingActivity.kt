package com.heapix.exchange.ui.converting

import android.os.Bundle
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.exchange.R
import com.heapix.exchange.base.BaseMvpActivity
import com.heapix.exchange.model.KeyboardModel
import com.heapix.exchange.ui.converting.adapter.currencycode.CurrencyCodeAdapter
import com.heapix.exchange.ui.converting.adapter.keyboard.KeyboardAdapter
import com.heapix.exchange.utils.SimpleTextWatcher
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
        setupOnTextChangedListener()

        setupKeyboardAdapter()
        setupCurrencyCodeAdapter()

        convertingPresenter.onCreate(
            keyboardAdapter.keyboardButtonClickObservable,
            currencyCodeAdapter.currencyCodeClickObservable
        )
    }

    companion object {
        private const val DOT = "."
        private const val LAST_NUMBER = 1
        private const val ZERO_NUMBER = "0"
    }

    private fun initListeners() {
        vIvSwitchCurrency.setOnClickListener {
            convertingPresenter.onSwitchCurrencyClicked()
        }

        vTvBaseCodeConversion.setOnClickListener {
            convertingPresenter.onBaseCodeClicked()
        }

        vTvTargetCodeConversion.setOnClickListener {
            convertingPresenter.onTargetCodeClicked()
        }

        vFlDotButton.setOnClickListener {
            convertingPresenter.onDotButtonClicked()
        }

        vFlZeroButton.setOnClickListener {
            convertingPresenter.onZeroButtonClicked()
        }

        vFlBackspaceButton.setOnClickListener {
            convertingPresenter.onBackspaceButtonClicked()
        }

        vFlBackspaceButton.setOnLongClickListener {
            convertingPresenter.onBackspaceButtonLongClicked()
        }
    }

    private fun setupOnTextChangedListener() {
        vEtPrimaryValue.addTextChangedListener(
            object : SimpleTextWatcher() {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    convertingPresenter.onTextChanged(s.toString())
                }
            }
        )
    }

    private fun setupKeyboardAdapter() {
        keyboardAdapter = KeyboardAdapter()
        vRvKeyboardButtons.adapter = keyboardAdapter
    }

    private fun setupCurrencyCodeAdapter() {
        currencyCodeAdapter = CurrencyCodeAdapter()
        vRvCurrencyCodeList.adapter = currencyCodeAdapter
    }

    private fun isDotInputValid(value: String?): Boolean {
        return value?.trim { it <= ' ' }?.isNotEmpty() == true
                && value.contains(DOT).not()
    }

    override fun updateBaseCode(baseCode: String?) {
        vTvBaseCodeConversion.text = baseCode
    }

    override fun updateTargetCode(targetCode: String?) {
        vTvTargetCodeConversion.text = targetCode
    }

    override fun updateConversionResult(conversionResult: Double?) {
        vEtSecondaryValue.setText(conversionResult.toString(), TextView.BufferType.EDITABLE)
    }

    override fun updateKeyboard(keyboardModelList: MutableList<KeyboardModel>) =
        keyboardAdapter.setItems(keyboardModelList)

    override fun updateCurrencyCodeList(currencyCodeList: List<Pair<String, Double>>) =
        currencyCodeAdapter.setItems(currencyCodeList)

    override fun setupDot() {
        when {
            vEtPrimaryValue.isFocused -> {
                if (isDotInputValid(vEtPrimaryValue.text.toString())) {
                    vEtPrimaryValue.append(DOT)
                }
            }

            vEtSecondaryValue.isFocused -> {
                if (isDotInputValid(vEtSecondaryValue.text.toString())) {
                    vEtSecondaryValue.append(DOT)
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

    override fun clearValues() {
        vEtPrimaryValue.text.clear()
        vEtSecondaryValue.text.clear()
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

    override fun switchRateValues() {
        val primaryValue = vEtPrimaryValue.text.toString()
        val secondaryValue = vEtSecondaryValue.text.toString()

        vEtPrimaryValue.setText(secondaryValue, TextView.BufferType.EDITABLE)
        vEtSecondaryValue.setText(primaryValue, TextView.BufferType.EDITABLE)
    }

    override fun toggleCurrencyCodeList() = vCurrencyCodeListBottomSheet.toggle()

}