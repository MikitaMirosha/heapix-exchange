package com.heapix.exchange.net.responses

import com.google.gson.annotations.SerializedName

data class PairExchangeResponse(
    @SerializedName("base_code")                val baseCode:               String? = null,
    @SerializedName("target_code")              val targetCode:             String? = null,

    @SerializedName("conversion_rate")          val conversionRate:         Double? = null,
    @SerializedName("conversion_result")        val conversionResult:       Double? = null
)