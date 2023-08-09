package com.icetetik.relaxation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.icetetik.R
import com.icetetik.databinding.FragmentRelaxationPlayerBinding


class RelaxationPlayerFragment : Fragment() {
    private var _binding: FragmentRelaxationPlayerBinding? = null
    private val binding get() = _binding!!
    private var exoPlayer: ExoPlayer? = null
    private var playBackPosition = 0L
    private var playWhenReady = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRelaxationPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPlayer()
        setupListener()

    }

    private fun setupListener() {
        exoPlayer?.addListener(
            object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)

                    if (playbackState == ExoPlayer.STATE_ENDED) {
                        val gotoClosing =
                            RelaxationPlayerFragmentDirections.actionRelaxationPlayerFragmentToClosingVideoFragment()
                        findNavController().navigate(gotoClosing)
                    }
                }
            }
        )
    }

    private fun setupPlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        exoPlayer?.playWhenReady = true
        binding.videoPlayer.player = exoPlayer

        val uri = RawResourceDataSource.buildRawResourceUri(R.raw.relaxation)
        val mediaItem = MediaItem.fromUri(uri)

        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.seekTo(playBackPosition)
        exoPlayer?.playWhenReady = playWhenReady
        exoPlayer?.prepare()
    }

    private fun releasePlayer() {
        exoPlayer?.let {
            playBackPosition = it.currentPosition
            playWhenReady = playWhenReady
            it.release()
            exoPlayer = null
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}