package com.example.nav3example.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreferences(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREF_NAME, Context.MODE_PRIVATE
    )

    companion object {
        private const val PREF_NAME = "app_preferences"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveUser(username: String, password: String) {
        sharedPreferences.edit {
            putString(KEY_USERNAME, username)
                .putString(KEY_PASSWORD, password)
                .putBoolean(KEY_IS_LOGGED_IN, true)
        }
    }

    fun getUsername(): String? {
        return sharedPreferences.getString(KEY_USERNAME, null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(KEY_PASSWORD, null)
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearUser() {
        sharedPreferences.edit {
            remove(KEY_USERNAME)
                .remove(KEY_PASSWORD)
                .putBoolean(KEY_IS_LOGGED_IN, false)
        }
    }
}