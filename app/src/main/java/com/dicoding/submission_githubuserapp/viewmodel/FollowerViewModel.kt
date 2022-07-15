package com.dicoding.submission_githubuserapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission_githubuserapp.api.RetrofitService
import com.dicoding.submission_githubuserapp.model.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    val listFollower = MutableLiveData<ArrayList<UserData>>()

    fun setListFollowerUser(username: String) {
        RetrofitService.apiInstance.getFollower(username).enqueue(object : Callback<ArrayList<UserData>> {
            override fun onResponse(
                call: Call<ArrayList<UserData>>,
                response: Response<ArrayList<UserData>>,
            ) {
                if (response.isSuccessful) {
                    listFollower.postValue(response.body())
                    Log.d(TAG, "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<UserData>>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun getListFollowerUser(): LiveData<ArrayList<UserData>> {
        return listFollower
    }
}