package com.example.nav3example.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigation(val route: String, val title: String, val icon: ImageVector) {

    private companion object {
        const val HOME_TAB = "home_tab"
        const val DETAILS_TAB = "details_tab"
        const val SETTINGS_TAB = "settings_tab"
    }

    data object Home : BottomNavigation(HOME_TAB, "Home", Icons.Default.Home)
    data object Details : BottomNavigation(DETAILS_TAB, "Details", Icons.Default.Info)
    data object Settings : BottomNavigation(SETTINGS_TAB, "Settings", Icons.Default.Settings)
}