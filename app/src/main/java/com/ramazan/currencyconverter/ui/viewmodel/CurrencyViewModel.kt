package com.ramazan.currencyconverter.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramazan.common.Resource
import com.ramazan.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val _currencyNames = MutableStateFlow<Resource<List<String>>>(Resource.Loading())
    val currencyNames: StateFlow<Resource<List<String>>> = _currencyNames

    private val _conversionResult = MutableStateFlow<Resource<String>>(Resource.Success(""))
    val conversionResult: StateFlow<Resource<String>> = _conversionResult

    init {
        loadCurrencyArray()
    }

    fun loadCurrencyArray() {
        repository.getRates("USD")
            .onEach { result ->
                _currencyNames.value = result
                Log.d("CurrencyViewModel", "Currency names loaded: ${result.data}")
            }
            .launchIn(viewModelScope)
    }

    fun convert(from: String, to: String, amountStr: String) {
        val amount = amountStr.toDoubleOrNull()
        if (amount == null) {
            _conversionResult.value = Resource.Error("Неверная сумма")
            return
        }

        viewModelScope.launch {
            _conversionResult.value = Resource.Loading()
            try {
                val rate = repository.getSingleRate(from, to)
                val resultValue = amount * rate
                _conversionResult.value = Resource.Success(String.format("%.2f %s", resultValue, to))
            } catch (e: Exception) {
                _conversionResult.value = Resource.Error("Ошибка конвертации")
            }
        }
    }
}
