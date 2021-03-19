package com.heapix.exchange.net.responses

import com.google.gson.annotations.SerializedName

class ExchangeRatesResponse(
    @SerializedName("conversion_rates")
    val conversionRates: Map<String, Double>? = mutableMapOf()
) : ExchangeResponse()