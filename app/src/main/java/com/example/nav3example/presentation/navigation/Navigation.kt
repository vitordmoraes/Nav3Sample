package com.example.nav3example.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.presentation.components.BottomNavigationBar
import com.example.nav3example.presentation.components.TopBar
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigation
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes
import com.example.nav3example.presentation.screen.login.LoginScreen
import com.example.nav3example.presentation.screen.splash.SplashScreen
import com.example.nav3example.presentation.state.LoggedUiState
import org.koin.androidx.compose.koinViewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import com.example.nav3example.presentation.animation.NavAnimations.homeFadeOrSlideAnimation
import com.example.nav3example.presentation.animation.NavAnimations.loginFadeOrSlideAnimation
import com.example.nav3example.presentation.animation.NavAnimations.longFadeAnimation

@Composable
fun Navigation(
    navigationViewModel: NavigationViewModel = koinViewModel(),
) {
    NavDisplay(
        backStack = navigationViewModel.backStack,
        onBack = {},
        entryProvider = entryProvider {
            entry<SplashRoute>(
                metadata = longFadeAnimation()
            ) {
                SplashScreen(
                    onNavigateToHome = {
                        navigationViewModel.navigateTo(HomeRoute)
                    },
                    onNavigateToLogin = {
                        navigationViewModel.navigateTo(LoginRoute)
                    }
                )
            }

            entry<LoginRoute>(
                metadata = loginFadeOrSlideAnimation(navigationViewModel.backStack)
            ) {
                LoginScreen(
                    onNavigateToHome = {
                        navigationViewModel.clearAndNavigateTo(HomeRoute)
                    }
                )
            }

            entry<HomeRoute>(metadata = homeFadeOrSlideAnimation(navigationViewModel.backStack)) {
                val tabBackStack = remember { mutableStateListOf<Any>(HomeNavigationRoutes.Home) }

                val uiState by produceState(initialValue = LoggedUiState()) {
                    navigationViewModel.uiState.collect { newState ->
                        value = newState
                    }
                }

                LaunchedEffect(uiState.shouldNavigateToLogin) {
                    if (uiState.shouldNavigateToLogin) {
                        navigationViewModel.navigateTo(LoginRoute)
                        navigationViewModel.onLoggedOut()
                    }
                }

                Scaffold(
                    topBar = {
                        TopBar(
                            title = (tabBackStack.last() as HomeNavigationRoutes).title,
                            onLogoutClick = {
                                navigationViewModel.logout()
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            selectedTab = tabBackStack.last() as HomeNavigationRoutes,
                            onTabSelected = { tab ->
                                tabBackStack.clear()
                                tabBackStack.add(tab)
                            }
                        )
                    }
                ) { _ ->
                    HomeNavigation(tabBackStack)
                }
            }
        }
    )
}