package com.example.viewmodelsample.koin

import androidx.lifecycle.SavedStateHandle
import com.example.viewmodelsample.viewmodel.CountSavedStateViewModel
import com.example.viewmodelsample.viewmodel.CountSavedStateViewModelWithParam
import com.example.viewmodelsample.viewmodel.CountViewModel
import com.example.viewmodelsample.viewmodel.CountViewModelWithParam
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { CountViewModel() }

    viewModel { CountViewModelWithParam(it[0]) }

    viewModel { CountSavedStateViewModel(it[0]) }

    viewModel { CountSavedStateViewModelWithParam(it[0], it[1]) }
}