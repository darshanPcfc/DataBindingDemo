package com.example.databindingdemo.di

import com.example.databindingdemo.ui.MainActivityViewModel
import com.example.databindingdemo.ui.moduleone.fragment.SearchViewModel
import com.example.databindingdemo.ui.moduletwo.activities.ModelTwoActivityViewModel
import com.example.databindingdemo.ui.moduletwo.fragment.ModTwoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Darshan Patel 24/02/2020
 * Usage:
 * How to call:
 * Useful parameter:
 */
val viewModelsInjection = module {
    viewModel { MainActivityViewModel(androidApplication(), get()) }
    viewModel { ModelTwoActivityViewModel(androidApplication(), get()) }
    viewModel { ModTwoViewModel(androidApplication(), get()) }
    viewModel { SearchViewModel(androidApplication(), get()) }
}