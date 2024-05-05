package com.example.mycalendar.feature.auth.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

private const val TAG = "SignupScreen"

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel(),
) {
    val signupUiState = viewModel.signupUiState

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
                SignupTitle()
                SignupForm(
                    signupState = signupUiState.signupState,
                    signupField = signupUiState.signupField,
                    errorMessage = signupUiState.errorMessage,
                    onSignupFieldChange = viewModel::updateSignUpField,
                    onSignup = viewModel::createNewUserWithEmailAndPassword
                )
            }
        }
    }
}

@Composable
private fun SignupTitle() {
    Image(
        painter = painterResource(id = R.drawable.google_logo),
        contentDescription = null,
        modifier = Modifier.size(36.dp),
    )
    Text(text = "Sign up", style = Typography.headlineLarge)
    Text(text = "with email and password", style = Typography.titleMedium)
}

@Composable
private fun SignupForm(
    signupState: SignupState,
    signupField: SignupField,
    errorMessage: String? = null,
    onSignupFieldChange: (SignupField) -> Unit,
    onSignup: () -> Unit,
) {
    OutlinedTextField(
        value = signupField.name,
        onValueChange = { onSignupFieldChange(signupField.copy(name = it)) },
        label = { Text("Display name*") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = signupField.email,
        onValueChange = { onSignupFieldChange(signupField.copy(email = it)) },
        label = { Text("Email*") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )

    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = signupField.password,
        onValueChange = { onSignupFieldChange(signupField.copy(password = it)) },
        label = { Text("Enter your password*") },
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
    OutlinedTextField(
        value = signupField.retypedPassword,
        onValueChange = { onSignupFieldChange(signupField.copy(retypedPassword = it)) },
        label = { Text("Re-enter your password*") },
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

    if (signupState == SignupState.FAILED)
        Text(
            text = errorMessage!!,
            style = Typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
        )

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Back",
            color = MaterialTheme.colorScheme.outline,
            style = Typography.labelLarge,
            modifier = Modifier
                .clickable { /*TODO: Implement*/ }
                .align(Alignment.CenterStart)
        )
        val coroutineScope = rememberCoroutineScope()
        Button(
            enabled = signupField.isValid(),
            onClick = {
                coroutineScope.launch {
                    onSignup()
//                    if (signupState == SignupState.SUCCESS)
//                    // TODO: navigate
                }
            }, colors = ButtonDefaults.buttonColors(
                containerColor = urlColor,
                contentColor = Color.White,
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(
                text = "Sign up",
                style = Typography.labelLarge,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }

    if (signupState == SignupState.LOADING)
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
}

@Preview
@Composable
fun SignupScreenLightPreview() {
    MyCalendarTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignupScreen()
        }
    }
}

@Preview
@Composable
fun SignupScreenDarkPreview() {
    MyCalendarTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignupScreen()
        }
    }
}