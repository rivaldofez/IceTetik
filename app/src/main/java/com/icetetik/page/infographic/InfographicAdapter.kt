package com.icetetik.page.infographic

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icetetik.data.model.Infographic
import com.icetetik.databinding.ItemImageInfographicBinding

class InfographicAdapter(private val context: Context) :
    RecyclerView.Adapter<InfographicAdapter.InfographicViewPagerHolder>() {

    private val listImage: ArrayList<Infographic> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfographicViewPagerHolder {
        val binding = ItemImageInfographicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfographicViewPagerHolder(binding)
    }

    override fun getItemCount(): Int = listImage.size

    override fun onBindViewHolder(holder: InfographicViewPagerHolder, position: Int) {
        holder.bind(listImage[position])
    }

    fun setData(data: List<Infographic>){
        listImage.clear()
        listImage.addAll(data)
        notifyDataSetChanged()
    }

    inner class InfographicViewPagerHolder(private val binding: ItemImageInfographicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(infographic: Infographic) {
            Glide.with(context)
                .load(infographic.url)
                .into(binding.ivInfographic)
        }

    }


}