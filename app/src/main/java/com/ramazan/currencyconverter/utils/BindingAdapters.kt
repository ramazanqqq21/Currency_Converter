package com.ramazan.currencyconverter.utils

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.ramazan.common.Resource
import kotlinx.coroutines.flow.StateFlow

@BindingAdapter("entries")
fun Spinner.bindEntries(data: StateFlow<Resource<List<String>>>?) {
    val resource = data?.value

    if (resource is Resource.Success) {
        val entries = resource.data
        if (entries != null) {
            val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, entries)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = arrayAdapter
            this.visibility = View.VISIBLE
        }
        else {
            this.visibility = View.GONE
        }
    }
    else {
        this.visibility = View.GONE
    }
}

