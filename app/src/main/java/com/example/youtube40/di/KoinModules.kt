package com.example.youtube40.di

import com.example.youtube40.core.network.networkModule
import com.example.youtube40.core.utils.connectivityStatus
import com.example.youtube40.data.local.prefModule

var koinModules = listOf(
    repoModules,
    viewModules,
    networkModule,
    prefModule,
    connectivityStatus
)

