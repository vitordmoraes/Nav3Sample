package com.example.nav3example.data.repository

import com.example.nav3example.data.local.datasource.SharedPrefsDataSource
import com.example.nav3example.domain.entity.User
import com.example.nav3example.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val localDataSource: SharedPrefsDataSource
) : AuthRepository {

    override suspend fun saveUser(user: User) {
        localDataSource.saveUser(user)
    }

    override suspend fun getUser(): User? {
        return localDataSource.getUser()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return localDataSource.isUserLoggedIn()
    }

    override suspend fun clearUser() {
        localDataSource.clearUser()
    }
}