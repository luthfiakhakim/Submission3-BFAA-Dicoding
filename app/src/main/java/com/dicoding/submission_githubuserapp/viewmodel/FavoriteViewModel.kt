package com.dicoding.submission_githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.submission_githubuserapp.database.ListFavoriteUser
import com.dicoding.submission_githubuserapp.database.ListFavoriteUserDao
import com.dicoding.submission_githubuserapp.database.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: ListFavoriteUserDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.listFavoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<ListFavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}