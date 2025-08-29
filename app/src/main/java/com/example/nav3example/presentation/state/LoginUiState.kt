package com.example.nav3example.presentation.state

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val shouldNavigateToHome: Boolean = false
)