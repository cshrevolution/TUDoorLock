package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class LiveCameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_camera)

        val btnBack: ImageButton = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {

            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        val cameraStreamingView = findViewById<WebView>(R.id.cameraStreamingView)
        cameraStreamingView.webViewClient = WebViewClient()
        cameraStreamingView.settings.loadWithOverviewMode = true
        cameraStreamingView.settings.useWideViewPort = true
        cameraStreamingView.loadUrl("192.168.0.21:8000")
    }
}
