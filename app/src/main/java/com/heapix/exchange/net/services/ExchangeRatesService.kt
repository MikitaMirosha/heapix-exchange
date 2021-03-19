package com.heapix.exchange.net.services

import com.heapix.exchange.net.responses.ExchangeRatesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRatesService {

    @GET("latest/{base_code}")
    fun getExchangeRates(
        @Path("base_code") baseCode: String?
    ): Observable<ExchangeRatesResponse>
}