package com.dicoding.submission_githubuserapp.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field: SerializedName("items")
    val items : ArrayList<UserData>
)