package com.ramazan.currencyconverter.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ramazan.currencyconverter.App
import com.ramazan.currencyconverter.R
import com.ramazan.currencyconverter.databinding.ActivityMainBinding
import com.ramazan.currencyconverter.ui.viewmodel.CurrencyViewModel
import com.ramazan.data.repository.CurrencyRepository
import com.ramazan.data.repository.RetrofitInstance

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


        }

    }