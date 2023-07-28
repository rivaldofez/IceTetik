package com.icetetik.relaxation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icetetik.R
import com.icetetik.databinding.FragmentOnboardRelaxationBinding

class OnboardRelaxationFragment : Fragment() {
    private var _binding: FragmentOnboardRelaxationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardRelaxationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnNext.setOnClickListener {
            val goToEmotionFragment = OnboardRelaxationFragmentDirections.actionOnboardRelaxationFragmentToEmotionFragment()
            findNavController().navigate(goToEmotionFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}