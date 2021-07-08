package com.example.viewmodelsample.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class CountViewModelWithParam(
    private val name: String
) : ViewModel() {

    private var count = 0

    fun plusCount() {
        ++count
    }

    fun showCountLog() {
        Log.d("MyTag", "name : $name, count : $count")
    }
}