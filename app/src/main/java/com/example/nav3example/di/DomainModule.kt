package com.example.nav3example.di

import com.example.nav3example.domain.usecases.CheckUserLoggedUseCase
import com.example.nav3example.domain.usecases.LoginUseCase
import com.example.nav3example.domain.usecases.LogoutUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { CheckUserLoggedUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
}