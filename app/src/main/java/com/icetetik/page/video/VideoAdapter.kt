package com.icetetik.page.video

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icetetik.data.model.Video
import com.icetetik.databinding.ItemVideoBinding

class VideoAdapter(val context: Context, val callback: VideoItemCallback): RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    private val listVideo: ArrayList<Video> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun getItemCount(): Int = listVideo.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(listVideo[position])
    }

    fun setData(data: List<Video>){
        listVideo.clear()
        listVideo.addAll(data)
        notifyDataSetChanged()
    }

    inner class VideoViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video) {
            binding.apply {
                tvTitle.text = video.title
                root.setOnClickListener { callback.onItemVideoClick(video) }
                Glide.with(context)
                    .load("https://i.ytimg.com/vi/${video.id}/mqdefault.jpg")
                    .into(ivThumbnailVideo)
            }
        }
    }

}