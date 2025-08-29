package com.example.nav3example.di

import com.example.nav3example.presentation.screen.home.HomeViewModel
import com.example.nav3example.presentation.screen.login.LoginViewModel
import com.example.nav3example.presentation.screen.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}