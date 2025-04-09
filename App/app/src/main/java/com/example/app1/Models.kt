package com.example.app1

data class User(val id: Int, val name: String)

data class TemperatureRecord(val temperature: Float, val record_time: String)

data class UserDataResponse(
    val user: String,
    val current_temp: TemperatureRecord?,
    val records: List<TemperatureRecord>
)
