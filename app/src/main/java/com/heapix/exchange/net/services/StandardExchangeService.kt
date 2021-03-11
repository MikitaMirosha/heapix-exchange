package com.heapix.exchange.net.services

import com.heapix.exchange.net.responses.StandardExchangeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface StandardExchangeService {

    @GET("latest/{base_code}")
    fun getStandardResponse(
        @Path("base_code") baseCode: String?
    ): Observable<StandardExchangeResponse>

    @GET("latest/{base_code}")
    fun getStandardResponseTime(
        @Path("base_code") baseCode: String?
    ): Observable<StandardExchangeResponse>
}