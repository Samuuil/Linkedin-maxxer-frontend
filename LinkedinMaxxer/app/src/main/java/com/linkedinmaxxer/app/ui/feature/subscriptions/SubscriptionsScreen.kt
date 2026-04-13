package com.linkedinmaxxer.app.ui.feature.subscriptions

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.linkedinmaxxer.app.domain.model.response.SubscriptionResponse
import com.linkedinmaxxer.app.ui.components.BottomNavBar
import com.linkedinmaxxer.app.ui.theme.Primary

@Composable
fun SubscriptionsScreen(
    data: SubscriptionsUIData,
    onAction: (SubscriptionsAction) -> Unit,
    onOpenHome: () -> Unit,
    onOpenPosts: () -> Unit,
    onOpenHints: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(data.errorMessage) {
        data.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(SubscriptionsAction.OnErrorShown)
        }
    }
    LaunchedEffect(data.successMessage) {
        data.successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(SubscriptionsAction.OnSuccessShown)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedKey = "subs",
                onClick = { key ->
                    when (key) {
                        "home" -> onOpenHome()
                        "posts" -> onOpenPosts()
                        "hints" -> onOpenHints()
                        "settings" -> onOpenSettings()
                    }
                },
            )
        },
    ) { paddingValues ->
        if (data.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 18.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Subs", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                }
                Spacer(modifier = Modifier.size(4.dp))
                Text("Manage your high-priority curators.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.size(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = data.linkedinUrlInput,
                        onValueChange = { onAction(SubscriptionsAction.OnLinkedinUrlChanged(it)) },
                        placeholder = { Text("https://linkedin.com/in/username") },
                        label = { Text("LinkedIn URL") },
                        modifier = Modifier.weight(1f),
                    )
                    Button(
                        onClick = { onAction(SubscriptionsAction.OnSubscribeClicked) },
                        enabled = !data.isSubmitting,
                        shape = RoundedCornerShape(999.dp),
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = null)
                    }
                }
            }

            if (data.subscriptions.isEmpty()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Column(Modifier.fillMaxWidth().padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Add more curators", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(
                                "Subscribe to creators to receive AI-generated comment suggestions.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            } else {
                items(data.subscriptions, key = { it.id }) { item ->
                    SubscriptionCard(
                        subscription = item,
                        onDelete = { onAction(SubscriptionsAction.OnDeleteSubscription(item.id)) },
                        onAutoCommentChanged = { enabled ->
                            onAction(SubscriptionsAction.OnAutoCommentChanged(item.id, enabled))
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun SubscriptionCard(
    subscription: SubscriptionResponse,
    onDelete: () -> Unit,
    onAutoCommentChanged: (Boolean) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text(subscription.linkedinUsername, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("@${subscription.linkedinUsername}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("AUTO-COMMENT", style = MaterialTheme.typography.labelSmall, color = Primary)
                Switch(checked = subscription.autoComment, onCheckedChange = onAutoCommentChanged)
            }
            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(32.dp)
                    .clickable(onClick = onDelete)
                    .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                    .padding(6.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(16.dp))
            }
        }
    }
}
