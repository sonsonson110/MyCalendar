package com.example.mycalendar.feature.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mycalendar.R
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.example.mycalendar.ui.theme.Typography
import com.example.mycalendar.ui.theme.urlColor
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navigateToSignUpScreen: () -> Unit,
    navigateToScheduleScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginUiState = viewModel.loginUiState

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(MaterialTheme.shapes.large),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(20.dp)
            ) {
                LoginTitle()

                val coroutineScope = rememberCoroutineScope()
                LoginForm(
                    loginState = loginUiState.loginState,
                    loginField = loginUiState.loginField,
                    errorMessage = loginUiState.errorMessage,
                    onLoginFieldChange = viewModel::updateLoginField,
                    onLogin = {
                        coroutineScope.launch {
                            viewModel.signInWithEmailAndPassword()
//                            if (loginUiState.loginState == LoginState.SUCCESS)
                            // TODO: Implement navigation

                        }
                    },
                    navigateToSignUpScreen = navigateToSignUpScreen,
                    navigateToScheduleScreen = navigateToScheduleScreen,
                )
            }
        }
    }
}

@Composable
private fun LoginTitle() {
    Image(
        painter = painterResource(id = R.drawable.google_logo),
        contentDescription = null,
        modifier = Modifier.size(36.dp),
    )
    Text(text = "Sign in", style = Typography.headlineLarge)
    Text(text = "Use your email and password", style = Typography.titleMedium)
}

@Composable
private fun LoginForm(
    loginState: LoginState,
    loginField: LoginField,
    onLoginFieldChange: (LoginField) -> Unit,
    errorMessage: String? = null,
    onLogin: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    navigateToScheduleScreen: () -> Unit,
) {
    OutlinedTextField(
        value = loginField.email,
        onValueChange = { onLoginFieldChange(loginField.copy(email = it)) },
        label = { Text("Email") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )

    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = loginField.password,
        onValueChange = { onLoginFieldChange(loginField.copy(password = it)) },
        label = { Text("Enter your password") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val imageId = if (passwordVisible)
                R.drawable.icon_visibility_24
            else R.drawable.icon_visibility_off_24

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = painterResource(id = imageId), null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        text = "Forgot password?",
        style = Typography.labelMedium,
        fontWeight = FontWeight.W500,
        color = urlColor,
        modifier = Modifier.clickable { /*TODO: Implement*/ }
    )

    if (loginState == LoginState.FAILED)
        Text(
            text = errorMessage!!,
            style = Typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
        )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.End),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Create account",
            color = urlColor,
            style = Typography.labelLarge,
            modifier = Modifier.clickable { navigateToSignUpScreen() }
        )
        Button(
            onClick = onLogin, colors = ButtonDefaults.buttonColors(
                containerColor = urlColor,
                contentColor = Color.White,
            )
        ) {
            Text(text = "Login", style = Typography.labelLarge)
        }
    }

    if (loginState == LoginState.LOADING)
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

    // always listen to loginState, navigate when login success
    LaunchedEffect(key1 = loginState) {
        if (loginState == LoginState.SUCCESS)
            navigateToScheduleScreen()
    }
}

@Preview
@Composable
fun LoginScreenLightPreview() {
    MyCalendarTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen(navigateToSignUpScreen = {}, navigateToScheduleScreen = {})
        }
    }
}

@Preview
@Composable
fun LoginScreenDarkPreview() {
    MyCalendarTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen(navigateToSignUpScreen = {}, navigateToScheduleScreen = {})
        }
    }
}