package com.io.testappvtb.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.io.testappvtb.databinding.FragmentFirstBinding
import com.io.testappvtb.gone
import com.io.testappvtb.parseExoError


class FirstFragment(private val url: String) : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding: FragmentFirstBinding
        get() = _binding!!

    private lateinit var player: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        setVideo()
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.player.setOnClickListener {
            player.play()
        }
    }

    private fun setVideo() {
        player = ExoPlayer.Builder(requireContext()).build()
        binding.player.player = player
        val mediaItem: MediaItem =
            MediaItem.fromUri(Uri.parse(url))
        player.setMediaItem(mediaItem)

        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)

                player.stop()
                binding.player.gone()
                binding.tvVideo.text = parseExoError(error.errorCode)

            }
        })

        player.prepare()
    }

    override fun onStop() {
        super.onStop()
        player.stop()
    }


}