package com.example.youtube40.di

import com.example.youtube40.ui.playlists.PlaylistViewModel
import com.example.youtube40.ui.playlists.detailPlaylist.DetailPlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModules: Module = module {
    viewModel { PlaylistViewModel(prefs = get(), repository = get()) }
    viewModel { DetailPlaylistViewModel(prefs = get(), repository = get()) }
}