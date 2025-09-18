package com.example.nav3example.presentation.screen.home.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes.Home
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes.Details
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes.Settings
import com.example.nav3example.presentation.screen.details.DetailsScreen
import com.example.nav3example.presentation.screen.home.HomeScreen
import com.example.nav3example.presentation.screen.settings.SettingsScreen
import kotlinx.coroutines.launch

@Composable
fun HomeNavigation(tabBackStack: SnapshotStateList<Any>) {

    val coroutineScope = rememberCoroutineScope()

    val screens = remember {
        listOf(Details, Home, Settings)
    }
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { screens.size }
    )

    BackHandler(enabled = pagerState.currentPage != 1) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(1)
        }
    }

    LaunchedEffect(tabBackStack.lastOrNull()) {
        val currentRoute = tabBackStack.lastOrNull() as? HomeNavigationRoutes ?: Home
        val targetPage = screens.indexOf(currentRoute).takeIf { it >= 0 } ?: 1

        if (pagerState.currentPage != targetPage) {
            pagerState.animateScrollToPage(targetPage)
        }
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            val currentRoute = screens[pagerState.currentPage]
            val currentBackStackRoute = tabBackStack.lastOrNull() as? HomeNavigationRoutes

            if (currentBackStackRoute != currentRoute) {
                when (currentRoute) {
                    Home -> {
                        tabBackStack.clear()
                        tabBackStack.add(Home)
                    }
                    Details -> {
                        tabBackStack.clear()
                        tabBackStack.add(Details)
                    }
                    Settings -> {
                        tabBackStack.clear()
                        tabBackStack.add(Settings)
                    }
                }
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = true,
    ) { page ->
        when (screens[page]) {
            Settings -> {
                SettingsScreen()
            }

            Home -> {
                HomeScreen()
            }

            Details -> {
                DetailsScreen()
            }
        }
    }
}