package com.example.databindingdemo.ni.remote.response

data class MainApiResponse (
    val ok:Boolean,
    val users: List<Users>
)