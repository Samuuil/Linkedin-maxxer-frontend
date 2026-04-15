package com.linkedinmaxxer.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.linkedinmaxxer.app.ui.theme.Primary
import com.linkedinmaxxer.app.ui.theme.PrimaryContainer

data class BottomNavItem(
    val key: String,
    val label: String,
    val icon: ImageVector,
)

@Composable
fun BottomNavBar(
    selectedKey: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val items = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("posts", "Posts", Icons.Default.Article),
        BottomNavItem("subs", "Subs", Icons.Default.Subscriptions),
        BottomNavItem("hints", "AI Hints", Icons.Default.AutoAwesome),
        BottomNavItem("settings", "Settings", Icons.Default.Settings),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach { item ->
            val isSelected = item.key == selectedKey
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick(item.key) }
                    .padding(vertical = 1.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 42.dp)
                        .widthIn(min = 62.dp)
                        .background(
                            if (isSelected) PrimaryContainer.copy(alpha = 0.35f) else Color.Transparent,
                            RoundedCornerShape(14.dp),
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier
                            .padding(bottom = 1.dp)
                            .defaultMinSize(minWidth = 18.dp, minHeight = 18.dp),
                        tint = if (isSelected) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = item.label.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 9.sp,
                        color = if (isSelected) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        letterSpacing = 0.6.sp,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}
