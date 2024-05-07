package com.example.mycalendar.feature.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalendar.core.data.repository.AuthRepository
import com.example.mycalendar.core.data.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SignupViewModel"

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    var signupUiState by mutableStateOf(SignupUiState())
        private set

    fun updateSignUpField(signupField: SignupField) {
        signupUiState = signupUiState.copy(signupField = signupField)
    }

    fun createNewUserWithEmailAndPassword() {
        viewModelScope.launch {
            signupUiState = signupUiState.copy(signupState = SignupState.LOADING)
            with(signupUiState.signupField) {
                authRepository.createUserWithEmailAndPassword(email, password)
                    .catch { e ->
                        signupUiState = signupUiState.copy(
                            signupState = SignupState.FAILED,
                            errorMessage = e.toString()
                        )
                    }
                    .collect { data ->
                        val newUser = com.example.mycalendar.core.data.model.User(
                            uid = data.user!!.uid,
                            name = name,
                            email = email,
                            isSelf = false
                        )
                        signupUiState = signupUiState.copy(signupState = SignupState.SUCCESS)
                        // store in local database & remote on success
                        newUser.let {
                            userRepository.createLocalUser(it)
                            userRepository.createRemoteUser(it)
                        }
                        // change display name in firebaseAuth
                        authRepository.updateAthUserDisplayName(name, authResult = data)
                    }
            }
        }
    }
}