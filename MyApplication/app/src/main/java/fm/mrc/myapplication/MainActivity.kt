package fm.mrc.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fm.mrc.myapplication.data.models.UserType
import fm.mrc.myapplication.ui.screens.*
import fm.mrc.myapplication.ui.theme.MyApplicationTheme
import fm.mrc.myapplication.ui.viewmodels.AuthState
import fm.mrc.myapplication.ui.viewmodels.AuthViewModel

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            Log.d(TAG, "MainActivity onCreate started")
            setContent {
                MyApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen()
                    }
                }
            }
            Log.d(TAG, "MainActivity onCreate completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error in MainActivity onCreate: ${e.message}", e)
            // Show error message to user
            setContent {
                MyApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "App initialization failed. Please restart the app.",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(authViewModel: AuthViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf("user_type_selection") }
    val authState by authViewModel.authState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    LaunchedEffect(Unit) {
        try {
            Log.d("MainScreen", "MainScreen composable started")
        } catch (e: Exception) {
            Log.e("MainScreen", "Error in MainScreen: ${e.message}", e)
        }
    }

    when (authState) {
        is AuthState.Initial -> {
            Log.d("MainScreen", "Showing initial state")
            UserTypeSelectionScreen(
                onProviderSelected = { currentScreen = "provider_login" },
                onClientSelected = { currentScreen = "client_login" }
            )
        }
        is AuthState.Loading -> {
            Log.d("MainScreen", "Showing loading state")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is AuthState.Authenticated -> {
            Log.d("MainScreen", "Showing authenticated state")
            when (currentUser?.userType) {
                UserType.SERVICE_PROVIDER -> HomeScreen()
                UserType.CLIENT -> ClientHomeScreen()
                null -> UserTypeSelectionScreen(
                    onProviderSelected = { currentScreen = "provider_login" },
                    onClientSelected = { currentScreen = "client_login" }
                )
                else -> UserTypeSelectionScreen(
                    onProviderSelected = { currentScreen = "provider_login" },
                    onClientSelected = { currentScreen = "client_login" }
                )
            }
        }
        is AuthState.Unauthenticated -> {
            Log.d("MainScreen", "Showing unauthenticated state")
            when (currentScreen) {
                "user_type_selection" -> UserTypeSelectionScreen(
                    onProviderSelected = { currentScreen = "provider_login" },
                    onClientSelected = { currentScreen = "client_login" }
                )
                "provider_login" -> ProviderLoginScreen(
                    onSignIn = { email, password -> authViewModel.signIn(email, password) },
                    onSignUp = { currentScreen = "provider_signup" },
                    onBackClick = { currentScreen = "user_type_selection" }
                )
                "provider_signup" -> ProviderSignUpScreen(
                    onSignUp = { email, password, name, phoneNumber ->
                        authViewModel.signUp(email, password, name, phoneNumber, UserType.SERVICE_PROVIDER)
                    },
                    onLoginClick = { currentScreen = "provider_login" },
                    onBackClick = { currentScreen = "provider_login" }
                )
                "client_login" -> ClientLoginScreen(
                    onSignIn = { email, password -> authViewModel.signIn(email, password) },
                    onSignUp = { currentScreen = "client_signup" },
                    onBackClick = { currentScreen = "user_type_selection" }
                )
                "client_signup" -> ClientSignUpScreen(
                    onSignUp = { email, password, name, phoneNumber ->
                        authViewModel.signUp(email, password, name, phoneNumber, UserType.CLIENT)
                    },
                    onBackClick = { currentScreen = "client_login" }
                )
            }
        }
        is AuthState.PasswordResetSent -> {
            Log.d("MainScreen", "Showing password reset sent state")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Password reset email sent. Please check your inbox.")
            }
        }
        is AuthState.Error -> {
            Log.e("MainScreen", "Showing error state: ${(authState as AuthState.Error).message}")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = (authState as AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { /* Retry the last operation */ }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}