package com.example.nav3example.domain.usecases

import com.example.nav3example.domain.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.clearUser()
    }
}