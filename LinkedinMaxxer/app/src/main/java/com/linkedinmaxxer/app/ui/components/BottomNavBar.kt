package com.linkedinmaxxer.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(vertical = 8.dp, horizontal = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        items.forEach { item ->
            val isSelected = item.key == selectedKey
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick(item.key) }
                    .padding(vertical = 4.dp, horizontal = 2.dp)
                    .background(
                        if (isSelected) PrimaryContainer.copy(alpha = 0.2f) else androidx.compose.ui.graphics.Color.Transparent,
                        RoundedCornerShape(14.dp),
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(1.dp),
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = if (isSelected) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = item.label.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp,
                    color = if (isSelected) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
