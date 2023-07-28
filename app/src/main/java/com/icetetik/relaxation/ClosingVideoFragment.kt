package com.icetetik.relaxation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icetetik.R
import com.icetetik.databinding.FragmentClosingVideoBinding

class ClosingVideoFragment : Fragment() {

    private var _binding: FragmentClosingVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClosingVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnBackMainMenu.setOnClickListener {
            requireActivity().finish()
        }


    }

}