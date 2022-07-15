package com.dicoding.submission_githubuserapp.api

import com.dicoding.submission_githubuserapp.BuildConfig
import com.dicoding.submission_githubuserapp.model.DetailUser
import com.dicoding.submission_githubuserapp.model.UserData
import com.dicoding.submission_githubuserapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("search/users")
    fun getSearchUser(
        @Query("q")
        query: String
    ): Call<UserResponse>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username")
        username: String,
    ): Call<DetailUser>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username")
        username: String,
    ): Call<ArrayList<UserData>>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username")
        username: String,
    ): Call<ArrayList<UserData>>
}