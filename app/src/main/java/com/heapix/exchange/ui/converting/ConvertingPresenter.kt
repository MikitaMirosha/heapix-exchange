package com.heapix.exchange.ui.converting

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.exchange.MyApp
import com.heapix.exchange.base.BaseMvpPresenter
import com.heapix.exchange.model.KeyboardModel
import com.heapix.exchange.net.repo.KeyboardRepo
import io.reactivex.Observable
import org.kodein.di.instance

@InjectViewState
class ConvertingPresenter : BaseMvpPresenter<ConvertingView>() {

    private val keyboardRepo: KeyboardRepo by MyApp.kodein.instance()

    fun onCreate(keyboardButtonClickObservable: Observable<KeyboardModel>) {
        getKeyboardAndUpdateUi()

        setupOnKeyboardButtonClickListener(keyboardButtonClickObservable)
    }

    private fun getKeyboardAndUpdateUi() {
        viewState.updateKeyboard(keyboardRepo.getAllKeyNumbers())
    }

    private fun setupOnKeyboardButtonClickListener(keyboardButtonClickObservable: Observable<KeyboardModel>) {
        addDisposable(
            keyboardButtonClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        it.keyboardNumber?.let { keyNumber -> showMessage(keyNumber) }
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

}