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
        setupKeyNumber(model)
        setupClickListener(model)
    }

    private fun setupKeyNumber(keyboardModel: KeyboardModel) {
        vTvKeyNumber.text = keyboardModel.keyNumber.toString()
    }

    private fun setupClickListener(keyboardModel: KeyboardModel) {
        setOnClickListener {
            keyboardPublishSubject.onNext(keyboardModel)
        }
    }
}