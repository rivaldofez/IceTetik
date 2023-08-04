package com.icetetik.page.infographic

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icetetik.data.model.Video
import com.icetetik.databinding.ItemImageInfographicBinding

class InfographicAdapter(private val context: Context) :
    RecyclerView.Adapter<InfographicAdapter.InfographicViewPagerHolder>() {

    private val listImage: ArrayList<Int> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfographicViewPagerHolder {
        val binding = ItemImageInfographicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfographicViewPagerHolder(binding)
    }

    override fun getItemCount(): Int = listImage.size

    override fun onBindViewHolder(holder: InfographicViewPagerHolder, position: Int) {
        holder.bind(listImage[position])
    }

    fun setData(data: List<Int>){
        listImage.clear()
        listImage.addAll(data)
        notifyDataSetChanged()
    }

    inner class InfographicViewPagerHolder(private val binding: ItemImageInfographicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Int) {
            Glide.with(context)
                .load(image)
                .into(binding.ivInfographic)
        }

    }


}