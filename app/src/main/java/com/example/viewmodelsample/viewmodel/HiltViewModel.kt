package com.example.viewmodelsample.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class HiltViewModel @ViewModelInject constructor() : ViewModel() {

    private var count = 0

    fun plusCount() {
        ++count
    }

    fun showCountLog() {
        Log.d("MyTag", "count : $count")
    }
}