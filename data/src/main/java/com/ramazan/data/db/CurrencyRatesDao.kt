package com.ramazan.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<CurrencyRatesEntity>)

    @Query("SELECT * FROM currency_rates")
    suspend fun getAllRates(): List<CurrencyRatesEntity>
}