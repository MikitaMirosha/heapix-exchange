package com.heapix.exchange.ui.converting

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.exchange.R
import com.heapix.exchange.base.BaseMvpActivity
import com.heapix.exchange.model.KeyboardModel
import com.heapix.exchange.ui.converting.adapter.KeyboardAdapter
import kotlinx.android.synthetic.main.view_keyboard.*

class ConvertingActivity : BaseMvpActivity(), ConvertingView {

    @InjectPresenter
    lateinit var convertingPresenter: ConvertingPresenter

    lateinit var keyboardAdapter: KeyboardAdapter

    override fun getLayoutId(): Int = R.layout.activity_converting

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        setupKeyboardAdapter()

        convertingPresenter.onCreate(keyboardAdapter.keyboardButtonClickObservable)
    }

    private fun setupKeyboardAdapter() {
        keyboardAdapter = KeyboardAdapter()
        vRvKeyboardButtons.adapter = keyboardAdapter
    }

    override fun updateKeyboard(keyboardModelList: MutableList<KeyboardModel>) =
        keyboardAdapter.setItems(keyboardModelList)

}