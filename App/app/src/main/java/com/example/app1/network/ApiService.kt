package com.example.app1.network

import retrofit2.http.GET
import com.example.app1.model.User
import com.example.app1.model.TemperatureRecord
import android.health.connect.datatypes.units.Temperature

interface ApiService {
    @GET("api/users/")
    suspend fun getUsers(): List<User>

    @GET("api/temperatures/")
    suspend fun getTemperatures(): List<TemperatureRecord>
}