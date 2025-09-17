package com.example.nav3example.presentation.screen.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.presentation.navigation.HomeRoute
import com.example.nav3example.presentation.navigation.NavigationViewModel
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes.Home
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes.Details
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes.Settings
import com.example.nav3example.presentation.screen.details.DetailsScreen
import com.example.nav3example.presentation.screen.home.HomeScreen
import com.example.nav3example.presentation.screen.settings.SettingsScreen

@Composable
fun HomeNavigation(
    tabBackStack: SnapshotStateList<Any>,
    navigationViewModel: NavigationViewModel
) {
    NavDisplay(
        backStack = tabBackStack,
        onBack = {
            if (tabBackStack.last() as HomeNavigationRoutes != Home) {
                navigationViewModel.clearAndNavigateTo(HomeRoute)
            }
        },
        entryProvider = entryProvider {
            entry<Home> {
                HomeScreen()
            }

            entry<Details> {
                DetailsScreen()
            }

            entry<Settings> {
                SettingsScreen()
            }
        }
    )
}