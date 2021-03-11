package com.heapix.exchange.ui.converting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.exchange.R
import com.heapix.exchange.base.adapters.BaseListAdapter
import com.heapix.exchange.base.adapters.BaseViewHolder
import com.heapix.exchange.model.KeyboardModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class KeyboardAdapter : BaseListAdapter<KeyboardModel>() {

    private val keyboardPublishSubject: PublishSubject<KeyboardModel> = PublishSubject.create()
    val keyboardButtonClickObservable: Observable<KeyboardModel> = keyboardPublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<KeyboardModel> {
        return KeyboardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_keyboard_button, parent, false),
            keyboardPublishSubject
        )
    }

}