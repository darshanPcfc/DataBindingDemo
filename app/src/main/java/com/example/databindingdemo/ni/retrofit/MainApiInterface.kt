package com.example.databindingdemo.ni.retrofit

import com.example.databindingdemo.ni.remote.response.MainApiResponse
import com.example.databindingdemo.util.constants.Constants
import retrofit2.Call
import retrofit2.http.GET

interface MainApiInterface {

    @GET(Constants.ApiEndpoints.MAIN_API)
    fun mainApiCall(): Call<MainApiResponse>
}