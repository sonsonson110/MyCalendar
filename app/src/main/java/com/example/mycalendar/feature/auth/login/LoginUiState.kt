package com.example.mycalendar.feature.auth.login

data class LoginUiState(
    val loginField: LoginField = LoginField(),
    val errorMessage: String? = null,
    val loginState: LoginState = LoginState.IDLE
)
enum class LoginState {
    IDLE, LOADING, SUCCESS, FAILED
}
data class LoginField(
    val email: String = "",
    val password: String = "",
)
