package com.ootd.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ootd.app.ui.theme.*

@Composable
fun WardrobeScreen() {
    var selectedCategory by remember { mutableStateOf("全部") }
    val categories = listOf("全部", "上衣", "裤子", "裙装", "配饰")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "我的衣橱",
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = TextDark,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Categories
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(categories) { category ->
                    CategoryItem(
                        name = category,
                        isSelected = selectedCategory == category,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            // Grid of Clothes
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(6) { index ->
                    ClothItem(index)
                }
            }
        }

        // Add Button
        LargeFloatingActionButton(
            onClick = { /* Simulate picking image */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp),
            containerColor = MistBlue,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(Icons.Outlined.Add, contentDescription = "Add Clothes")
        }
    }
}

@Composable
fun CategoryItem(name: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = name,
        fontSize = 16.sp,
        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Light,
        color = if (isSelected) MistBlue else TextGrey,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
    )
}

@Composable
fun ClothItem(index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Simulated clothing item
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (index % 2 == 0) MistBlue.copy(alpha = 0.05f) else NudePink.copy(alpha = 0.05f))
            )
            
            // Just a placeholder text for clothes
            Text(
                text = "春季单品 #$index",
                fontSize = 12.sp,
                color = TextGrey,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
            )
        }
    }
}
