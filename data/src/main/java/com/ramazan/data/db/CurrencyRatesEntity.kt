package com.ramazan.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rates")
data class CurrencyRatesEntity (
    @PrimaryKey
    val currency: String,
    val rate: Double
) {}