package com.project.tudoorlock

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class LiveCameraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_camara)

        // Back 버튼 참조
        val backButton: ImageButton = findViewById(R.id.btn_back)

        // 클릭 리스너 설정
        backButton.setOnClickListener {
            // 이전 Activity로 돌아가기
            finish()
        }
    }
}