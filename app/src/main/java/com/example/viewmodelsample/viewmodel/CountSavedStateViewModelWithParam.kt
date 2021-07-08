package com.example.viewmodelsample.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CountSavedStateViewModelWithParam(
    private val handle: SavedStateHandle,
    private val name: String
) : ViewModel() {

    companion object {
        private const val COUNTER = "counter"
    }

    private var count1 = 0

    private val count2 get() = handle.get<Int>(COUNTER) ?: 0

    //private val countLiveData = handle.getLiveData<Int>(COUNTER)

    fun plusCount() {
        ++count1
        handle.set(COUNTER, count2 + 1)
    }

    fun showCountLog() {
        Log.d("MyTag", "-------showCountLog-------")
        Log.d("MyTag", "count1 : $count1, count2 : $count2")
        handle.keys().forEach {
            Log.d("MyTag", "key : $it, value : ${handle.get<Any>(it)}")
        }
    }

    fun clear() {
        Log.d("MyTag", "-------clear------")
        handle.keys().forEach {
            Log.d("MyTag", "delete key : $it")
            handle.remove<Any>(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MyTag", "CountSavedStateViewModel onCleared")
    }
}