package com.example.nav3example.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nav3example.presentation.state.SplashUiState
import com.example.nav3example.domain.usecases.CheckUserLoggedUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkUserLoggedUseCase: CheckUserLoggedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    fun checkUserLogin() {
        viewModelScope.launch {
            delay(2000)

            val isLoggedIn = checkUserLoggedUseCase()

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                shouldNavigateToHome = isLoggedIn,
                shouldNavigateToLogin = !isLoggedIn
            )
            uiState
        }
    }
}