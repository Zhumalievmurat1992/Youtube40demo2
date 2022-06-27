package com.example.youtube40.ui.playlists

import androidx.lifecycle.LiveData
import com.example.youtube40.core.network.status.Resource
import com.example.youtube40.core.ui.BaseViewModel
import com.example.youtube40.data.local.AppPrefs
import com.example.youtube40.data.remote.model.PlaylistModel
import com.example.youtube40.repository.Repository

class PlaylistViewModel(private val repository: Repository,private val prefs: AppPrefs) : BaseViewModel() {

    fun getPlayList(): LiveData<Resource<PlaylistModel>> {
        return repository.getPlayList()
    }
}