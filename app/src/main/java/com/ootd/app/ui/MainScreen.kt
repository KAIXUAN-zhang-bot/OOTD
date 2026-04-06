package com.ootd.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ootd.app.ui.screens.HomeScreen
import com.ootd.app.ui.screens.ProfileScreen
import com.ootd.app.ui.screens.WardrobeScreen
import com.ootd.app.ui.theme.MistBlue
import com.ootd.app.ui.theme.TextGrey

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "首页", Icons.Outlined.Home)
    object Wardrobe : Screen("wardrobe", "衣橱", Icons.Outlined.Checkroom)
    object Profile : Screen("profile", "我的", Icons.Outlined.Person)
}

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = MistBlue,
                tonalElevation = 0.dp
            ) {
                listOf(Screen.Home, Screen.Wardrobe, Screen.Profile).forEach { screen ->
                    NavigationBarItem(
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MistBlue,
                            selectedTextColor = MistBlue,
                            unselectedIconColor = TextGrey,
                            unselectedTextColor = TextGrey,
                            indicatorColor = MistBlue.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentScreen) {
                is Screen.Home -> HomeScreen()
                is Screen.Wardrobe -> WardrobeScreen()
                is Screen.Profile -> ProfileScreen()
            }
        }
    }
}
