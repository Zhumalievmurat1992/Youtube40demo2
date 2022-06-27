package com.example.youtube40.ui.playlists.detailPlaylist


import android.os.Build
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube40.R
import com.example.youtube40.core.network.status.Status
import com.example.youtube40.core.ui.BaseActivity
import com.example.youtube40.core.utils.ConnectivityStatus
import com.example.youtube40.data.remote.model.PlaylistModel
import com.example.youtube40.databinding.ActivityDetailPlaylistBinding
import com.example.youtube40.ui.playlists.PlaylistsActivity.Companion.PLAYLIST
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@RequiresApi(Build.VERSION_CODES.O)
class DetailPlaylist : BaseActivity<DetailPlaylistViewModel, ActivityDetailPlaylistBinding>() {

    override val viewModel: DetailPlaylistViewModel by viewModel()
    private val connectivityStatus: ConnectivityStatus by inject()

    private lateinit var layout: ConstraintLayout

    private lateinit var detailList: PlaylistModel
    private val adapter by lazy { DetailPlaylistAdapter(detailList) }
//    private val detailAdapter: DetailPlaylistAdapter by lazy {
//        DetailPlaylistAdapter(detailList)
//    }


    override fun initView() {
        super.initView()
        layout = findViewById(R.id.no_internet_check)

    }

    private fun checkConnectivity() {
        connectivityStatus.observe(this) { isConnected ->
            if (isConnected) {
                layout.visibility = View.GONE

            } else {
                layout.visibility = View.VISIBLE

            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        checkConnectivity()

        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it
        }


        viewModel.getPlaylistItem(intent.getStringExtra(PLAYLIST).toString()).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) with(binding) {
                        detailList = it.data
                        tvTitle.text = detailList.items[0].snippet.title
                        tvDescription.text = detailList.items[0].snippet.description
                        tvDescription.movementMethod = ScrollingMovementMethod()
                        (detailList.items.size.toString() + getString(0)).also { it1 ->
                            binding.tvVideos.text = it1
                        }
                            initRecycler()
                        viewModel.loading.postValue(false)
                        progressBar.isVisible = false
                    }

                }
                Status.LOADING -> {
                    viewModel.loading.postValue(true)
                    binding.progressBar.isVisible = true
                }
                Status.ERROR -> {
                    viewModel.loading.postValue(false)
                    binding.progressBar.isVisible = false
                }
            }
        }

    }
//    private fun click(id: ContentDetail) {
//        Intent(this, VideoActivity::class.java).apply {
//            putExtra(PLAYLIST_VIDEO, id.videoId)
//            startActivity(this)
//        }
//    }

    override fun initListener() {
        super.initListener()
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }


    private fun initRecycler() {
        binding.recyclerPlaylistDetail.apply {
            layoutManager = LinearLayoutManager(this@DetailPlaylist)
            adapter = this@DetailPlaylist.adapter
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailPlaylistBinding {
        return ActivityDetailPlaylistBinding.inflate(inflater)
    }
}