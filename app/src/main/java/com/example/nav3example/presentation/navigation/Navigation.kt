package com.example.nav3example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.presentation.screen.details.DetailsScreen
import com.example.nav3example.presentation.screen.home.HomeScreen
import com.example.nav3example.presentation.screen.login.LoginScreen
import com.example.nav3example.presentation.screen.settings.SettingsScreen
import com.example.nav3example.presentation.screen.splash.SplashScreen

@Composable
fun Navigation() {
    val backStack = remember { mutableStateListOf<Any>(SplashRoute) }

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = { route ->
            when (route) {
                is SplashRoute -> NavEntry(route) {
                    SplashScreen(
                        onNavigateToHome = {
                            backStack.clear()
                            backStack.add(HomeRoute)
                        },
                        onNavigateToLogin = {
                            backStack.clear()
                            backStack.add(LoginRoute)
                        }
                    )
                }

                is LoginRoute -> NavEntry(route) {
                    LoginScreen(
                        onNavigateToHome = {
                            backStack.clear()
                            backStack.add(HomeRoute)
                        }
                    )
                }

                is HomeRoute -> NavEntry(route) {
                    HomeScreen(
                        onNavigateToLogin = {
                            backStack.clear()
                            backStack.add(LoginRoute)
                        }
                    )
                }

                is DetailsRoute -> NavEntry(route) {
                    DetailsScreen(
                        onNavigateBack = {
                            backStack.removeLastOrNull()
                        }
                    )
                }

                is SettingsRoute -> NavEntry(route) {
                    SettingsScreen(
                        onNavigateBack = {
                            backStack.removeLastOrNull()
                        }
                    )
                }

                else -> NavEntry(Unit) {}
            }
        }
    )
}