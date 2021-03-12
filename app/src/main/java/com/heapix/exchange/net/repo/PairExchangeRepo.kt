package com.heapix.exchange.net.repo

import android.content.SharedPreferences
import com.heapix.exchange.net.responses.PairExchangeResponse
import com.heapix.exchange.net.services.PairExchangeService
import com.heapix.exchange.utils.preferences.baseCode
import com.heapix.exchange.utils.preferences.targetCode
import io.reactivex.Observable

class PairExchangeRepo(
    private val api: PairExchangeService,
    private val sharedPreferences: SharedPreferences
) {

    fun getPairResponse(
        baseCode: String?,
        targetCode: String?,
        conversionResult: Double?
    ): Observable<PairExchangeResponse> {
        return api.getPairResponse(
            baseCode,
            targetCode,
            conversionResult
        )
    }

    fun getBaseCode(): String = sharedPreferences.baseCode

    fun saveBaseCode(baseCode: String?) {
        sharedPreferences.baseCode = baseCode ?: ""
    }

    fun getTargetCode(): String = sharedPreferences.targetCode

    fun saveTargetCode(targetCode: String?) {
        sharedPreferences.targetCode = targetCode ?: ""
    }

}