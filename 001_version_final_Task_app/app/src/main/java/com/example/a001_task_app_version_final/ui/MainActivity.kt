package com.example.a001_task_app_version_final.ui

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.VideoView
import com.example.a001_task_app_version_final.R

class MainActivity : AppCompatActivity() {

    private lateinit var mVideoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mVideoView = findViewById(R.id.videoView)

        val videoUri: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.intro)

        mVideoView.setOnCompletionListener(MediaPlayer.OnCompletionListener {

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        })

        mVideoView.setVideoURI(videoUri)
        mVideoView.start()

    }
}