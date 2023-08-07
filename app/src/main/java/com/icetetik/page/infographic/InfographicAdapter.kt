package com.icetetik.page.infographic

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icetetik.data.model.Infographic
import com.icetetik.databinding.ItemVideoBinding

class InfographicAdapter(private val context: Context, val callback: InfographicItemCallback) :
    RecyclerView.Adapter<InfographicAdapter.InfographicViewHolder>() {

    private val listInfographic: ArrayList<Infographic> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfographicViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfographicViewHolder(binding)
    }

    override fun getItemCount(): Int = listInfographic.size

    override fun onBindViewHolder(holder: InfographicViewHolder, position: Int) {
        holder.bind(listInfographic[position])
    }

    fun setData(data: List<Infographic>){
        listInfographic.clear()
        listInfographic.addAll(data)
        notifyDataSetChanged()
    }

    inner class InfographicViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(infographic: Infographic) {
            binding.apply {
                tvTitle.text = infographic.title
                root.setOnClickListener {
                    callback.onItemInfographicClick(infographic)
                }
                Glide.with(context)
                    .load(infographic.url)
                    .into(ivThumbnailVideo)

            }
        }

    }


}