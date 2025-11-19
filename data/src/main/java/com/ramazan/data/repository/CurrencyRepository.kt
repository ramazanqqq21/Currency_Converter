package com.ramazan.data.repository

import android.util.Log
import com.ramazan.common.Resource
import com.ramazan.data.api.CurrencyApi
import com.ramazan.data.db.CurrencyRatesDao
import com.ramazan.data.db.CurrencyRatesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrencyRepository(
    private val api: CurrencyApi,
    private val dao: CurrencyRatesDao,
) {
    fun getRates(baseCurrency: String): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.getRates(baseCurrency)
            Log.d("CurrencyRepository", "Response: $response")
            val ratesForDb = response.conversionRates.map { (code, rate) ->
                CurrencyRatesEntity(currency = code, rate = rate)
            }
            dao.insertRates(ratesForDb)

        } catch (e: Exception) {
            emit(Resource.Error("Ошибка сети: не удалось обновить данные. ${e.message}"))
        }

        dao.getAllRates().collect { dbRates ->
            val currencyCodes = dbRates.map { it.currency }
            emit(Resource.Success(currencyCodes))
        }
    }

    suspend fun getSingleRate(from: String, to: String): Double {
        val response = api.getRates(from)
        return response.conversionRates[to] ?: 0.0
    }
}
