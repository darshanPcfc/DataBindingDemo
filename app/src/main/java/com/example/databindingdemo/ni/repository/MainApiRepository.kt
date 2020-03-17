package com.example.databindingdemo.ni.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.databindingdemo.ni.remote.request.MainApiRequest
import com.example.databindingdemo.ni.remote.response.MainApiResponse
import com.example.databindingdemo.ni.remote.response.Users
import com.example.databindingdemo.ni.remote.response.base.BaseResponse
import com.example.databindingdemo.ni.retrofit.MainApiInterface
import com.example.databindingdemo.ni.retrofit.RetrofitGenericResponse
import com.example.databindingdemo.ni.retrofit.RetrofitResponseCallback
import com.google.gson.Gson
import retrofit2.Response

class MainApiRepository(private val context: Context, val mainApiInterceptor: MainApiInterface) {
    fun validateMpin(): LiveData<MainApiResponse> {
        val data = MutableLiveData<MainApiResponse>()

        val validateMpinRequest = MainApiRequest()
        val dataJson = Gson().toJson(validateMpinRequest)

        RetrofitGenericResponse.callRetrofit(mainApiInterceptor.mainApiCall(), object : RetrofitResponseCallback {
            override fun success(response: Response<*>) {
                if (response.body() != null) {
                    println(response.body().toString())
                    data.value = response.body() as MainApiResponse
                }
            }

            override fun error(error: String) {
                data.value = null
            }

            override fun failure(message: String) {
                data.value = null
            }
        })

        return data

    }

    companion object {
        private val TAG = MainApiRepository::class.java.simpleName
    }
}