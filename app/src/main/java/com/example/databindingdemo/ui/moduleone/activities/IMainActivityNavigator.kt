package com.example.databindingdemo.ui.moduleone.activities

interface IMainActivityNavigator {
    fun setData()
    fun searchBlacklistStringData(string: String)
    fun errorLayoutDisplay()
    fun fetchAllUsers()
}