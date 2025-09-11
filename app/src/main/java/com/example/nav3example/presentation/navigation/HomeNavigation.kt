package com.example.nav3example.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeNavigation(val route: String, val title: String, val icon: ImageVector) {
    data object Home : HomeNavigation("home_tab", "Home", Icons.Default.Home)
    data object Details : HomeNavigation("details_tab", "Details", Icons.Default.Info)
    data object Settings : HomeNavigation("settings_tab", "Settings", Icons.Default.Settings)
}