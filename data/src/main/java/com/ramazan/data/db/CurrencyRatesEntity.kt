package com.ramazan.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rate")
data class CurrencyRatesEntity (
    @PrimaryKey
    val currency: String,
    val rate: Double
) {}