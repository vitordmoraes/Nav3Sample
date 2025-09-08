package com.example.nav3example.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.nav3example.presentation.screen.home.HomeNavigation
import com.example.nav3example.presentation.screen.home.HomeNavigation.Home
import com.example.nav3example.presentation.screen.home.HomeNavigation.Details
import com.example.nav3example.presentation.screen.home.HomeNavigation.Settings



@Composable
fun BottomNavigationBar(
    selectedTab: HomeNavigation,
    onTabSelected: (HomeNavigation) -> Unit
) {
    val tabs = listOf(Details, Home, Settings)

    NavigationBar {
        tabs.forEach { tab ->
            NavigationBarItem(
                icon = { Icon(tab.icon, contentDescription = tab.title) },
                label = { Text(tab.title) },
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}