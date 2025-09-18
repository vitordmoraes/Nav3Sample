package com.example.nav3example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.presentation.screen.home.navigation.HomeScaffolding
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes
import com.example.nav3example.presentation.screen.login.LoginScreen
import com.example.nav3example.presentation.screen.splash.SplashScreen
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

                HomeScaffolding(tabBackStack, navigationViewModel)
            }
        }
    )
}