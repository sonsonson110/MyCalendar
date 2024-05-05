package com.example.mycalendar.feature.auth.signup

data class SignupUiState(
    val signupField: SignupField = SignupField(),
    val errorMessage: String? = null,
    val signupState: SignupState = SignupState.IDLE,
)

enum class SignupState {
    IDLE, LOADING, SUCCESS, FAILED
}
data class SignupField(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val retypedPassword: String = "",
) {
    fun isValid(): Boolean {
        if (name.isBlank())
            return false
        if (email.isBlank())
            return false
        if ((password != retypedPassword) || password.length < 6)
            return false
        return true
    }
}
