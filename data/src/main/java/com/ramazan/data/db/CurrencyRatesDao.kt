package com.ramazan.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<CurrencyRatesEntity>)

    @Query("SELECT * FROM currency_rates")
    fun getAllRates(): Flow<List<CurrencyRatesEntity>>
}