package com.example.app1.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app1.ApiClient
import com.example.app1.User
import com.example.app1.UserDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UserDetailsState {
    data class Error(val errMessage: String?) : UserDetailsState()
    data object Loading : UserDetailsState()
    data class Success(val user: User, val userData: UserDataResponse) : UserDetailsState()
}

sealed class UsersListState {
    data object Loading : UsersListState()
    data class Error(val errMessage: String?) : UsersListState()
    data class Success(val users: List<User>) : UsersListState()
}

class TempFetchViewModel : ViewModel() {

    private val _users = MutableStateFlow<UsersListState>(UsersListState.Loading)
    private val _selectedUser = MutableStateFlow<UserDetailsState>(UserDetailsState.Loading)

    val users: StateFlow<UsersListState> = _users.asStateFlow()
    val selectedUser: StateFlow<UserDetailsState> = _selectedUser.asStateFlow()


    init {
        getAllUsers()
    }

    fun getAllUsers() {
        _users.value = UsersListState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = ApiClient.api.getUsers()
                _users.value = UsersListState.Success(users)
            } catch (e: Exception) {
                _users.value = UsersListState.Error(e.message)
            }
        }
    }


    fun getUserData(user: User) {
        _selectedUser.value = UserDetailsState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userData = ApiClient.api.getUserData(user.id)
                _selectedUser.value = UserDetailsState.Success(user, userData);
            } catch (e: Exception) {
                _selectedUser.value = UserDetailsState.Error(e.message)
            }
        }
    }
}