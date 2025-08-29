package com.example.nav3example.data.local.datasource

import com.example.nav3example.domain.entity.User

class SharedPrefsDataSource(
    private val appPreferences: AppPreferences
) {
    suspend fun saveUser(user: User) {
        appPreferences.saveUser(user.username, user.password)
    }

    suspend fun getUser(): User? {
        val username = appPreferences.getUsername()
        val password = appPreferences.getPassword()

        return if (username != null && password != null) {
            User(username, password)
        } else {
            null
        }
    }

    suspend fun isUserLoggedIn(): Boolean {
        return appPreferences.isUserLoggedIn()
    }

    suspend fun clearUser() {
        appPreferences.clearUser()
    }
}