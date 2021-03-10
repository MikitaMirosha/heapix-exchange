package com.heapix.exchange.net.repo

import com.heapix.exchange.model.KeyboardModel

class KeyboardRepo {

    fun getAllKeyNumbers(): MutableList<KeyboardModel> {
        val keyboardModelList = mutableListOf<KeyboardModel>()

        (1..9).forEach { keyNumber ->
            mutableListOf<KeyboardModel>().add((KeyboardModel(keyNumber)))
        }
        return keyboardModelList
    }
}