package com.heapix.exchange.net.responses

import com.google.gson.annotations.SerializedName

data class PairExchangeResponse(
    val result: String? = null,
    val documentation: String? = null,

    @SerializedName("terms_of_use")             val termsOfUse:             String? = null,

    @SerializedName("time_last_update_unix")    val timeLastUpdateUnix:     Long? = null,
    @SerializedName("time_last_update_utc")     val timeLastUpdateUtc:      String? = null,
    @SerializedName("time_next_update_unix")    val timeNextUpdateUnix:     Long? = null,
    @SerializedName("time_next_update_utc")     val timeNextUpdateUtc:      String? = null,

    @SerializedName("base_code")                val baseCode:               String? = null,
    @SerializedName("target_code")              val targetCode:             String? = null,

    @SerializedName("conversion_rate")          val conversionRate:         Double? = null,
    @SerializedName("conversion_result")        val conversionResult:       Double? = null
)