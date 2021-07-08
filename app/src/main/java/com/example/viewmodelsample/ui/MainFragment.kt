package com.example.viewmodelsample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.example.viewmodelsample.R
import com.example.viewmodelsample.databinding.FragmentMainBinding
import com.example.viewmodelsample.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {

        private const val MY_PARAM = "myParam"

        fun newInstance() = MainFragment().apply {
            arguments = bundleOf(MY_PARAM to "hello blackjin from fragment")
        }
    }

    /**
     * original
     */
    private val countViewModel1 by lazy {
        ViewModelProvider(this).get(CountViewModel::class.java)
    }

    private val sharedCountViewModel1 by lazy {
        ViewModelProvider(requireActivity()).get(CountViewModel::class.java)
    }

    private val countViewModelWithParam1 by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CountViewModelWithParam("MainFragment") as T
            }
        }).get(CountViewModelWithParam::class.java)
    }

    private val sharedCountViewModelWithParam1 by lazy {
        ViewModelProvider(requireActivity()).get(CountViewModelWithParam::class.java)
    }


    private val savedStateViewModel1 by lazy {
        ViewModelProvider(
            this,
            SavedStateViewModelFactory(activity?.application, this, arguments)
        ).get(CountSavedStateViewModel::class.java)
    }

    private val sharedSavedStateViewModel1 by lazy {
        ViewModelProvider(
            requireActivity(),
            SavedStateViewModelFactory(activity?.application, this, arguments)
        ).get(CountSavedStateViewModel::class.java)
    }

    private val savedStateViewModelWithParam1 by lazy {
        ViewModelProvider(this, object : AbstractSavedStateViewModelFactory(this, arguments) {
            override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                return CountSavedStateViewModelWithParam(handle, "MainFragment") as T
            }
        }).get(CountSavedStateViewModelWithParam::class.java)
    }


    /**
     * androidx
     */
    private val countViewModel2 by viewModels<CountViewModel>()

    private val sharedCountViewModel2 by activityViewModels<CountViewModel>()

    private val countViewModelWithParam2 by viewModels<CountViewModelWithParam> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CountViewModelWithParam("MainFragment") as T
            }
        }
    }

    private val sharedCountViewModelWithParam2 by activityViewModels<CountViewModelWithParam>()

    private val savedStateViewModel2 by viewModels<CountSavedStateViewModel> {
        SavedStateViewModelFactory(activity?.application, this, arguments)
    }


    private val savedStateViewModelWithParam2 by viewModels<CountSavedStateViewModelWithParam> {
        object : AbstractSavedStateViewModelFactory(this, arguments) {
            override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                return CountSavedStateViewModelWithParam(handle, "MainFragment") as T
            }
        }
    }


    /**
     * koin
     */
    private val mainViewModel3 by viewModel<CountViewModel>()

    private val sharedCountViewModel3 by sharedViewModel<CountViewModel>()

    private val mainViewModelWithParam3 by viewModel<CountViewModelWithParam> {
        parametersOf("MainFragment")
    }

    private val savedStateViewModel3 by stateViewModel<CountSavedStateViewModel>()

    private val savedStateViewModelWithParam3 by stateViewModel<CountSavedStateViewModelWithParam> {
        parametersOf("MainFragment")
    }


    /**
     * hilt
     */
    private val mainViewModel4 by viewModels<HiltViewModel>()

    private val sharedCountViewModel4 by activityViewModels<HiltViewModel>()

    private val savedStateViewModel4 by viewModels<HiltSavedStateViewModel>()



    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPlus.setOnClickListener {
            savedStateViewModel2.plusCount()
        }

        binding.btnShow.setOnClickListener {
            savedStateViewModel2.showCountLog()
        }

        binding.btnClear.setOnClickListener {
            savedStateViewModel2.clear()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MyTag", "MainFragment onSaveInstanceState bundle : $outState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("MyTag", "MainFragment onViewStateRestored bundle : $savedInstanceState")
    }
}