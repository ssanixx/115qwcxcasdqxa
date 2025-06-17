package com.example.playerdas

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider
import com.google.android.exoplayer2.drm.MediaDrmCallback
import com.google.android.exoplayer2.drm.LocalMediaDrmCallback
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerView = PlayerView(this)
        setContentView(playerView)

        initializePlayer()
    }

    private fun initializePlayer() {
        val drmSchemeUuid = Util.getDrmUuid("clearkey")!!

        val keyRequestJson = """
            {
              "keys":[
                {
                  "k":"M9KsZETKWDrOWsjdXDFtfA",
                  "kid":"LmeZ3wemXPVSq562kzK-pA",
                  "kty":"oct"
                }
              ],
              "type":"temporary"
            }
        """.trimIndent()

        val drmCallback: MediaDrmCallback = LocalMediaDrmCallback(keyRequestJson.toByteArray())

        val drmSessionManagerProvider = DefaultDrmSessionManagerProvider().apply {
            setDrmHttpDataSourceFactory(null)
            setDrmCallback(drmCallback)
        }

        val mediaItem = MediaItem.Builder()
            .setUri(Uri.parse("https://player.cdn.tv/siete/vmx/index.mpd"))
            .setDrmUuid(drmSchemeUuid)
            .setDrmSessionForClearTypes(true)
            .build()

        player = ExoPlayer.Builder(this)
            .setDrmSessionManagerProvider(drmSessionManagerProvider)
            .build()

        playerView.player = player
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }
}
