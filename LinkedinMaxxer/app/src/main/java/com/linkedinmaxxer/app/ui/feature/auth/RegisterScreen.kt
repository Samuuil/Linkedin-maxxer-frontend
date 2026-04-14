package com.linkedinmaxxer.app.ui.feature.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    data: RegisterUIData,
    onAction: (RegisterAction) -> Unit,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(data.errorMessage) {
        data.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(RegisterAction.OnErrorShown)
        }
    }

    LaunchedEffect(data.isSuccess) {
        if (data.isSuccess) onRegisterSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AuthBrandHeader(
            title = "LinkedIn Maxxer",
            subtitle = "Create your professional sanctuary.",
            modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        AuthTextField(
            label = "Work Email",
            value = data.email,
            onValueChange = { onAction(RegisterAction.OnEmailChanged(it)) },
            placeholder = "name@company.com",
        )

        AuthTextField(
            label = "Password",
            value = data.password,
            onValueChange = { onAction(RegisterAction.OnPasswordChanged(it)) },
            placeholder = "",
            isPassword = true,
            isPasswordVisible = data.isPasswordVisible,
            onPasswordVisibilityToggle = { onAction(RegisterAction.OnPasswordVisibilityToggle) },
        )

        AuthTextField(
            label = "Confirm Password",
            value = data.confirmPassword,
            onValueChange = { onAction(RegisterAction.OnConfirmPasswordChanged(it)) },
            placeholder = "",
            isPassword = true,
            isPasswordVisible = data.isConfirmPasswordVisible,
            onPasswordVisibilityToggle = { onAction(RegisterAction.OnConfirmPasswordVisibilityToggle) },
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Checkbox(
                checked = data.agreedToTerms,
                onCheckedChange = { onAction(RegisterAction.OnTermsChanged(it)) },
            )
            Text(
                text = "I agree to the Terms of Service and Privacy Policy.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 12.dp),
            )
        }

        AuthPrimaryButton(
            title = "Create Account",
            isLoading = data.isLoading,
            onClick = { onAction(RegisterAction.OnRegisterClicked) },
            modifier = Modifier.padding(top = 8.dp),
        )

        AuthFooterLink(
            prefix = "Already have an account?",
            suffix = "Login",
            onClick = onNavigateToLogin,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}
