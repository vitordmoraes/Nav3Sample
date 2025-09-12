package com.example.nav3example.presentation.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nav3example.domain.usecases.LogoutUseCase
import com.example.nav3example.presentation.state.LoggedUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _backStack = mutableStateListOf<Any>(SplashRoute)
    val backStack: SnapshotStateList<Any> = _backStack

    private val _uiState = MutableStateFlow(LoggedUiState())
    val uiState: StateFlow<LoggedUiState> = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.value = _uiState.value.copy(shouldNavigateToLogin = true)
        }
    }

    fun onLoggedOut() {
        _uiState.value = _uiState.value.copy(shouldNavigateToLogin = false)
    }

    fun clearAndNavigateTo(route: Any) {
        backStack.clear()
        backStack.add(route)
    }
}