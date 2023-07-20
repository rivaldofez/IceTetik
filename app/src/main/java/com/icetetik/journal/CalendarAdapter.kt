package com.icetetik.journal

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icetetik.R
import com.icetetik.databinding.ItemCalendarBinding
import java.time.LocalDate

class CalendarAdapter(val context: Context, val callback: CalendarItemCallback): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    private val daysOfMonth: ArrayList<String> = ArrayList()
    private var currentAdapterDate: LocalDate = LocalDate.now()
    private val currentDate = LocalDate.now()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val layoutparams = binding.root.layoutParams
//        layoutparams.height = (parent.height * 0.07).toInt()
        layoutparams.height = parent.width / 7
        return CalendarViewHolder(binding)
    }

    fun calculateSizeOfView(context: Context): Int {

        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels
        return (dpWidth / 7) // COLUMN_COUNT would be 4 in your case
    }

    fun setData(data: List<String>, currentAdapterDate: LocalDate){
        daysOfMonth.clear()
        daysOfMonth.addAll(data)
        this.currentAdapterDate = currentAdapterDate
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(daysOfMonth[position], position)
    }

    override fun getItemCount(): Int = daysOfMonth.size

    inner class CalendarViewHolder(private val binding: ItemCalendarBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(dayOfMonth: String, position: Int){
            if(position % 7 == 0){
                binding.tvItemDay.setTypeface(null, Typeface.BOLD)
            }

            if(
                dayOfMonth == currentDate.dayOfWeek.value.toString() &&
                        currentAdapterDate.year == currentDate.year &&
                        currentAdapterDate.month.value == currentDate.month.value
            ){
                binding.clItemDay.setBackgroundColor(context.getColor(R.color.black))
            }


            binding.tvItemDay.text = dayOfMonth

            binding.root.setOnClickListener {
                callback.onItemCalendarClicked(dayOfMonth)
            }
        }
    }


}