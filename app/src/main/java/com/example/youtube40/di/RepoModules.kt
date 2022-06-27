package com.example.youtube40.di

import com.example.youtube40.repository.Repository
import org.koin.core.module.Module
import org.koin.dsl.module

var repoModules : Module = module {
    single { Repository(get())}
}