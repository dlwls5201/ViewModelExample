package com.example.viewmodelsample.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class CountViewModel : ViewModel() {

    private var count = 0

    fun plusCount() {
        ++count
    }

    fun showCountLog() {
        Log.d("MyTag", "count : $count")
    }
}