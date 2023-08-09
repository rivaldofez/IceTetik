package com.icetetik.relaxation

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.icetetik.R
import com.icetetik.databinding.FragmentSourceEmotionBinding


class SourceEmotionFragment : Fragment() {
    private var _binding: FragmentSourceEmotionBinding? = null
    private val binding get() = _binding!!

    private val sourceEmotionList = listOf(
        "Keluarga",
        "Pekerjaan",
        "Percintaan",
        "Kesehatan",
        "Perjalanan",
        "Santai",
        "Cuaca",
        "Belanja",
        "Teman",
        "Tidur",
        "Pendidikan",
        "Diri Sendiri",
        "Seni",
        "Hobi",
        "Keuangan",
        "Ibadah"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSourceEmotionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            val goToOnBoardVideo =
                SourceEmotionFragmentDirections.actionSourceEmotionFragmentToOnboardVideoFragment()
            findNavController().navigate(goToOnBoardVideo)
        }

        sourceEmotionList.forEach {
            binding.cgSourceEmotion.addView(generateChipItem(it, requireContext()))
        }

        for (i in 0 until binding.cgSourceEmotion.childCount) {
            val chip = binding.cgSourceEmotion.getChildAt(i) as Chip
            chip.setOnClickListener {
                if (chip.isCheckable) {
                    chip.chipBackgroundColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                } else {
                    chip.chipBackgroundColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.cream_100
                        )
                    )
                }
                chip.isCheckable = !chip.isCheckable
            }
        }
    }

    private fun generateChipItem(text: String, context: Context): Chip {
        val paddingDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 8f,
            resources.displayMetrics
        ).toInt()

        val chip = Chip(context)
        chip.id = ViewCompat.generateViewId()
        chip.text = text
        chip.setEnsureMinTouchTargetSize(false)
        chip.chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
        chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
        chip.setTextColor(context.getColor(R.color.primaryBackgroundColor))
        chip.setPadding(paddingDp)
        chip.minHeight = 30
        chip.isAllCaps = true
        return chip


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}