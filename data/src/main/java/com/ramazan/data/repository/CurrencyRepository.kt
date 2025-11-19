package com.ramazan.data.repository

import com.ramazan.data.api.CurrencyApi
import com.ramazan.data.db.CurrencyRatesDao
import com.ramazan.data.db.CurrencyRatesEntity
import com.ramazan.data.model.CurrencyResponse

class CurrencyRepository(
    private val api : CurrencyApi,
    private val dao : CurrencyRatesDao,
){
    suspend fun fetchAndSaveRates(baseCode: String){
        val response = api.getRates(baseCode)

        val ratesForDb = response.conversionRates.map {
            (currencyCode, rate) ->
            CurrencyRatesEntity(currencyCode, rate)
        }

        dao.insertRates(ratesForDb)
    }

    suspend fun getRatesFromDb(): List<CurrencyRatesEntity>{
        return dao.getAllRates()
    }
    suspend fun getRates(baseCode: String): CurrencyResponse{
        return RetrofitInstance.api.getRates(baseCode)
    }
}