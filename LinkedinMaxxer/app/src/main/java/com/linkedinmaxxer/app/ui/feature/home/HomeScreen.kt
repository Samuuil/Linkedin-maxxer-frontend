package com.linkedinmaxxer.app.ui.feature.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.filled.TrendingUp
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
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
                        ) {
                            Icon(Icons.Default.Insights, contentDescription = null, tint = Primary)
                        }
                        Text("Executive Lens", style = MaterialTheme.typography.titleMedium, color = Primary)
                    }
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    "Good morning",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text("Your intelligence\nis updated.", style = MaterialTheme.typography.headlineMedium)
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricCard("Pending Suggestions", data.pendingSuggestions.toString(), Icons.Default.Lightbulb, Modifier.weight(1f))
                        MetricCard("Active Subs", data.activeSubscriptions.toString(), Icons.Default.Subscriptions, Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricCard("Auto-comment", data.autoCommentEnabled.toString(), Icons.Default.AutoAwesome, Modifier.weight(1f))
                        MetricCard("Recent Growth", "${data.recentGrowthPercent}%", Icons.Default.TrendingUp, Modifier.weight(1f))
                    }
                }
            }

            item {
                Text("Quick Actions", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = onOpenCreatePost,
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = null)
                    Text("Create Post", modifier = Modifier.weight(1f).padding(start = 8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null)
                }
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    ) { Text("Add Subscription", color = MaterialTheme.colorScheme.onSurface) }
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    ) { Text("Review Hints", color = MaterialTheme.colorScheme.onSurface) }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Recent Activity", style = MaterialTheme.typography.titleMedium)
                    Text("View All", style = MaterialTheme.typography.labelMedium, color = Primary)
                }
            }

            items(data.recentActivity) { item ->
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(PrimaryContainer.copy(alpha = 0.18f), RoundedCornerShape(10.dp)),
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
                            Text(item.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text(
                            item.status.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = Primary,
                            modifier = Modifier
                                .background(PrimaryContainer.copy(alpha = 0.2f), RoundedCornerShape(999.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                        )
                    }
                }
            }

            if (data.recentActivity.isEmpty()) {
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
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
private fun MetricCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(Brush.linearGradient(listOf(PrimaryContainer.copy(alpha = 0.25f), Primary.copy(alpha = 0.1f))), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
            }
            Text(value, style = MaterialTheme.typography.headlineMedium)
            Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
