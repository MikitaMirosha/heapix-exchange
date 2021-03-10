package com.heapix.exchange.net.repo

import android.content.SharedPreferences
import com.heapix.exchange.net.responses.StandardExchangeResponse
import com.heapix.exchange.net.services.StandardExchangeService
import io.reactivex.Observable
import java.math.BigDecimal

class StandardExchangeRepo(
    private val api: StandardExchangeService,
    private val sharedPreferences: SharedPreferences
) {

    fun getStandardResponse(): Observable<StandardExchangeResponse> {
        return api.getStandardResponse()
    }

    fun getConversionRates(): Observable<MutableList<Map<String, BigDecimal>?>> {
        return api.getStandardResponse().map {
            mutableListOf(it.conversionRates)
        }
    }

//    fun getConversionRates(): Observable<MutableList<StandardExchangeResponse>> {
//        return api.getStandardResponse().map {
//            it.
//        }
//    }
}