package com.example.databindingdemo.ui.moduleone.activities.daodata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.databindingdemo.ni.remote.response.Users


@Dao
interface IUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: Users)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: Words)


    @Query("SELECT * from user_name ORDER BY id ASC")
    fun getUsers(): LiveData<List<Users>>

    @Query("SELECT * from search_word")
    fun getWords(): LiveData<List<Words>>

    @Query("SELECT * from search_word WHERE word = :search")
    fun getSelectedWord(search: String): LiveData<List<Words>>

    @Query("SELECT * from user_name WHERE display_name LIKE '%' || :search || '%' ")
    fun getFeatchUser(search: String): LiveData<List<Users>>
}