package com.heapix.exchange.ui.converting.adapter

import android.view.View
import com.heapix.exchange.base.adapters.BaseViewHolder
import com.heapix.exchange.model.KeyboardModel
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_keyboard_button.*

class KeyboardViewHolder(
    itemView: View,
    private val keyboardPublishSubject: PublishSubject<KeyboardModel>
) : BaseViewHolder<KeyboardModel>(itemView) {

    override fun bind(model: KeyboardModel) {
        setupKeyboardNumber(model)
        setupClickListener(model)
    }

    private fun setupKeyboardNumber(keyboardModel: KeyboardModel) {
        vTvKeyboardNumber.text = keyboardModel.keyboardNumber.toString()
    }

    private fun setupClickListener(keyboardModel: KeyboardModel) {
        setOnClickListener {
            keyboardPublishSubject.onNext(keyboardModel)
        }
    }
}