package com.dicoding.submission_githubuserapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dicoding.submission_githubuserapp.api.RetrofitService
import com.dicoding.submission_githubuserapp.database.ListFavoriteUser
import com.dicoding.submission_githubuserapp.database.ListFavoriteUserDao
import com.dicoding.submission_githubuserapp.database.UserDatabase
import com.dicoding.submission_githubuserapp.model.DetailUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val users = MutableLiveData<DetailUser>()

    private var userDao: ListFavoriteUserDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.listFavoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitService.apiInstance.getUserDetail(username).enqueue(object : Callback<DetailUser> {
            override fun onResponse(
                call: Call<DetailUser>,
                response: Response<DetailUser>,
            ) {
                if (response.isSuccessful) {
                    users.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun addToFavorite(
        username: String,
        id: Int,
        avatarUrl: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = ListFavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun userFavorited(id: Int) = userDao?.userFavorited(id)

    fun deleteFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavoriteUser(id)
        }
    }
}