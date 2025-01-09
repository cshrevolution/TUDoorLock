package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification) // activity_notification.xml 로드

        val registerCardButton: Button = findViewById(R.id.register_card_button)

        // 버튼 클릭 이벤트 설정
        registerCardButton.setOnClickListener {
            // LiveCameraActivity로 이동
            val intent = Intent(this, LiveCameraActivity::class.java)
            startActivity(intent)
        }
        // 뒤로가기 버튼 클릭 시 설정 화면으로 이동
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
