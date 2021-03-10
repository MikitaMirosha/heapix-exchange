package com.heapix.exchange.utils.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtils {

    companion object {
        fun getSharedPreferences(context: Context) =
            context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)!!

        private const val PREFERENCES = "PREFERENCES"

//        const val TIME_LAST_UPDATE_UTC: String = "TIME_LAST_UPDATE_UTC"
    }
}

inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
    val editMe = edit()
    operation(editMe)
    editMe.apply()
}

//var SharedPreferences.timeLastUpdateUtc: String
//    get() = getString(PreferencesUtils.TIME_LAST_UPDATE_UTC, "") ?: ""
//    set(value) = editMe { it.putString(PreferencesUtils.TIME_LAST_UPDATE_UTC, value) }
