package com.icetetik.page.video

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.R
import com.icetetik.databinding.ActivityVideoPlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

class VideoPlayerActivity : AppCompatActivity(), YouTubePlayerListener {
    private lateinit var binding: ActivityVideoPlayerBinding
    private var youtubePlayer: YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        lifecycle.addObserver(binding.playerYoutube)
        binding.playerYoutube.addYouTubePlayerListener(this)
    }

    override fun onApiChange(youTubePlayer: YouTubePlayer) {

    }

    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

    }

    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {

    }

    override fun onPlaybackQualityChange(
        youTubePlayer: YouTubePlayer,
        playbackQuality: PlayerConstants.PlaybackQuality
    ) {

    }

    override fun onPlaybackRateChange(
        youTubePlayer: YouTubePlayer,
        playbackRate: PlayerConstants.PlaybackRate
    ) {

    }

    override fun onReady(youTubePlayer: YouTubePlayer) {
        youtubePlayer = youTubePlayer
        youTubePlayer.loadVideo("-CmadmM5cOk", 0f)
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {

    }

    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {

    }

    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {

    }

    override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {

    }
}