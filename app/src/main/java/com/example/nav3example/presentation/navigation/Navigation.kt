package com.example.nav3example.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.presentation.components.BottomNavigationBar
import com.example.nav3example.presentation.components.TopBar
import com.example.nav3example.presentation.screen.details.DetailsScreen
import com.example.nav3example.presentation.screen.home.HomeScreen
import com.example.nav3example.presentation.screen.login.LoginScreen
import com.example.nav3example.presentation.screen.settings.SettingsScreen
import com.example.nav3example.presentation.screen.splash.SplashScreen
import com.example.nav3example.presentation.state.LoggedUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(
    navigationViewModel: NavigationViewModel = koinViewModel(),
) {
//    val homeBackHandler = remember { mutableStateOf<(() -> Unit)?>(null) }

//    fun handleBack() {
//        val isHome = navigationViewModel.backStack.lastOrNull() is HomeRoute
//        val handler = homeBackHandler.value
//
//        when {
//            isHome && handler != null -> handler()
//            navigationViewModel.backStack.size > 1 -> navigationViewModel.backStack.removeLastOrNull()
//        }
//    }

    NavDisplay(
        backStack = navigationViewModel.backStack,
        onBack = {
//            when (navigationViewModel.backStack.lastOrNull()) {
//                is HomeRoute -> {
//                    val handler = homeBackHandler.value
//                    if (handler != null) {
//                        handler()
//                    } else {
//                        if (navigationViewModel.backStack.size > 1) navigationViewModel.backStack.removeLastOrNull()
//                    }
//                }
//                else -> {
//                    if (navigationViewModel.backStack.size > 1) navigationViewModel.backStack.removeLastOrNull()
//                }
//            }
        },
        entryProvider = { route ->
            when (route) {
                is SplashRoute -> NavEntry(route) {
                    SplashScreen(
                        onNavigateToHome = {
                            navigationViewModel.clearAndNavigateTo(HomeRoute)
                        },
                        onNavigateToLogin = {
                            navigationViewModel.clearAndNavigateTo(LoginRoute)
                        }
                    )
                }

                is LoginRoute -> NavEntry(route) {
                    LoginScreen(
                        onNavigateToHome = {
                            navigationViewModel.clearAndNavigateTo(HomeRoute)
                        }
                    )
                }

                is HomeRoute -> NavEntry(route) {
                    val tabBackStack = remember { mutableStateListOf<Any>(BottomNavigation.Home) }

                    val uiState by produceState(initialValue = LoggedUiState()) {
                        navigationViewModel.uiState.collect { newState ->
                            value = newState
                        }
                    }

                    LaunchedEffect(uiState.shouldNavigateToLogin) {
                        if (uiState.shouldNavigateToLogin) {
                            navigationViewModel.clearAndNavigateTo(LoginRoute)
                            navigationViewModel.onLoggedOut()
                        }
                    }
//
//                    LaunchedEffect(Unit) {
//                        homeBackHandler.value = {
//                            if (tabBackStack.lastOrNull() !is BottomNavigation) {
//                                tabBackStack.clear()
//                                tabBackStack.add(BottomNavigation.Home)
//                            } else {
//                                navigationViewModel.backStack.removeLastOrNull()
//                            }
//                        }
//                    }
//
//                    DisposableEffect(Unit) {
//                        onDispose {
//                            homeBackHandler.value = null
//                        }
//                    }

                    Scaffold(
                        topBar = {
                            TopBar(
                                title = (tabBackStack.last() as BottomNavigation).title,
                                onLogoutClick = {
                                    navigationViewModel.logout()
                                }
                            )
                        },
                        bottomBar = {
                            BottomNavigationBar(
                                selectedTab = tabBackStack.last() as BottomNavigation,
                                onTabSelected = { tab ->
                                    tabBackStack.clear()
                                    tabBackStack.add(tab)
                                }
                            )
                        }
                    ) { _ ->
                        NavDisplay(
                            backStack = tabBackStack,
                            onBack = {
                                if (tabBackStack.last() as BottomNavigation != BottomNavigation.Home){
                                    navigationViewModel.clearAndNavigateTo(HomeRoute)
                                }
                            },
                            entryProvider = { tabRoute ->
                                when (tabRoute) {
                                    is BottomNavigation.Home -> {
                                        NavEntry(tabRoute) { HomeScreen() }
                                    }
                                    is BottomNavigation.Details -> {
                                        NavEntry(tabRoute) { DetailsScreen() }
                                    }
                                    is BottomNavigation.Settings -> {
                                        NavEntry(tabRoute) { SettingsScreen() }
                                    }
                                    else -> NavEntry(Unit) {}
                                }
                            }
                        )
                    }
                }

                else -> NavEntry(Unit) {}
            }
        }
    )
}