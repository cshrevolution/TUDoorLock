package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var ivLock: ImageButton
    private lateinit var tvLockText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu) // menu.xml 로드

        ivLock = findViewById(R.id.iv_lock)
        tvLockText = findViewById(R.id.tv_lock_text)

        // 초기 상태는 ic_lock
        ivLock.setImageResource(R.drawable.ic_lock)
        tvLockText.text = "문 잠금 해제하기" // 초기 텍스트

        // 잠금 아이콘 클릭 이벤트
        ivLock.setOnClickListener {
            // 현재 상태에 따라 동작 처리
            if (ivLock.drawable.constantState == getDrawable(R.drawable.ic_unlock)?.constantState) {
                // ic_unlock 상태일 경우
                ivLock.setImageResource(R.drawable.ic_lock) // ic_lock으로 변경
                tvLockText.text = "문 잠금 해제하기" // 텍스트 변경
                // SharedPreferences에서 잠금 상태 저장
                val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isUnlocked", false)
                editor.apply()
            } else {
                // PasswordInputActivity로 이동
                val intent = Intent(this, PasswordInputActivity::class.java)
                startActivityForResult(intent, 1) // 결과를 받을 준비
            }
        }

        // 설정 버튼 클릭 이벤트
        val btnSettings: ImageButton = findViewById(R.id.btn_settings)
        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // 알림 버튼 클릭 이벤트
        val btnNotification: ImageButton = findViewById(R.id.btn_notification)
        btnNotification.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        // 실시간 카메라 버튼 클릭 이벤트
        val cameraButton: Button = findViewById(R.id.camera_button)
        cameraButton.setOnClickListener {
            val intent = Intent(this, LiveCameraActivity::class.java)
            startActivity(intent)
        }
    }

    // PasswordInputActivity에서 돌아올 때 호출되는 메서드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val isUnlocked = data?.getBooleanExtra("isUnlocked", false) ?: false
            if (isUnlocked) {
                ivLock.setImageResource(R.drawable.ic_unlock) // 잠금 해제 아이콘으로 변경
                tvLockText.text = "문 잠금 해제됨" // 텍스트 변경
            }
        }
    }
}
