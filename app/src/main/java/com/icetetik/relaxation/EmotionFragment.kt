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
import com.icetetik.databinding.FragmentEmotionBinding

class EmotionFragment : Fragment() {
    private var _binding: FragmentEmotionBinding? = null
    private val binding get() = _binding!!

    private val positiveEmotionTitleList = listOf(
        "Antusias",
        "Gembira",
        "Takjub",
        "Semangat",
        "Bangga",
        "Penuh Cinta",
        "Santai",
        "Tenang",
        "Puas",
        "Lega",
        "Senang"
    )

    private val negativeEmotionTitleList = listOf(
        "Marah",
        "Takut",
        "Stres",
        "Waspada",
        "Kewalahan",
        "Patah Hati",
        "Bingung",
        "Kesal",
        "Malu",
        "Cemas",
        "Lesu",
        "Sedih",
        "Duka",
        "Bosan",
        "Apatis",
        "Kesepian",
        "Gelisah",
        "Murung",
        "Kecewa",
        "Hiperaktif"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmotionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            val goToSourceEmotion =
                EmotionFragmentDirections.actionEmotionFragmentToSourceEmotionFragment()
            findNavController().navigate(goToSourceEmotion)
        }


        positiveEmotionTitleList.forEach {
            binding.cgPositiveEmotion.addView(generateChipItem(it, requireContext()))
        }

        negativeEmotionTitleList.forEach {
            binding.cgNegativeEmotion.addView(generateChipItem(it, requireContext()))
        }

        for (i in 0 until binding.cgPositiveEmotion.childCount) {
            val chip = binding.cgPositiveEmotion.getChildAt(i) as Chip
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

        for (i in 0 until binding.cgNegativeEmotion.childCount) {
            val chip = binding.cgNegativeEmotion.getChildAt(i) as Chip
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
        chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
        chip.setTextColor(context.getColor(R.color.primaryBackgroundColor))
        chip.chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
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