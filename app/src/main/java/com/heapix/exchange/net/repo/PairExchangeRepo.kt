package com.heapix.exchange.net.repo

import android.content.SharedPreferences
import com.heapix.exchange.net.responses.PairExchangeResponse
import com.heapix.exchange.net.services.PairExchangeService
import com.heapix.exchange.utils.preferences.targetCode
import io.reactivex.Observable

class PairExchangeRepo(
    private val api: PairExchangeService,
    private val sharedPreferences: SharedPreferences
) {

    fun getPairExchange(
        baseCode: String,
        targetCode: String
    ): Observable<PairExchangeResponse> {
        return api.getPairExchange(
            baseCode,
            targetCode
        )
    }

    fun getTargetCode(): String = sharedPreferences.targetCode

    fun saveTargetCode(targetCode: String) {
        sharedPreferences.targetCode = targetCode
    }

    fun isTargetCodeInStorage(): Boolean = getTargetCode() != ""

}