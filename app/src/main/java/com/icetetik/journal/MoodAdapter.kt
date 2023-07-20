package com.icetetik.journal

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.icetetik.R
import com.icetetik.data.model.MoodItemView
import com.icetetik.databinding.ItemMoodBinding

class MoodAdapter(val context: Context): RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {
    private val listMoodItemView: ArrayList<MoodItemView> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val binding = ItemMoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoodViewHolder(binding)
    }

    override fun getItemCount(): Int = listMoodItemView.size

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        holder.bind(listMoodItemView[position])
    }

    fun setData(data: List<MoodItemView>){
        listMoodItemView.clear()
        listMoodItemView.addAll(data)
        notifyDataSetChanged()
    }


    inner class MoodViewHolder(private val binding: ItemMoodBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(moodItemView: MoodItemView){
            binding.apply {
                tvMood.text = moodItemView.condition
                ivMood.setImageResource(moodItemView.image)
            }
        }
    }

}