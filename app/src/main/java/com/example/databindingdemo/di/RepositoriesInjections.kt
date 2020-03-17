package com.example.databindingdemo.di

import com.example.databindingdemo.ni.repository.MainApiRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Darshan Patel
 * Usage:
 * How to call:
 * Useful parameter:
 */

val repositoriesInjection = module {
    single { MainApiRepository(androidContext(), mainApiInterceptor = get()) }
}