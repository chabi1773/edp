package com.example.app1.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.app1.model.User

object RetrofitClient {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}