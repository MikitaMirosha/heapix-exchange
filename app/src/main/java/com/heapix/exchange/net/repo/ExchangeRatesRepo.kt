package com.heapix.exchange.net.repo

import android.content.SharedPreferences
import com.heapix.exchange.net.responses.ExchangeRatesResponse
import com.heapix.exchange.net.services.ExchangeRatesService
import com.heapix.exchange.utils.preferences.baseCode
import io.reactivex.Observable

class ExchangeRatesRepo(
    private val api: ExchangeRatesService,
    private val sharedPreferences: SharedPreferences
) {

    fun getExchangeRates(baseCode: String): Observable<ExchangeRatesResponse> {
        return api.getExchangeRates(baseCode)
    }

    fun getBaseCode(): String = sharedPreferences.baseCode

    fun saveBaseCode(baseCode: String) {
        sharedPreferences.baseCode = baseCode
    }

    fun isBaseCodeInStorage(): Boolean = getBaseCode() != ""
}