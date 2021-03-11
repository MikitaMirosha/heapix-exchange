package com.heapix.exchange.net.repo

import android.content.SharedPreferences
import com.heapix.exchange.net.responses.StandardExchangeResponse
import com.heapix.exchange.net.services.StandardExchangeService
import com.heapix.exchange.utils.preferences.baseCode
import io.reactivex.Observable

class StandardExchangeRepo(
    private val api: StandardExchangeService,
    private val sharedPreferences: SharedPreferences
) {

    fun getStandardResponse(baseCode: String?): Observable<List<Pair<String, Double>>> {
        return api.getStandardResponse(baseCode).map {
            it.conversionRates?.toList()
        }
    }

    fun getStandardResponseTime(baseCode: String?): Observable<StandardExchangeResponse> {
        return api.getStandardResponseTime(baseCode)
    }

    fun getBaseCode(): String = sharedPreferences.baseCode

    fun saveBaseCode(baseCode: String?) {
        sharedPreferences.baseCode = baseCode ?: ""
    }
}