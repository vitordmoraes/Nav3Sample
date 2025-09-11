package com.example.nav3example.presentation.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nav3example.presentation.components.BottomNavigationBar
import com.example.nav3example.presentation.components.TopBar
import com.example.nav3example.presentation.navigation.HomeNavigation
import com.example.nav3example.presentation.state.HomeUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {

    var selectedTab : HomeNavigation by remember { mutableStateOf(HomeNavigation.Home) }
    val uiState by produceState(initialValue = HomeUiState()) {
        viewModel.uiState.collect { newState ->
            value = newState
        }
    }

    LaunchedEffect(uiState.shouldNavigateToLogin) {
        if (uiState.shouldNavigateToLogin) {
            onNavigateToLogin()
        }
    }

    BackHandler {}

    Scaffold(
        topBar = {
            TopBar(
                title = selectedTab.title,
                onLogoutClick = { viewModel.logout() }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {

                is HomeNavigation.Home -> {
                    Text(
                        text = "Home Content",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                is HomeNavigation.Details -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Details Content",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Informações detalhadas sobre o app",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                is HomeNavigation.Settings -> {
                    Text(
                        text = "TODO",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}