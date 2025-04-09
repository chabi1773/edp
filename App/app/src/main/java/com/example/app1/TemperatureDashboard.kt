
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app1.User
import com.example.app1.components.DropdownMenuComponent
import com.example.app1.components.LineChartComponent
import com.example.app1.ui.theme.TempFetchViewModel
import com.example.app1.ui.theme.UserDetailsState
import com.example.app1.ui.theme.UsersListState
import kotlinx.coroutines.launch


@Composable
fun TemperatureDashboard() {
    val tempFetchViewModel: TempFetchViewModel = viewModel()

    val users by tempFetchViewModel.users.collectAsState()
    val currentUser by tempFetchViewModel.selectedUser.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        when (val state = users) {
            is UsersListState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is UsersListState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text("Error loading users", color = MaterialTheme.colorScheme.error)
                    state.errMessage?.let {
                        Text(it)
                    }
                }
            }

            is UsersListState.Success -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.users) { user ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedUser = user
                                    tempFetchViewModel.getUserData(user)
                                    showDialog = true
                                },
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(user.name, style = MaterialTheme.typography.titleMedium)
                                Text("ID: ${user.id}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }

        if (showDialog && selectedUser != null) {
            UserDetailsDialog(
                onDismiss = { showDialog = false },
                userDetailsState = currentUser,
                userName = selectedUser?.name ?: ""
            )
        }
    }
}

@Composable
fun UserDetailsDialog(
    onDismiss: () -> Unit,
    userDetailsState: UserDetailsState,
    userName: String
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Details for $userName", style = MaterialTheme.typography.headlineSmall)
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (userDetailsState) {
                    is UserDetailsState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    is UserDetailsState.Error -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Error fetching details", color = MaterialTheme.colorScheme.error)
                            userDetailsState.errMessage?.let {
                                Text(it)
                            }
                        }
                    }

                    is UserDetailsState.Success -> {
                        val userData = userDetailsState.userData
                        Text(
                            "Current Temperature: ${userData.current_temp?.temperature}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LineChartComponent(records = userData.records)
                    }
                }
            }
        }
    }
}
