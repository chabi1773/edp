package com.example.app1

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/users")
    suspend fun getUsers(): List<User>

    @GET("api/user/{id}")
    suspend fun getUserData(@Path("id") id:Int): UserDataResponse
}