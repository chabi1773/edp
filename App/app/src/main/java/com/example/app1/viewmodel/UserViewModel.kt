package com.example.app1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app1.model.User
import com.example.app1.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class UserViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users
    init {
        viewModelScope.launch {
            try{
                _users.value = RetrofitClient.api.getUsers()
            } catch (e: Exception){
                Log.e("UserVM", "Error: ${e.message}")
            }
        }
    }
}