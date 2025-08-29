package com.example.nav3example.presentation.state

data class SplashUiState(
    val isLoading: Boolean = true,
    val shouldNavigateToHome: Boolean = false,
    val shouldNavigateToLogin: Boolean = false
)