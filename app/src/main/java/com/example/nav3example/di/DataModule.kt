package com.example.nav3example.di

import com.example.nav3example.data.local.datasource.SharedPrefsDataSource
import com.example.nav3example.data.local.preferences.AppPreferences
import com.example.nav3example.data.repository.AuthRepositoryImpl
import com.example.nav3example.domain.repository.AuthRepository
import org.koin.dsl.module

val dataModule = module {

    single { AppPreferences(get()) }

    single { SharedPrefsDataSource(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
}