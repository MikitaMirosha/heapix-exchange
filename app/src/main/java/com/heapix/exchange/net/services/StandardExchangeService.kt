package com.heapix.exchange.net.services

import com.heapix.exchange.net.responses.StandardExchangeResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface StandardExchangeService {

    @GET("latest/USD")
    fun getStandardResponse(): Observable<StandardExchangeResponse>
}