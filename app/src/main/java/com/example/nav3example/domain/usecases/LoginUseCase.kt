package com.example.nav3example.domain.usecases

import com.example.nav3example.domain.entity.User
import com.example.nav3example.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String) {
        val user = User(username, password)
        authRepository.saveUser(user)
    }
}