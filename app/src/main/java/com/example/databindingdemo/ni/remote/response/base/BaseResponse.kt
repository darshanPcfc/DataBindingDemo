package com.example.databindingdemo.ni.remote.response.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ashish Ingale 14/10/2019
 * API Base response parse here
 */

open class BaseResponse {

    @SerializedName("ok")
    @Expose
    var okStatus : Boolean = false
}
