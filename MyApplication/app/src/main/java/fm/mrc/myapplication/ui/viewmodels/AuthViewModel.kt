package fm.mrc.myapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fm.mrc.myapplication.data.models.User
import fm.mrc.myapplication.data.models.UserType
import fm.mrc.myapplication.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val TAG = "AuthViewModel"
    private val authRepository = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        try {
            Log.d(TAG, "Initializing AuthViewModel")
            checkAuthState()
        } catch (e: Exception) {
            Log.e(TAG, "Error in AuthViewModel init: ${e.message}", e)
            _authState.value = AuthState.Error("Initialization failed: ${e.message}")
        }
    }

    private fun checkAuthState() {
        try {
            Log.d(TAG, "Checking auth state")
            val user = authRepository.getCurrentUser()
            if (user != null) {
                Log.d(TAG, "User found, fetching user data")
                viewModelScope.launch {
                    try {
                        val userDoc = authRepository.getUserData(user.uid)
                        _currentUser.value = userDoc
                        _authState.value = AuthState.Authenticated
                        Log.d(TAG, "User authenticated successfully")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error fetching user data: ${e.message}", e)
                        _authState.value = AuthState.Error("Failed to get user data: ${e.message}")
                    }
                }
            } else {
                Log.d(TAG, "No user found, setting to unauthenticated")
                _authState.value = AuthState.Unauthenticated
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in checkAuthState: ${e.message}", e)
            _authState.value = AuthState.Error("Auth check failed: ${e.message}")
        }
    }

    fun signUp(
        email: String,
        password: String,
        name: String,
        phoneNumber: String,
        userType: UserType
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = authRepository.signUp(email, password, name, phoneNumber, userType)
                result.fold(
                    onSuccess = { user ->
                        _currentUser.value = user
                        _authState.value = AuthState.Authenticated
                    },
                    onFailure = { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Sign up failed")
                    }
                )
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Sign up failed")
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = authRepository.signIn(email, password)
                result.fold(
                    onSuccess = { user ->
                        _currentUser.value = user
                        _authState.value = AuthState.Authenticated
                    },
                    onFailure = { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Sign in failed")
                    }
                )
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Sign in failed")
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = authRepository.signOut()
                result.fold(
                    onSuccess = {
                        _currentUser.value = null
                        _authState.value = AuthState.Unauthenticated
                    },
                    onFailure = { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Sign out failed")
                    }
                )
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Sign out failed")
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = authRepository.resetPassword(email)
                result.fold(
                    onSuccess = {
                        _authState.value = AuthState.PasswordResetSent
                    },
                    onFailure = { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Password reset failed")
                    }
                )
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Password reset failed")
            }
        }
    }
}

sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object PasswordResetSent : AuthState()
    data class Error(val message: String) : AuthState()
} 