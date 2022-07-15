package com.dicoding.submission_githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission_githubuserapp.api.RetrofitService
import com.dicoding.submission_githubuserapp.model.UserData
import com.dicoding.submission_githubuserapp.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {


    private val listUser = MutableLiveData<ArrayList<UserData>>()
    private val isLoading = MutableLiveData<Boolean>(false)

    fun setSearchUser(query: String){
        isLoading.value = true
        RetrofitService.apiInstance.getSearchUser(query).enqueue(object : Callback<UserResponse>{
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful){
                    listUser.postValue(response.body()?.items)
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
                isLoading.value = false
            }
        })
    }

    fun getSearchUser(): LiveData<ArrayList<UserData>>{
        return listUser
    }

    fun getLoading() = isLoading
}