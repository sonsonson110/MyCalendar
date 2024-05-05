package com.example.mycalendar.feature.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalendar.core.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    // hold current login ui state
    var loginUiState by mutableStateOf<LoginUiState>(LoginUiState())
        private set // prevent ui modify directly

    fun updateLoginField(loginField: LoginField) {
        loginUiState = loginUiState.copy(loginField = loginField)
    }

    suspend fun signInWithEmailAndPassword() {
        viewModelScope.launch {
            loginUiState = loginUiState.copy(loginState = LoginState.LOADING)
            with(loginUiState.loginField) {
                authRepository.signInUserWithEmailAndPassword(email, password)
                    .catch { e ->
                        loginUiState = loginUiState.copy(
                            errorMessage = e.message,
                            loginState = LoginState.FAILED
                        )
                    }
                    .collect { _ ->
                        loginUiState = loginUiState.copy(
                            loginState = LoginState.SUCCESS
                        )
                    }
            }
        }
    }
}