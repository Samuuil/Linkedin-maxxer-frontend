package com.linkedinmaxxer.app.ui.feature.settings

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.linkedinmaxxer.app.ui.components.BottomNavBar

@Composable
fun SettingsScreen(
    data: SettingsUIData,
    onAction: (SettingsAction) -> Unit,
    onLoggedOut: () -> Unit,
    onOpenHome: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(data.errorMessage) {
        data.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(SettingsAction.OnErrorShown)
        }
    }

    LaunchedEffect(data.logoutCompleted) {
        if (data.logoutCompleted) onLoggedOut()
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedKey = "settings",
                onClick = { key -> if (key == "home") onOpenHome() },
            )
        },
    ) { paddingValues ->
        if (data.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Executive Lens", style = MaterialTheme.typography.titleMedium)
                androidx.compose.material3.Icon(Icons.Default.Notifications, contentDescription = null)
            }

            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("ACCOUNT", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("User Profile", fontWeight = FontWeight.Bold)
                    Text(data.profile?.linkedinUsername ?: "LinkedIn username not connected", style = MaterialTheme.typography.bodySmall)
                    Text("Email", fontWeight = FontWeight.Bold)
                    Text(data.profile?.email.orEmpty(), style = MaterialTheme.typography.bodySmall)
                }
            }

            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("LINKEDIN CONNECTIONS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("API Credentials", fontWeight = FontWeight.Bold)
                    Text("Connected via backend profile/auth state", style = MaterialTheme.typography.bodySmall)
                }
            }

            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text("Push Notifications", fontWeight = FontWeight.Bold)
                        Text(
                            "UI-only toggle (backend supports push token string update).",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                    Switch(
                        checked = data.pushNotificationsEnabled,
                        onCheckedChange = { onAction(SettingsAction.OnPushNotificationsChanged(it)) },
                    )
                }
            }

            Spacer(Modifier.height(4.dp))
            Button(onClick = { onAction(SettingsAction.OnLogoutClicked) }, modifier = Modifier.fillMaxWidth()) {
                Text("Logout")
            }
        }
    }
}
