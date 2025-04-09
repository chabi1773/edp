package com.example.app1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app1.components.DropdownMenuComponent
import com.example.app1.components.LineChartComponent
import kotlinx.coroutines.launch

@Composable
fun TemperatureDashboard() {
    val coroutineScope = rememberCoroutineScope()
    var users by remember { mutableStateOf(listOf<User>()) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var userData by remember { mutableStateOf<UserDataResponse?>(null) }
    LaunchedEffect(Unit) {
        users = ApiClient.api.getUsers()
    }
    Column (modifier = Modifier.padding(16.dp)){
        DropdownMenuComponent(
            users = users,
            selectedUser = selectedUser,
            onUserSelected = {
            selectedUser = it
            coroutineScope.launch {
                userData = ApiClient.api.getUserData(it.id)
            }
        }
        )
        userData?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Current Temperature:${it.current_temp?.temperature}", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            LineChartComponent(records = it.records)
        }
    }
}