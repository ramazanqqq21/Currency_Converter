package com.ramazan.currencyconverter.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramazan.data.repository.CurrencyRepository
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: CurrencyRepository): ViewModel() {
    private var currencyListNames = MutableLiveData<List<String>>()
    fun getCurrencyList(): MutableLiveData<List<String>> = currencyListNames

    private var currencyConvert = MutableLiveData<String>()
    fun getCurrencyConvert(): MutableLiveData<String> = currencyConvert




    init{
        loadCurrencyArray()
    }

    fun loadCurrencyArray(){
        viewModelScope.launch {
            try{
                val response = repository.fetchAndSaveRates("USD")
                Log.i("RETROFIT", "loadCurrencyArray: $response")

                val saveRates = repository.getRatesFromDb()

                currencyListNames.value = saveRates.map { it.currency }
            }
            catch (e: Exception){
                Log.e("ERROR RETROFIT", "$e")
            }
        }
    }



    fun convert(from: String, to: String, amount: Double){
        viewModelScope.launch {
            try{
                val response = repository.getRates(from)
                val rates = response.conversionRates[to]
                Log.i("RETROFIT", "convert: $rates")
                currencyConvert.value = "Результат конвертации: ${amount * (rates ?: 0.0)}"
            }
            catch (e: Exception){
                Log.i("ERROR RETROFIT", "convert: $e")
            }
        }
    }


}