package com.example.musicplayer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.musicplayerservice.MusicServiceAIDL

class MusicPlayerFragment : Fragment() {
    private var musicService: MusicServiceAIDL? = null

    companion object {
        fun newInstance(): MusicPlayerFragment {
            return MusicPlayerFragment()
        }
    }

    // Method to receive the music service reference
    fun setMusicService(service: MusicServiceAIDL?) {
        Log.d("MusicPlayerFragment", "Setting musicService: $service")
        this.musicService = service
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.music_player_fragment, container,false)
        val playButton = view.findViewById<Button>(R.id.play_button)
        val pauseButton = view.findViewById<Button>(R.id.pause_button)

        playButton.setOnClickListener {
            Log.d("MusicPlayerFragment", "Play Button Clicked")
            if (musicService != null) {
                Log.d("MusicPlayerFragment", "musicService is not null")
                try {
                    musicService?.play()
                    Log.d("MusicPlayerFragment", "Play Method Called on MusicService")
                } catch (e: Exception) {
                    Log.e("MusicPlayerFragment", "Exception calling play method: ${e.message}")
                }
            } else {
                Log.d("MusicPlayerFragment", "MusicService is null")
            }
        }


            pauseButton.setOnClickListener {
            Log.d("MusicPlayerFragment", "Pause button clicked")
            musicService?.pause()
        }

        Log.d("MusicPlayerFragment", "Fragment view created")
        return view
    }
}