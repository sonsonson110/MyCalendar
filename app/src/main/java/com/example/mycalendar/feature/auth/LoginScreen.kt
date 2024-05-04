package com.example.mycalendar.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycalendar.R
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.example.mycalendar.ui.theme.Typography
import com.example.mycalendar.ui.theme.urlColor

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
) {
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
                LoginForm()
            }
        }
    }
}
@Composable
private fun LoginTitle() {
    Icon(
        painter = painterResource(id = R.drawable.google_logo),
        contentDescription = null,
        modifier = Modifier.size(36.dp),
    )
    Text(text = "Sign in", style = Typography.headlineLarge)
    Text(text = "Use your email and password", style = Typography.titleMedium)
}

@Composable
private fun LoginForm() {
    var email by remember { mutableStateOf("") }
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Email") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Enter your password") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val imageId = if (passwordVisible)
                R.drawable.icon_visibility_24
            else R.drawable.icon_visibility_off_24

            // provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = painterResource(id = imageId), description)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(
            tag = "forgot-password",
            annotation = "link-to-forgot-password-screen"
        )
        withStyle(style = SpanStyle(color = urlColor, fontWeight = FontWeight.W500)) {
            append("Forgot password?")
        }
        pop()
    }
    ClickableText(
        text = annotatedString,
        style = Typography.bodySmall,
    ) {
        // TODO: Implement onClick event
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.End),
        modifier = Modifier.fillMaxWidth()
    ) {
        ClickableText(text = buildAnnotatedString {
            pushStringAnnotation(
                tag = "create-account",
                annotation = "link-to-create-account-screen"
            )
            withStyle(
                style = SpanStyle(color = urlColor)
            ) {
                append("Create account")
            }
            pop()
        }) {
            // TODO: Implement onClick event
        }
        Button(
            onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                containerColor = urlColor,
                contentColor = Color.White,
            )
        ) {
            Text(text = "Login", style = Typography.labelLarge)
        }
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
            LoginScreen()
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
            LoginScreen()
        }
    }
}