package com.icetetik.page.video

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.R
import com.icetetik.data.model.Video
import com.icetetik.databinding.ActivityVideoPlayerBinding
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.KeyParcelable
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

        val video = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KeyParcelable.VIDEO_DATA, Video::class.java)
        } else {
            intent.getParcelableExtra(KeyParcelable.VIDEO_DATA)
        }

        if (video == null){
            binding.showSnackBar(getString(R.string.error_video_null))
        } else {
            youTubePlayer.loadVideo(video.id, 0f)
        }


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