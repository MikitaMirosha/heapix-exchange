package com.heapix.exchange.utils.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtils {

    companion object {
        fun getSharedPreferences(context: Context) =
            context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)!!

        private const val PREFERENCES = "PREFERENCES"

        const val BASE_CODE: String = "BASE_CODE"
    }
}

inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
    val editMe = edit()
    operation(editMe)
    editMe.apply()
}

var SharedPreferences.baseCode: String
    get() = getString(PreferencesUtils.BASE_CODE, "") ?: ""
    set(value) = editMe { it.putString(PreferencesUtils.BASE_CODE, value) }
