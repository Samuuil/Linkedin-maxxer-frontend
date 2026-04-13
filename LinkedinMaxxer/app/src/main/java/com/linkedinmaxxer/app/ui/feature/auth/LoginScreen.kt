package com.linkedinmaxxer.app.ui.feature.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    data: LoginUIData,
    onAction: (LoginAction) -> Unit,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(data.errorMessage) {
        data.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(LoginAction.OnErrorShown)
        }
    }

    LaunchedEffect(data.isSuccess) {
        if (data.isSuccess) onLoginSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        AuthBrandHeader(
            title = "Executive Lens",
            subtitle = "Log in to access your professional intelligence dashboard.",
            modifier = Modifier.padding(top = 12.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        AuthTextField(
            label = "Email Address",
            value = data.email,
            onValueChange = { onAction(LoginAction.OnEmailChanged(it)) },
            placeholder = "name@company.com",
        )

        AuthTextField(
            label = "Password",
            value = data.password,
            onValueChange = { onAction(LoginAction.OnPasswordChanged(it)) },
            placeholder = "••••••••",
            isPassword = true,
            isPasswordVisible = data.isPasswordVisible,
            onPasswordVisibilityToggle = { onAction(LoginAction.OnPasswordVisibilityToggle) },
        )

        AuthPrimaryButton(
            title = "Login",
            isLoading = data.isLoading,
            onClick = { onAction(LoginAction.OnLoginClicked) },
            modifier = Modifier.padding(top = 6.dp),
        )

        Spacer(modifier = Modifier.height(14.dp))

        InlineStatus(
            icon = Icons.Default.VerifiedUser,
            text = "Enterprise Grade Security",
            modifier = Modifier.fillMaxWidth(),
        )

        AuthFooterLink(
            prefix = "New to Executive Lens?",
            suffix = "Register",
            onClick = onNavigateToRegister,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
