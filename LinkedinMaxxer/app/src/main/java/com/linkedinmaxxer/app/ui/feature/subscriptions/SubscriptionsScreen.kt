package com.linkedinmaxxer.app.ui.feature.subscriptions

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.linkedinmaxxer.app.domain.model.response.SubscriptionResponse
import com.linkedinmaxxer.app.ui.components.BottomNavBar
import com.linkedinmaxxer.app.ui.theme.InterFamily
import com.linkedinmaxxer.app.ui.theme.ManropeFamily
import com.linkedinmaxxer.app.ui.theme.Primary
import com.linkedinmaxxer.app.ui.theme.PrimaryContainer

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
                CircularProgressIndicator(color = Primary)
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
            // ── App header ────────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .background(PrimaryContainer.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(Icons.Default.Insights, contentDescription = null, tint = Primary)
                        }
                        Text(
                            "Linkedin Maxxer",
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = ManropeFamily,
                            color = Primary,
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                // ── Section title ─────────────────────────────────────────
                Text(
                    "Subs",
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Manage your high-priority curators.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = InterFamily,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(16.dp))

                // ── Add subscription row ──────────────────────────────────
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        value = data.linkedinUrlInput,
                        onValueChange = { onAction(SubscriptionsAction.OnLinkedinUrlChanged(it)) },
                        placeholder = {
                            Text(
                                "linkedin.com/in/name-12345",
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = InterFamily,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .height(68.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary.copy(alpha = 0.6f),
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                        ),
                    )

                    // Add New button — wide, tall, icon left / stacked text right
                    Button(
                        onClick = { onAction(SubscriptionsAction.OnSubscribeClicked) },
                        enabled = !data.isSubmitting,
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                        modifier = Modifier
                            .width(118.dp)
                            .height(68.dp),
                    ) {
                        Icon(
                            Icons.Default.PersonAdd,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp),
                        )
                        Spacer(Modifier.weight(1f))
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "Add",
                                fontFamily = ManropeFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge,
                            )
                            Text(
                                "New",
                                fontFamily = ManropeFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            }

            // ── Subscription list ─────────────────────────────────────────
            if (data.subscriptions.isEmpty()) {
                item {
                    Spacer(Modifier.height(20.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        ),
                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(36.dp),
                            )
                            Text(
                                "Add more curators",
                                style = MaterialTheme.typography.titleMedium,
                                fontFamily = ManropeFamily,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                "Subscribe to creators to automatically receive AI-generated comment suggestions.",
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = InterFamily,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            } else {
                item { Spacer(Modifier.height(8.dp)) }
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
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Avatar circle — first letter of username
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(PrimaryContainer.copy(alpha = 0.14f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = subscription.linkedinUsername.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryContainer,
                )
            }

            // Username block
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    subscription.linkedinUsername,
                    style = MaterialTheme.typography.titleSmall,
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    "@${subscription.linkedinUsername}",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = InterFamily,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // Auto-comment toggle
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    "Auto",
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Bold,
                    color = if (subscription.autoComment) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    "Comment",
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Bold,
                    color = if (subscription.autoComment) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Switch(
                    checked = subscription.autoComment,
                    onCheckedChange = onAutoCommentChanged,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    ),
                )
            }

            // Delete button
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Unsubscribe",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
                    modifier = Modifier.size(26.dp),
                )
            }
        }
    }
}
