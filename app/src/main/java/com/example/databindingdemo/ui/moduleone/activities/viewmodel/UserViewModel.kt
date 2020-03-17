package com.example.databindingdemo.ui.moduleone.activities.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.databindingdemo.base.BaseApplication
import com.example.databindingdemo.ni.remote.response.Users
import com.example.databindingdemo.ui.moduleone.activities.daodata.UserRoomDatabase
import com.example.databindingdemo.ui.moduleone.activities.daodata.Words
import com.example.databindingdemo.ui.moduleone.activities.dbrepository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: BaseApplication) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository

    // LiveData gives us updated words when they change.
    val alluser: LiveData<List<Users>>

    val allWords: LiveData<List<Words>>

    //val allWords: LiveData<List<Words>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val userDao = UserRoomDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        alluser = repository.allUsers
        allWords = repository.allWords
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(users: Users) = viewModelScope.launch {
        repository.insert(users)
    }

    fun insertWords(word: Words) = viewModelScope.launch {
        repository.insertWord(word)
    }

    fun fetchSearchWord(search : String) : LiveData<List<Words>>{
        return repository.fetchSearchWord(search)
    }

    fun fetchUser(search : String) : LiveData<List<Users>>{
        return repository.fetchUser(search)
    }

}