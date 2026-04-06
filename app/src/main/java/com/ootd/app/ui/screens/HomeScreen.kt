package com.ootd.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ootd.app.ui.theme.MistBlue
import com.ootd.app.ui.theme.NudePink
import com.ootd.app.ui.theme.TextDark
import com.ootd.app.ui.theme.TextGrey

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Top Greeting
        Text(
            text = "今日宜元气满满",
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = TextDark,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "发现属于你的清晨之美",
            fontSize = 14.sp,
            color = TextGrey,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Weather Card
        WeatherCard()

        Spacer(modifier = Modifier.height(32.dp))

        // Recommendation Card
        RecommendationCard()
    }
}

@Composable
fun WeatherCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MistBlue.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "22°C / 晴", fontSize = 20.sp, color = TextDark)
                Text(text = "上海, 静安区", fontSize = 12.sp, color = TextGrey)
            }
            Icon(
                imageVector = Icons.Outlined.WbSunny,
                contentDescription = null,
                tint = MistBlue,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
fun RecommendationCard() {
    Column {
        Text(
            text = "今日推荐搭配",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = NudePink.copy(alpha = 0.2f)),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    Text(text = "搭配指数: 98", fontSize = 14.sp, color = TextDark)
                    Text(text = "契合度极高", fontSize = 12.sp, color = TextGrey)
                }
                
                Text(
                    text = "天气微凉，记得添衣哦",
                    fontSize = 16.sp,
                    color = TextDark,
                    modifier = Modifier.align(Alignment.BottomStart)
                )

                // Simulated Outfit Visual (Using Shapes)
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(MistBlue, RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.White, RoundedCornerShape(12.dp))
                    )
                }
            }
        }
    }
}
