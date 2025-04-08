package com.example.app1.inf

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app1.viewmodel.UserViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue


@Composable
fun MainScreen(userViewModel: UserViewModel) {
    val users by userViewModel.users.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Users")

        users.forEach { user ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Username: ${user.username}")
                    Text(text = "Status: ${user.status}")
                }
            }
        }
    }
}
