package com.ramazan.data.api

import com.ramazan.data.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest/{base}")
    suspend fun getRates(@Query("base") base: String): CurrencyResponse
}