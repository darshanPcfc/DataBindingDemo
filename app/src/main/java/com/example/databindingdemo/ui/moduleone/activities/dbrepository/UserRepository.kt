package com.example.databindingdemo.ui.moduleone.activities.dbrepository

import androidx.lifecycle.LiveData
import com.example.databindingdemo.ni.remote.response.Users
import com.example.databindingdemo.ui.moduleone.activities.daodata.IUserDao
import com.example.databindingdemo.ui.moduleone.activities.daodata.Words

class UserRepository(private val iUserDao: IUserDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allUsers: LiveData<List<Users>> = iUserDao.getUsers()

    val allWords: LiveData<List<Words>> = iUserDao.getWords()

    fun fetchSearchWord(search: String): LiveData<List<Words>> {
        return iUserDao.getSelectedWord(search)
    }

    fun fetchUser(search: String): LiveData<List<Users>> {
        return iUserDao.getFeatchUser(search)
    }

    suspend fun insert(users: Users) {
        iUserDao.insert(users)
    }

    suspend fun insertWord(word: Words) {
        iUserDao.insertWord(word)
    }
}