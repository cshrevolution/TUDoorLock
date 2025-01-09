package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class LiveCameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_camera) // activity_live_camera.xml 로드

        // 뒤로 가기 버튼 클릭 이벤트 설정
        val btnBack: ImageButton = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            // MenuActivity로 돌아가기
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish() // 현재 Activity 종료
        }
    }
}
