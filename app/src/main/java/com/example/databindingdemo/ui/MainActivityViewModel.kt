package com.example.databindingdemo.ui

import android.app.Application
import android.os.Handler
import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.databindingdemo.base.BaseApplication
import com.example.databindingdemo.base.BaseViewModel
import com.example.databindingdemo.ni.remote.response.MainApiResponse
import com.example.databindingdemo.ni.remote.response.Users
import com.example.databindingdemo.ni.repository.MainApiRepository
import com.example.databindingdemo.ni.retrofit.MainApiInterface
import com.example.databindingdemo.ui.moduleone.activities.IMainActivityNavigator
import com.example.databindingdemo.ui.moduleone.activities.viewmodel.UserViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application, val mainApiInterface: MainApiInterface) :
    BaseViewModel<IMainActivityNavigator>() {

    private val mainApiRepository: MainApiRepository
    private var search = ""

    init {
        mainApiRepository = MainApiRepository(application, mainApiInterface)
    }

    fun callmainApi(): LiveData<MainApiResponse> {
        return mainApiRepository.validateMpin()
    }

    fun insertInsideUser(userList: List<Users>) {
        val userViewModel = UserViewModel(BaseApplication.instance!!)
        GlobalScope.launch {
            userList.forEach {
                userViewModel.insert(it)
            }
        }
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        handler.removeCallbacks(input_finish_checker)
    }

    fun afterTextChanged(s: Editable) {
        last_text_edit = System.currentTimeMillis()
        search = s.toString()
        handler.postDelayed(input_finish_checker, delay)
    }

    var delay: Long = 2000 // 1 seconds after user stops typing
    var last_text_edit: Long = 0
    var handler = Handler()

    private val input_finish_checker = Runnable {
        if (System.currentTimeMillis() > last_text_edit + delay - 2000) {
            //check for blacklist string
            //true display no result ui
            //false display result string

            Log.e("SearchString", "Result == $search")
            if (search.length > 2) {
                getNavigator()?.searchBlacklistStringData(search)
            } else if (search.isEmpty()) {
                getNavigator()?.fetchAllUsers()
            }
        }
    }

    fun callApiData() {
        try {
            getNavigator()?.setData()
        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }

}
