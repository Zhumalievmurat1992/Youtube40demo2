package com.example.youtube40.data.local

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val prefModule = module {
    single { AppPrefs(androidContext()) }
}

class AppPrefs(context: Context) {

    private val prefs = context.getSharedPreferences("app_prefs",Context.MODE_PRIVATE)

    var onBoard: Boolean
    get() = prefs.getBoolean("onBoard",false)
    set(value) = prefs.edit().putBoolean("onBoard",value).apply()
}