package com.example.databindingdemo.ui.moduletwo.fragment

import android.app.Application
import com.example.databindingdemo.base.BaseViewModel
import com.example.databindingdemo.ni.repository.MainApiRepository
import com.example.databindingdemo.ni.retrofit.MainApiInterface

class ModTwoViewModel(application: Application, val mainApiInterface: MainApiInterface) :
    BaseViewModel<IModelTwoNavigation>() {
    private val mainApiRepository: MainApiRepository

    init {
        mainApiRepository = MainApiRepository(application, mainApiInterface)
    }
}
