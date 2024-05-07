package com.example.mycalendar.feature.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalendar.core.data.model.User
import com.example.mycalendar.core.data.repository.AuthRepository
import com.example.mycalendar.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    // hold current login ui state
    var loginUiState by mutableStateOf<LoginUiState>(LoginUiState())
        private set // prevent ui modify directly

    fun updateLoginField(loginField: LoginField) {
        loginUiState = loginUiState.copy(loginField = loginField)
    }

    fun signInWithEmailAndPassword() {
        viewModelScope.launch(Dispatchers.IO) {
            loginUiState = loginUiState.copy(loginState = LoginState.LOADING)
            with(loginUiState.loginField) {
                authRepository.signInUserWithEmailAndPassword(email, password)
                    .catch { e ->
                        loginUiState = loginUiState.copy(
                            errorMessage = e.message,
                            loginState = LoginState.FAILED
                        )
                    }
                    .collect { data ->
                        loginUiState = loginUiState.copy(
                            loginState = LoginState.SUCCESS
                        )
                        // add/update to local db current user
                        userRepository.createLocalUser(
                            User(
                                uid = data.user!!.uid,
                                name = data.user!!.displayName,
                                email = data.user!!.email,
                                isSelf = true
                            )
                        )
                    }
            }
        }
    }
}