package com.ramazan.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse (
    val result: String,
    @SerializedName("base_code")
    val baseCode: String,
    @SerializedName("conversion_rates")
    val conversionRates: Map<String, Double>,
){}
