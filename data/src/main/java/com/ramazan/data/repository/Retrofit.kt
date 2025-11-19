package com.ramazan.data.repository

import com.ramazan.data.api.CurrencyApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{
    private const val API_KEY = "2e7d10e00f38ce846b590ef6"
    private const val BASE_URL = "https://v6.exchangerate-api.com/v6/$API_KEY/"

    val api: CurrencyApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

}