package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu) // menu.xml 로드

        // 설정 버튼 클릭 이벤트
        val btnSettings: ImageButton = findViewById(R.id.btn_settings)
        btnSettings.setOnClickListener {
            // SettingsActivity로 이동
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // 알림 버튼 클릭 이벤트
        val btnNotification: ImageButton = findViewById(R.id.btn_notification)
        btnNotification.setOnClickListener {
            // NotificationActivity로 이동
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        // 실시간 카메라 버튼 클릭 이벤트 추가
        val registerCardButton: Button = findViewById(R.id.camera_button) // 버튼 참조
        registerCardButton.setOnClickListener {
            // LiveCameraActivity로 이동
            val intent = Intent(this, LiveCameraActivity::class.java)
            startActivity(intent)
        }
    }
}
