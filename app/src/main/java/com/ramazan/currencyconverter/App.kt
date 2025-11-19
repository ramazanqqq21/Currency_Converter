package com.ramazan.currencyconverter

import android.app.Application
import androidx.room.Room
import com.ramazan.data.db.CurrencyDatabase

class App : Application() {
    lateinit var database: CurrencyDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            CurrencyDatabase::class.java,
            "currency_db"
        ).build()
    }
}
