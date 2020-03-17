package com.example.databindingdemo.ui.moduleone.fragment

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.databindingdemo.base.BaseViewModel
import com.example.databindingdemo.ni.remote.response.MainApiResponse
import com.example.databindingdemo.ni.repository.MainApiRepository
import com.example.databindingdemo.ni.retrofit.MainApiInterface

class SearchViewModel(application: Application, val mainApiInterface: MainApiInterface) :
    BaseViewModel<ISearchObserver>() {

    private val mainApiRepository: MainApiRepository

    init {
        mainApiRepository = MainApiRepository(application, mainApiInterface)
    }

    fun callmainApi(): LiveData<MainApiResponse> {
        return mainApiRepository.validateMpin()
    }

    fun callApiData() {
        try {
            getNavigator()?.setData()
        }catch (e : Throwable){
            e.printStackTrace()
        }

    }
}