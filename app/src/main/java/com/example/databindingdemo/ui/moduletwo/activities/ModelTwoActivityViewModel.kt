package com.example.databindingdemo.ui.moduletwo.activities

import android.app.Application
import com.example.databindingdemo.base.BaseViewModel
import com.example.databindingdemo.ni.repository.MainApiRepository
import com.example.databindingdemo.ni.retrofit.MainApiInterface

class ModelTwoActivityViewModel(application: Application, val mainApiInterface: MainApiInterface) :
    BaseViewModel<IModelTwoNavigator>()  {
    private val mainApiRepository: MainApiRepository

    init {
        mainApiRepository = MainApiRepository(application, mainApiInterface)
    }
}