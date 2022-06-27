package com.example.youtube40.ui.playlists

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube40.R
import com.example.youtube40.core.extentions.loadImage
import com.example.youtube40.data.remote.model.PlayListItem
import com.example.youtube40.data.remote.model.PlaylistModel
import com.example.youtube40.databinding.ItemPlaylistsBinding

class PlaylistAdapter(private val playList: PlaylistModel, private val click: (id: PlayListItem) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPlaylistsBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(playList: PlayListItem) = with(binding) {
            itemPlaylists.context.loadImage(playList.snippet.thumbnails.high.url,itemPlaylists)
            tvPlaylistTitle.text = playList.snippet.title
            val videoCount = playList.contentDetail.itemCount.toString() + " Видео"
            tvVideoCount.text = videoCount
            // binding.tvVideoCount.text = "${playlists.contentDetails!!.itemCount}  "
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_playlists, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(playList.items[position])

        holder.itemView.setOnClickListener {
                click(playList.items[position])
        }

    }

    override fun getItemCount(): Int = playList.items.size

}