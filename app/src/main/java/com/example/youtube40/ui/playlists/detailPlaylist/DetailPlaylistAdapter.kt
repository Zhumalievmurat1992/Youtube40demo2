package com.example.youtube40.ui.playlists.detailPlaylist

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube40.R
import com.example.youtube40.core.extentions.loadImage
import com.example.youtube40.data.remote.model.PlayListItem
import com.example.youtube40.data.remote.model.PlaylistModel
import com.example.youtube40.databinding.ItemDetailBinding
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class DetailPlaylistAdapter(
    private var playlist: PlaylistModel
) :
    RecyclerView.Adapter<DetailPlaylistAdapter.Holder>() {


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDetailBinding.bind(itemView)
        fun bind(playlist: PlayListItem) {
            binding.tvPlaylistTitle.text = playlist.snippet.title
            val date = playlist.snippet.publishedAt
            val dateTime = OffsetDateTime.parse(date)
            val location = dateTime.withOffsetSameInstant(ZoneOffset.ofHours(-2))
            binding.tvCountOfVideos.text = String.format(location.format(DateTimeFormatter.ofPattern("dd-MM-uuuu ")))
            binding.ivPlaylists.context.loadImage(playlist.snippet.thumbnails.default.url,binding.ivPlaylists)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(playlist.items[position])
    }

    override fun getItemCount(): Int = playlist.items.size


}