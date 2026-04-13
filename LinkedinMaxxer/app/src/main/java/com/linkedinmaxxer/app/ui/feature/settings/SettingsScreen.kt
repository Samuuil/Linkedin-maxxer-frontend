package com.linkedinmaxxer.app.ui.feature.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.linkedinmaxxer.app.ui.theme.ErrorContainer
import com.linkedinmaxxer.app.ui.theme.Primary
import com.linkedinmaxxer.app.ui.theme.PrimaryContainer

@Composable
fun SettingsScreen(
    data: SettingsUIData,
    onAction: (SettingsAction) -> Unit,
    onLoggedOut: () -> Unit,
    onOpenHome: () -> Unit,
    onOpenSetup: () -> Unit,
    onOpenPosts: () -> Unit,
    onOpenSubscriptions: () -> Unit,
    onOpenHints: () -> Unit,
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
                onClick = { key ->
                    when (key) {
                        "home" -> onOpenHome()
                        "posts" -> onOpenPosts()
                        "subs" -> onOpenSubscriptions()
                        "hints" -> onOpenHints()
                    }
                },
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
                .padding(horizontal = 18.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(PrimaryContainer.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center,
                    ) { Icon(Icons.Default.Person, contentDescription = null, tint = Primary) }
                    Text("Executive Lens", style = MaterialTheme.typography.titleMedium, color = Primary)
                }
            }

            Text("ACCOUNT", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(Modifier.size(36.dp).background(PrimaryContainer.copy(alpha = 0.2f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Primary)
                        }
                        Column(Modifier.weight(1f)) {
                            Text("User Profile", fontWeight = FontWeight.Bold)
                            Text(data.profile?.linkedinUsername ?: "LinkedIn not connected", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(Modifier.size(36.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Mail, contentDescription = null)
                        }
                        Column(Modifier.weight(1f)) {
                            Text("Email", fontWeight = FontWeight.Bold)
                            Text(data.profile?.email.orEmpty(), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            Text("LINKEDIN CONNECTIONS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("API Credentials", fontWeight = FontWeight.Bold)
                            Text("Managed in setup checklist", style = MaterialTheme.typography.bodySmall)
                        }
                        Button(
                            onClick = onOpenSetup,
                            shape = RoundedCornerShape(999.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        ) { Text("Edit", color = MaterialTheme.colorScheme.onSurface) }
                    }
                }
            }

            Text("PREFERENCES", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text("Push Notifications", fontWeight = FontWeight.Bold)
                        Text("Linked to configured push token", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = data.pushNotificationsEnabled,
                        onCheckedChange = { onAction(SettingsAction.OnPushNotificationsChanged(it)) },
                    )
                }
            }

            Text("SUPPORT", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Help Center", fontWeight = FontWeight.SemiBold)
                    Text("Privacy Policy", fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(2.dp))
            Button(
                onClick = { onAction(SettingsAction.OnLogoutClicked) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ErrorContainer, contentColor = MaterialTheme.colorScheme.error),
                shape = RoundedCornerShape(14.dp),
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(Modifier.size(6.dp))
                Text("Logout")
            }
            Text(
                "EXECUTIVE LENS V2.4.1 (STABLE)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 2.dp),
            )
        }
    }
}
