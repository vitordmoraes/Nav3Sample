package com.example.nav3example.domain.usecases

import com.example.nav3example.domain.repository.AuthRepository

class CheckUserLoggedUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}