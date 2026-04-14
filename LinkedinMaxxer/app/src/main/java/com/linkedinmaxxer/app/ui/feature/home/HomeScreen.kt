package com.linkedinmaxxer.app.ui.feature.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.linkedinmaxxer.app.ui.components.BottomNavBar
import com.linkedinmaxxer.app.ui.theme.Primary
import com.linkedinmaxxer.app.ui.theme.PrimaryContainer

@Composable
fun HomeScreen(
    data: HomeUIData,
    onAction: (HomeAction) -> Unit,
    onOpenSettings: () -> Unit,
    onOpenPosts: () -> Unit,
    onOpenCreatePost: () -> Unit,
    onOpenSubscriptions: () -> Unit,
    onOpenHints: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(data.errorMessage) {
        data.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(HomeAction.OnErrorShown)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedKey = "home",
                onClick = { key ->
                    when (key) {
                        "settings" -> onOpenSettings()
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 18.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            // ── Header ──────────────────────────────────────────────────
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
                            color = Primary,
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    "Good morning",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    "Your intelligence\nis updated.",
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            // ── Metric cards ─────────────────────────────────────────────
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricCard(
                            label = "Pending Suggestions",
                            value = data.pendingSuggestions.toString(),
                            icon = Icons.Default.Lightbulb,
                            modifier = Modifier.weight(1f),
                        )
                        MetricCard(
                            label = "Active Subs",
                            value = data.activeSubscriptions.toString(),
                            icon = Icons.Default.Groups,
                            modifier = Modifier.weight(1f),
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricCard(
                            label = "Auto-comment",
                            value = data.autoCommentEnabled.toString(),
                            icon = Icons.Default.RateReview,
                            modifier = Modifier.weight(1f),
                        )
                        MetricCard(
                            label = "Recent Growth",
                            value = "${data.recentGrowthPercent}%",
                            icon = Icons.Default.ShowChart,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            // ── Quick Actions ─────────────────────────────────────────────
            item {
                Spacer(Modifier.height(6.dp))
                Text(
                    "Quick Actions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(Modifier.height(14.dp))
                Button(
                    onClick = onOpenCreatePost,
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = null)
                    Text(
                        "Create Post",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.SemiBold,
                    )
                    Icon(Icons.Default.ArrowForward, contentDescription = null)
                }
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    QuickActionCard(
                        label = "Add Subscription",
                        icon = Icons.Default.PersonAdd,
                        onClick = onOpenSubscriptions,
                        modifier = Modifier.weight(1f),
                    )
                    QuickActionCard(
                        label = "Review Comments",
                        icon = Icons.Default.Forum,
                        onClick = onOpenHints,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            // ── Recent Activity ───────────────────────────────────────────
            item {
                Spacer(Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Recent Activity", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "View All",
                        style = MaterialTheme.typography.labelLarge,
                        color = Primary,
                    )
                }
                Spacer(Modifier.height(4.dp))
            }

            items(data.recentActivity) { item ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ),
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    PrimaryContainer.copy(alpha = 0.18f),
                                    RoundedCornerShape(10.dp),
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = if (item.type == "suggestion") Icons.Default.Forum else Icons.Default.Insights,
                                contentDescription = null,
                                tint = Primary,
                            )
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 10.dp)) {
                            Text(item.title, fontWeight = FontWeight.Bold, maxLines = 1)
                            Text(
                                item.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Text(
                            item.status.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = Primary,
                            modifier = Modifier
                                .background(
                                    PrimaryContainer.copy(alpha = 0.2f),
                                    RoundedCornerShape(999.dp),
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                        )
                    }
                }
            }

            if (data.recentActivity.isEmpty()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        ),
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Text("No recent activity yet", fontWeight = FontWeight.SemiBold)
                            Text(
                                "Activity will appear here after posts or suggestions.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(PrimaryContainer.copy(alpha = 0.14f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = PrimaryContainer,
                    modifier = Modifier.size(26.dp),
                )
            }
            Text(
                label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun MetricCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                PrimaryContainer.copy(alpha = 0.25f),
                                Primary.copy(alpha = 0.1f),
                            ),
                        ),
                        RoundedCornerShape(8.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(20.dp),
                )
            }
            Text(value, style = MaterialTheme.typography.headlineMedium)
            Text(
                label.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
