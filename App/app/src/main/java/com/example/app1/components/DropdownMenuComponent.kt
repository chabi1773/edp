package com.example.app1.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.app1.User


@Composable
fun DropdownMenuComponent(
    users: List<User>,
    selectedUser: User?,
    onUserSelected: (User) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Button(onClick = {expanded = true}){
            Text(text = selectedUser?.name ?:"Selected User")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
            users.forEach { user->
                DropdownMenuItem(text = {Text(user.name)}, onClick = {
                    onUserSelected(user)
                    expanded = false

                })
            }

        }
    }
}
