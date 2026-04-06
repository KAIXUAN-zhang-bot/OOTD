package com.ootd.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ootd.app.ui.theme.*

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "个人偏好",
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = TextDark,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Preferences Group
        PreferenceGroup(title = "我的风格") {
            PreferenceItem(title = "穿搭风格", subtitle = "清雅、极简", onClick = {})
            PreferenceItem(title = "色彩体系", subtitle = "莫兰迪色系", onClick = {})
        }

        Spacer(modifier = Modifier.height(32.dp))

        PreferenceGroup(title = "我的身材") {
            PreferenceItem(title = "体型设定", subtitle = "匀称 H型", onClick = {})
            PreferenceItem(title = "肤色倾向", subtitle = "冷白皮", onClick = {})
        }
    }
}

@Composable
fun PreferenceGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title,
            fontSize = 14.sp,
            color = MistBlue,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                content()
            }
        }
    }
}

@Composable
fun PreferenceItem(title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = title, fontSize = 16.sp, color = TextDark)
            Text(text = subtitle, fontSize = 12.sp, color = TextGrey)
        }
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = TextGrey.copy(alpha = 0.5f),
            modifier = Modifier.size(20.dp)
        )
    }
}
