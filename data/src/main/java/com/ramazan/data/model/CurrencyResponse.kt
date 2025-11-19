package com.ramazan.data.model

data class CurrencyResponse (
    val result: String,
    val baseCode: String,
    val conversionRates: Map<String, Double>,
){}
