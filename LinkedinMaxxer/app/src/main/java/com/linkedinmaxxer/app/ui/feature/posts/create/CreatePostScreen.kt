package com.linkedinmaxxer.app.ui.feature.posts.create

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.linkedinmaxxer.app.ui.components.BottomNavBar
import com.linkedinmaxxer.app.ui.theme.Primary
import com.linkedinmaxxer.app.ui.theme.PrimaryContainer
import com.linkedinmaxxer.app.ui.theme.PrimaryFixed

@Composable
fun CreatePostScreen(
    data: CreatePostUIData,
    onAction: (CreatePostAction) -> Unit,
    onPublished: () -> Unit,
    onOpenHome: () -> Unit,
    onOpenPosts: () -> Unit,
    onOpenSubscriptions: () -> Unit,
    onOpenHints: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(data.errorMessage) {
        data.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(CreatePostAction.OnErrorShown)
        }
    }
    LaunchedEffect(data.successMessage) {
        data.successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onAction(CreatePostAction.OnSuccessShown)
        }
    }
    LaunchedEffect(data.publishCompleted) {
        if (data.publishCompleted) onPublished()
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedKey = "posts",
                onClick = { key ->
                    when (key) {
                        "home" -> onOpenHome()
                        "posts" -> onOpenPosts()
                        "subs" -> onOpenSubscriptions()
                        "hints" -> onOpenHints()
                        "settings" -> onOpenSettings()
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 18.dp, vertical = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // ── Header ───────────────────────────────────────────────────
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
                Button(
                    onClick = { onAction(CreatePostAction.OnPublishClicked) },
                    enabled = !data.isPublishing,
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    contentPadding = PaddingValues(horizontal = 22.dp, vertical = 11.dp),
                ) {
                    Text("Publish", fontWeight = FontWeight.Bold)
                }
            }

            // Extra space between header and editor
            Spacer(Modifier.height(8.dp))

            // ── Post text field ───────────────────────────────────────────
            OutlinedTextField(
                value = data.text,
                onValueChange = { onAction(CreatePostAction.OnTextChanged(it)) },
                placeholder = {
                    Text(
                        "What intelligence are you sharing today?",
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary.copy(alpha = 0.6f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                ),
            )
            Text(
                "${data.text.length} / ${data.maxLength}",
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            // ── Enhance with AI ───────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryFixed, RoundedCornerShape(20.dp))
                    .clickable(enabled = !data.isEnhancing && !data.isPublishing) {
                        onAction(CreatePostAction.OnEnhanceClicked)
                    }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(22.dp),
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            "Enhance with AI",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                        )
                        Text(
                            "Optimize for high-level engagement",
                            style = MaterialTheme.typography.bodySmall,
                            color = Primary.copy(alpha = 0.6f),
                        )
                    }
                }
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(20.dp),
                )
            }

            // ── Enhancing loading state ───────────────────────────────────
            if (data.isEnhancing) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Primary)
                }
            }

            // ── Enhanced result block ─────────────────────────────────────
            data.enhancedText?.let { enhanced ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Primary.copy(alpha = 0.05f), RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // "Optimized" pill
                    Text(
                        "Optimized",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryContainer,
                        modifier = Modifier
                            .background(PrimaryContainer.copy(alpha = 0.14f), RoundedCornerShape(999.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                    )
                    // Enhanced text
                    Text(
                        enhanced,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    // Apply button
                    Button(
                        onClick = { onAction(CreatePostAction.OnApplyEnhancementClicked) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        ),
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                        Text(
                            "Apply Optimization",
                            modifier = Modifier.padding(start = 6.dp),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}
