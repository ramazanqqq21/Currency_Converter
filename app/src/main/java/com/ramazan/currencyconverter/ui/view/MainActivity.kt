package com.ramazan.currencyconverter.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ramazan.currencyconverter.App
import com.ramazan.currencyconverter.R
import com.ramazan.currencyconverter.databinding.ActivityMainBinding
import com.ramazan.currencyconverter.ui.viewmodel.CurrencyViewModel
import com.ramazan.common.Resource

import com.ramazan.data.repository.CurrencyRepository
import com.ramazan.data.repository.RetrofitInstance

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val dao = (application as App).database.currencyRatesDao()
        val repository = CurrencyRepository(RetrofitInstance.api, dao)
        viewModel = CurrencyViewModel(repository)


        binding.btnCurrency.setOnClickListener {
            val fromCurrency = binding.spinnerCurrency1.selectedItem.toString()
            val toCurrency = binding.spinnerCurrency2.selectedItem.toString()
            val amount = binding.etSum.text.toString()

            viewModel.convert(fromCurrency, toCurrency, amount)
        }


        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.currencyNames.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { currencies ->
                            val adapter =
                                ArrayAdapter(this@MainActivity,
                                    android.R.layout.simple_spinner_item,
                                    currencies)
                            binding.spinnerCurrency1.adapter = adapter
                            binding.spinnerCurrency2.adapter = adapter
                        }
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.conversionResult.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE // Можно использовать отдельный ProgressBar для результата
                        binding.twResult.visibility = View.GONE // Скрываем старый результат
                        binding.btnCurrency.isEnabled = false // Блокируем кнопку, пока идет расчет
                    }

                    is Resource.Success -> {
                        // 2. Показываем успешный результат
                        binding.progressBar.visibility = View.GONE
                        binding.twResult.visibility = View.VISIBLE
                        binding.btnCurrency.isEnabled = true

                        // Устанавливаем итоговое значение в TextView
                        binding.twResult.text = resource.data
                    }

                    is Resource.Error -> {
                        // 3. Показываем ошибку
                        binding.progressBar.visibility = View.GONE
                        binding.twResult.visibility = View.GONE
                        binding.btnCurrency.isEnabled = true

                        Toast.makeText(this@MainActivity, resource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }


}


