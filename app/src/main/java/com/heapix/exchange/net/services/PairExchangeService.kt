package com.heapix.exchange.net.services

import com.heapix.exchange.net.responses.PairExchangeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PairExchangeService {

    @GET("pair/{base_code}/{target_code}")
    fun getPairExchange(
        @Path("base_code") baseCode: String?,
        @Path("target_code") targetCode: String?
    ): Observable<PairExchangeResponse>
}
