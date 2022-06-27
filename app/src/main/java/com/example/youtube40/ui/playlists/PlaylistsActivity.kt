package com.example.youtube40.ui.playlists

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube40.R
import com.example.youtube40.core.extentions.showMessage
import com.example.youtube40.core.network.status.Status
import com.example.youtube40.core.ui.BaseActivity
import com.example.youtube40.core.utils.ConnectivityStatus
import com.example.youtube40.data.remote.model.PlayListItem
import com.example.youtube40.data.remote.model.PlaylistModel
import com.example.youtube40.databinding.ActivityPlaylistsBinding
import com.example.youtube40.ui.playlists.detailPlaylist.DetailPlaylist
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsActivity : BaseActivity<PlaylistViewModel, ActivityPlaylistsBinding>() {

    override val viewModel: PlaylistViewModel by viewModel()
    private val connectivityStatus: ConnectivityStatus by inject()
    private lateinit var playList: PlaylistModel
    private val adapter by lazy { PlaylistAdapter(playList,this::click ) }
    private lateinit var layout: ConstraintLayout
    private lateinit var rv: RecyclerView

    override fun initView() {
        super.initView()
        layout = findViewById(R.id.check_internet)
         rv = findViewById(R.id.rv)

    }

    private fun click(id: PlayListItem) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent(this, DetailPlaylist::class.java).apply {
                putExtra(PLAYLIST, id.id)
                startActivity(this)
            }
        }

    }

    private fun checkConnectivity() {
        connectivityStatus.observe(this) { isConnected ->
            if (isConnected) {
                layout.visibility = View.GONE
                rv.visibility = View.VISIBLE
                Log.e("ololo", "checkConnectivity: $isConnected")
            } else {
                layout.visibility = View.VISIBLE
                rv.visibility = View.GONE
                Log.e("ololo", "checkConnectivity: $isConnected")
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        checkConnectivity()

        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it
        }

        viewModel.getPlayList().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
                        playList = it.data
                        viewModel.loading.postValue(false)
                        setupRecycler()

                    }
                }
                Status.LOADING -> {
                    viewModel.loading.postValue(true)
                    binding.progressBar.isVisible = true
                }
                Status.ERROR -> {
                    it.msg?.let { it1 -> showMessage(it1) }
                    viewModel.loading.postValue(false)
                }

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecycler() {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@PlaylistsActivity)
            adapter = this@PlaylistsActivity.adapter
        }
        adapter.notifyDataSetChanged()
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistsBinding {
        return ActivityPlaylistsBinding.inflate(inflater)
    }

//    override fun click(item: Item) {
//
//        Intent(this, DetailPlaylist::class.java).apply {
//            putExtra(PLAYLIST, item.id)
//            startActivity(this)
//        }
//
////
////        val list = arrayListOf<String>()
////        val intent = Intent(this,DetailPlaylist::class.java)
////        intent.putExtra(Constant.id,item.id)
////        val bundle = Bundle()
////        bundle.putStringArrayList(Constant.id,list)
////        showMessage(item.id)
////        startActivity(intent)
//    }

    companion object {
        const val PLAYLIST = "playlist"
        const val PLAYLIST_VIDEO = "playlistVideo"
    }

}