package com.example.nav3example.presentation.screen.home.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.example.nav3example.presentation.components.AddItemDialog
import com.example.nav3example.presentation.components.BottomNavigationBar
import com.example.nav3example.presentation.components.TopBar
import com.example.nav3example.presentation.navigation.LoginRoute
import com.example.nav3example.presentation.navigation.NavigationViewModel
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes.Home
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes.Details
import com.example.nav3example.presentation.screen.home.navigation.HomeNavigationRoutes.Settings
import com.example.nav3example.presentation.screen.details.DetailsScreen
import com.example.nav3example.presentation.screen.home.HomeScreen
import com.example.nav3example.presentation.screen.settings.SettingsScreen
import com.example.nav3example.presentation.state.LoggedUiState
import kotlinx.coroutines.launch

@Composable
fun HomeScaffolding(
    tabBackStack: SnapshotStateList<Any>,
    navigationViewModel: NavigationViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }

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


    val screens = remember {
        listOf(Details, Home, Settings)
    }
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { screens.size }
    )

    Scaffold(
        topBar = {
            TopBar(
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
        },
        floatingActionButton = {
            if (tabBackStack.lastOrNull() is Home) {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    ) { _ ->

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

        if (showDialog) {
            AddItemDialog(
                itemName = itemName,
                onItemNameChange = { itemName = it },
                onConfirm = {
                    if (itemName.isNotBlank()) {
                        showDialog = false
                    }
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }
    }
}