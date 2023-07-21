package com.icetetik.journal

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icetetik.R
import com.icetetik.databinding.ItemCalendarBinding
import java.time.LocalDate

class CalendarAdapter(val context: Context, val callback: CalendarItemCallback) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    private val daysOfMonth: ArrayList<String> = ArrayList()
    private var currentAdapterDate: LocalDate = LocalDate.now()
    private val currentDate = LocalDate.now()
    private var selectedDate = LocalDate.now()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding =
            ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val layoutparams = binding.root.layoutParams
//        layoutparams.height = (parent.height * 0.07).toInt()
        layoutparams.height = parent.width / 7
        return CalendarViewHolder(binding)
    }

    fun setData(data: List<String>, currentAdapterDate: LocalDate, selectedDate: LocalDate) {
        daysOfMonth.clear()
        daysOfMonth.addAll(data)
        this.currentAdapterDate = currentAdapterDate
        this.selectedDate = selectedDate
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(daysOfMonth[position], position)
    }

    override fun getItemCount(): Int = daysOfMonth.size

    inner class CalendarViewHolder(private val binding: ItemCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dayOfMonth: String, position: Int) {
            binding.apply {
                if (position % 7 == 0) {
                    tvItemDay.setTypeface(null, Typeface.BOLD)
                }

                if (
                    dayOfMonth == currentDate.dayOfMonth.toString() &&
                    currentAdapterDate.year == currentDate.year &&
                    currentAdapterDate.month.value == currentDate.month.value
                ) {
                    clItemDay.setBackgroundColor(context.getColor(R.color.green_50))
                    tvItemDay.setTextColor(context.getColor(R.color.white))
                    tvItemDay.setTypeface(null, Typeface.BOLD)
                }

                if (
                    dayOfMonth == selectedDate.dayOfMonth.toString() &&
                    currentAdapterDate.year == selectedDate.year &&
                    currentAdapterDate.month.value == selectedDate.month.value
                ) {
                    clItemDay.setBackgroundColor(context.getColor(R.color.primaryBackgroundColor))
                    tvItemDay.setTextColor(context.getColor(R.color.white))
                    tvItemDay.setTypeface(null, Typeface.BOLD)
                }

                tvItemDay.text = dayOfMonth
                if (dayOfMonth.isNotEmpty()) {
                    root.setOnClickListener {
                        callback.onItemCalendarClicked(dayOfMonth)
                    }
                }
            }
        }
    }


}