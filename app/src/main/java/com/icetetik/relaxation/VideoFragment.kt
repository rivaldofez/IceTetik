package com.icetetik.relaxation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.icetetik.databinding.FragmentVideoBinding
import com.icetetik.page.mainmood.MoodActivity

class VideoFragment : Fragment() {
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHome.setOnClickListener {
            val intent = Intent(requireActivity(), MoodActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()
        }

        binding.ivStartVideo.setOnClickListener {
            val gotoRelaxationPlayer = VideoFragmentDirections.actionVideoFragmentToRelaxationPlayerFragment()
            findNavController().navigate(gotoRelaxationPlayer)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}