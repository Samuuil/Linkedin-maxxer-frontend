package com.linkedinmaxxer.app.ui.feature.hints

import android.widget.Toast
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Insights
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.linkedinmaxxer.app.domain.model.response.CommentSuggestionResponse
import com.linkedinmaxxer.app.ui.components.BottomNavBar
import com.linkedinmaxxer.app.ui.theme.InterFamily
import com.linkedinmaxxer.app.ui.theme.ManropeFamily
import com.linkedinmaxxer.app.ui.theme.Primary
import com.linkedinmaxxer.app.ui.theme.PrimaryContainer

@Composable
fun HintsScreen(
    data: HintsUIData,
    onAction: (HintsAction) -> Unit,
    onOpenSuggestion: (String) -> Unit,
    onOpenHome: () -> Unit,
    onOpenPosts: () -> Unit,
    onOpenSubs: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(data.errorMessage) {
        data.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(HintsAction.OnErrorShown)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedKey = "hints",
                onClick = { key ->
                    when (key) {
                        "home" -> onOpenHome()
                        "posts" -> onOpenPosts()
                        "subs" -> onOpenSubs()
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

                Text(
                    "AI Hints",
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "AI-generated comment suggestions from your subscriptions.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = InterFamily,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(Modifier.height(16.dp))

                // ── Segmented filter pill ─────────────────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerHighest,
                            RoundedCornerShape(999.dp),
                        )
                        .padding(4.dp),
                ) {
                    Row {
                        HintsFilter.entries.forEach { filter ->
                            val isSelected = data.filter == filter
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        if (isSelected) Color.White else Color.Transparent,
                                        RoundedCornerShape(999.dp),
                                    )
                                    .clickable { onAction(HintsAction.OnFilterChanged(filter)) }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = filter.name
                                        .lowercase()
                                        .replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.labelLarge,
                                    fontFamily = InterFamily,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }
            }

            // ── Suggestions ───────────────────────────────────────────────
            if (data.suggestions.isEmpty()) {
                item {
                    Spacer(Modifier.height(8.dp))
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
                                "No suggestions yet",
                                style = MaterialTheme.typography.titleMedium,
                                fontFamily = ManropeFamily,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                "Suggestions generated by your subscriptions will appear here.",
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = InterFamily,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            } else {
                items(data.suggestions, key = { it.id }) { item ->
                    SuggestionCard(
                        item = item,
                        onRespond = { approve -> onAction(HintsAction.OnRespond(item.id, approve)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun SuggestionCard(
    item: CommentSuggestionResponse,
    onRespond: (Boolean) -> Unit,
) {
    var postExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // ── Header: avatar + username + status badge ──────────────────
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
                            .size(44.dp)
                            .background(PrimaryContainer.copy(alpha = 0.14f), CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = item.linkedinUsername.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryContainer,
                        )
                    }
                    Column {
                        Text(
                            item.linkedinUsername,
                            style = MaterialTheme.typography.titleSmall,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            item.createdAt.substringBefore("T"),
                            style = MaterialTheme.typography.labelSmall,
                            fontFamily = InterFamily,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                StatusBadge(item.status)
            }

            // ── Source post ───────────────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    "SOURCE POST",
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = InterFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceContainer,
                            RoundedCornerShape(12.dp),
                        )
                        .clickable { postExpanded = !postExpanded }
                        .padding(10.dp),
                ) {
                    Text(
                        text = "\u201C${item.postDescription}\u201D",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = InterFamily,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = if (postExpanded) Int.MAX_VALUE else 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            // ── AI suggestion ─────────────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(14.dp),
                    )
                    Text(
                        "AI SUGGESTION",
                        style = MaterialTheme.typography.labelSmall,
                        fontFamily = InterFamily,
                        fontWeight = FontWeight.Bold,
                        color = Primary,
                    )
                }
                Text(
                    item.suggestedComment,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryContainer,
                )
            }

            // ── Action buttons (PENDING only) ─────────────────────────────
            if (item.status == "PENDING") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Button(
                        onClick = { onRespond(false) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        ),
                    ) {
                        Text(
                            "Reject",
                            fontFamily = InterFamily,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Button(
                        onClick = { onRespond(true) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    ) {
                        Text(
                            "Approve",
                            fontFamily = InterFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: String) {
    val (bg, textColor) = when (status) {
        "PENDING" -> Color(0xFFFFE0B2) to Color(0xFFE65100)
        "APPROVED" -> Color(0xFFC8E6C9) to Color(0xFF2E7D32)
        "REJECTED" -> Color(0xFFFFDAD6) to Color(0xFFBA1A1A)
        else -> MaterialTheme.colorScheme.surfaceContainerLow to MaterialTheme.colorScheme.onSurfaceVariant
    }
    Text(
        text = status,
        style = MaterialTheme.typography.labelSmall,
        fontFamily = InterFamily,
        fontWeight = FontWeight.Bold,
        color = textColor,
        modifier = Modifier
            .background(bg, RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}
