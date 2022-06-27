package com.example.youtube40.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.youtube40.core.network.status.Resource
import com.example.youtube40.data.remote.RemoteDataSource
import com.example.youtube40.data.remote.model.PlaylistModel
import kotlinx.coroutines.Dispatchers

class Repository(private val dataSource: RemoteDataSource) {

    fun getPlayList(): LiveData<Resource<PlaylistModel>> = liveData(Dispatchers.IO) {
        emit(Resource.loading()) // emit observes thread and returns data
        val response = dataSource.getPlaylist()
        emit(response)
    }
    fun getPlayListItem(id: String): LiveData<Resource<PlaylistModel>> = liveData(Dispatchers.IO) {
        emit(Resource.loading()) // emit observes thread and returns data
        val response = dataSource.getPlaylistItem(id)
        emit(response)
    }
}