package com.example.viewmodelsample.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.viewmodelsample.R
import com.example.viewmodelsample.databinding.ActivityMainBinding
import com.example.viewmodelsample.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {

        private const val MY_PARAM = "myParam"
    }

    /**
     * original
     */
    private val countViewModel1 by lazy {
        ViewModelProvider(this).get(CountViewModel::class.java)
    }

    private val countViewModelWithParam1 by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CountViewModelWithParam("MainActivity") as T
            }
        }).get(CountViewModelWithParam::class.java)
    }

    private val savedStateViewModel1 by lazy {
        ViewModelProvider(
            this,
            SavedStateViewModelFactory(application, this, intent.extras)
        ).get(CountSavedStateViewModel::class.java)
    }

    private val savedStateViewModelWithParam1 by lazy {
        ViewModelProvider(this, object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                return CountSavedStateViewModelWithParam(handle, "MainActivity") as T
            }
        }).get(CountSavedStateViewModelWithParam::class.java)
    }

    /**
     * androidx
     */
    private val countViewModel2 by viewModels<CountViewModel>()

    private val countViewModelWithParam2 by viewModels<CountViewModelWithParam> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CountViewModelWithParam("MainActivity") as T
            }
        }
    }

    private val savedStateViewModel2 by viewModels<CountSavedStateViewModel> {
        SavedStateViewModelFactory(application, this, intent.extras)
    }

    private val savedStateViewModelWithParam2 by viewModels<CountSavedStateViewModelWithParam> {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                return CountSavedStateViewModelWithParam(handle, "MainActivity") as T
            }
        }
    }

    /**
     * koin
     */
    private val mainViewModel3 by viewModel<CountViewModel>()

    private val mainViewModelWithParam3 by viewModel<CountViewModelWithParam> {
        parametersOf("MainActivity")
    }

    private val savedStateViewModel3 by stateViewModel<CountSavedStateViewModel>()

    internal class SavedStateViewModel(val savedStateHandle: SavedStateHandle) : ViewModel()

    private val savedStateViewModelWithParam3 by stateViewModel<CountSavedStateViewModelWithParam> {
        parametersOf("MainActivity")
    }


    /**
     * hilt
     */
    private val mainViewModel4 by viewModels<HiltViewModel>()

    private val savedStateViewModel4 by viewModels<HiltSavedStateViewModel>()



    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initFragment()

        binding.btnPlus.setOnClickListener {
            savedStateViewModel2.plusCount()
        }

        binding.btnShow.setOnClickListener {
            savedStateViewModel2.showCountLog()
        }

        binding.btnClear.setOnClickListener {
            savedStateViewModel2.clear()
        }

        binding.btnMain.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java).apply {
                    putExtra(MY_PARAM, "hello blackjin from activity")
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
            )
        }

        Log.d("MyTag", "MainActivity onCreate MY_PARAM : ${intent?.getStringExtra(MY_PARAM)}")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("MyTag", "MainActivity onNewIntent MY_PARAM : ${intent?.getStringExtra(MY_PARAM)}")
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MyTag", "MainActivity onSaveInstanceState bundle : $outState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("MyTag", "MainActivity onViewStateRestored bundle : $savedInstanceState")
    }
}