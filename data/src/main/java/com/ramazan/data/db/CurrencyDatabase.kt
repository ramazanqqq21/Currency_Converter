package com.ramazan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyRatesEntity::class], version = 1)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyRatesDao(): CurrencyRatesDao
}

