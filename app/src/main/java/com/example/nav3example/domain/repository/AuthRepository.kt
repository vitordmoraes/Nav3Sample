package com.example.nav3example.domain.repository

import com.example.nav3example.domain.entity.User

interface AuthRepository {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun isUserLoggedIn(): Boolean
    suspend fun clearUser()
}