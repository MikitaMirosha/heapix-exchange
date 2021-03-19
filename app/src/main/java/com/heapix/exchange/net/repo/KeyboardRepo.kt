package com.heapix.exchange.net.repo

import com.heapix.exchange.model.KeyboardModel

class KeyboardRepo {

    companion object {
        private const val MIN_KEY_NUMBER = 1
        private const val MAX_KEY_NUMBER = 9
    }

    fun getKeyNumbers(): MutableList<KeyboardModel> {
        val keyboardModelList = mutableListOf<KeyboardModel>()

        (MIN_KEY_NUMBER..MAX_KEY_NUMBER).forEach { keyNumber ->
            keyboardModelList.add((KeyboardModel(keyNumber)))
        }
        return keyboardModelList
    }
}