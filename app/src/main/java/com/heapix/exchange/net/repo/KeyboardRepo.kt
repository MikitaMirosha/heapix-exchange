package com.heapix.exchange.net.repo

import com.heapix.exchange.model.KeyboardModel

class KeyboardRepo {

    fun getKeyNumbers(): MutableList<KeyboardModel> {
        val keyboardModelList = mutableListOf<KeyboardModel>()

        (1..9).forEach { keyNumber ->
            keyboardModelList.add((KeyboardModel(keyNumber)))
        }
        return keyboardModelList
    }
}