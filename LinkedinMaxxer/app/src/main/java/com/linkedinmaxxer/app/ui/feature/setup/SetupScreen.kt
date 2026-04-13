package com.linkedinmaxxer.app.ui.feature.setup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Token
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.linkedinmaxxer.app.ui.theme.Primary
import com.linkedinmaxxer.app.ui.theme.PrimaryContainer
import com.linkedinmaxxer.app.ui.theme.SurfaceContainerHighest

@Composable
fun SetupScreen(
    data: SetupUIData,
    onAction: (SetupAction) -> Unit,
    onContinue: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(data.errorMessage) {
        data.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(SetupAction.OnErrorShown)
        }
    }
    LaunchedEffect(data.successMessage) {
        data.successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(SetupAction.OnSuccessShown)
        }
    }
    LaunchedEffect(data.completed) {
        if (data.completed) {
            onContinue()
        }
    }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
            ) {
                Button(
                    onClick = { onAction(SetupAction.OnContinue) },
                    enabled = !data.isSaving,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                ) {
                    if (data.isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp, color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Continue")
                    }
                }
                Text(
                    if (data.isSaving) "Saving..." else "Continue saves all LinkedIn fields",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                )
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Executive Lens", style = MaterialTheme.typography.titleMedium, color = Primary)
                androidx.compose.material3.Icon(Icons.Default.Notifications, contentDescription = null, tint = Primary)
            }

            Text("Configuration\nChecklist", style = MaterialTheme.typography.headlineMedium)
            LinearProgressLike(progress = connectedCount(data) / 3f)
            Text(
                "Complete these essential steps to synchronize your digital presence.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            SetupSection(
                title = "Official LinkedIn token",
                connected = data.officialConnected,
                icon = Icons.Default.Key,
            ) {
                OutlinedTextField(
                    value = data.officialToken,
                    onValueChange = { onAction(SetupAction.OnOfficialTokenChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("li_at token") },
                )
            }

            SetupSection(
                title = "Unofficial token",
                connected = data.unofficialConnected,
                icon = Icons.Default.Token,
            ) {
                OutlinedTextField(
                    value = data.unofficialToken,
                    onValueChange = { onAction(SetupAction.OnUnofficialTokenChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("session_id token") },
                )
            }

            SetupSection(
                title = "Credentials",
                connected = data.credentialsConnected,
                icon = Icons.Default.Lock,
            ) {
                OutlinedTextField(
                    value = data.linkedinEmail,
                    onValueChange = { onAction(SetupAction.OnLinkedinEmailChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("executive@curator.io") },
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = data.linkedinPassword,
                    onValueChange = { onAction(SetupAction.OnLinkedinPasswordChanged(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("password") },
                )
            }

        }
    }
}

@Composable
private fun SetupSection(
    title: String,
    connected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit,
) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(icon, contentDescription = null, tint = Primary)
                    Text(title, fontWeight = FontWeight.Bold)
                }
                Text(
                    if (connected) "Connected" else "Not connected",
                    color = if (connected) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .background(
                            if (connected) PrimaryContainer.copy(alpha = 0.2f) else SurfaceContainerHighest,
                            RoundedCornerShape(999.dp),
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }
            content()
        }
    }
}

@Composable
private fun LinearProgressLike(progress: Float) {
    val safeProgress = progress.coerceIn(0f, 1f)
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerHighest),
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(safeProgress)
                .height(6.dp)
                .background(Primary),
        )
    }
}

private fun connectedCount(data: SetupUIData): Int =
    listOf(
        data.officialConnected,
        data.unofficialConnected,
        data.credentialsConnected,
    ).count { it }
