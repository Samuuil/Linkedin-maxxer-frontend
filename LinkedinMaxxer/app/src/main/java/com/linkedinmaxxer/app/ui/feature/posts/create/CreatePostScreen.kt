package com.linkedinmaxxer.app.ui.feature.posts.create

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun CreatePostScreen(
    data: CreatePostUIData,
    onAction: (CreatePostAction) -> Unit,
    onPublished: () -> Unit,
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

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 18.dp, vertical = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier
                            .background(PrimaryContainer.copy(alpha = 0.2f), CircleShape)
                            .padding(8.dp),
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Primary)
                    }
                    Text("Executive Lens", style = MaterialTheme.typography.titleMedium, color = Primary)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Button(
                        onClick = { onAction(CreatePostAction.OnPublishClicked) },
                        enabled = !data.isPublishing,
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    ) { Text("Publish") }
                }
            }

            OutlinedTextField(
                value = data.text,
                onValueChange = { onAction(CreatePostAction.OnTextChanged(it)) },
                placeholder = { Text("What intelligence are you sharing today?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
            )
            Text(
                "${data.text.length} / ${data.maxLength}",
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Button(
                onClick = { onAction(CreatePostAction.OnEnhanceClicked) },
                enabled = !data.isEnhancing && !data.isPublishing,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer.copy(alpha = 0.25f), contentColor = Primary),
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null)
                Spacer(modifier = Modifier.weight(1f))
                Text(if (data.isEnhancing) "Enhancing..." else "Enhance with AI")
            }

            if (data.isEnhancing) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            data.enhancedText?.let { enhanced ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(14.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Refinement Preview", style = MaterialTheme.typography.labelSmall, color = Primary)
                    Text(enhanced, style = MaterialTheme.typography.bodyMedium)
                    Button(
                        onClick = { onAction(CreatePostAction.OnApplyEnhancementClicked) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                        Text("Apply Optimization", modifier = Modifier.padding(start = 6.dp))
                    }
                }
            }
        }
    }
}
