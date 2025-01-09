package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Lock 버튼과 Unlock 버튼 참조
        val lockButton: ImageButton = findViewById(R.id.iv_lock)
        val unlockButton: ImageButton = findViewById(R.id.iv_unlock)

        // intent로부터 잠금 해제 상태 확인
        val isUnlocked = intent.getBooleanExtra("isUnlocked", false)
        if (isUnlocked) {
            // 잠금 해제 상태라면 Unlock 버튼 표시
            lockButton.visibility = View.GONE
            unlockButton.visibility = View.VISIBLE
        }

        // Lock 버튼 클릭 이벤트
        lockButton.setOnClickListener {
            // Lock 버튼 숨기고 Unlock 버튼 표시
            lockButton.visibility = View.GONE
            unlockButton.visibility = View.VISIBLE
        }

        // Unlock 버튼 클릭 이벤트
        unlockButton.setOnClickListener {
            // Unlock 버튼 숨기고 Lock 버튼 표시
            unlockButton.visibility = View.GONE
            lockButton.visibility = View.VISIBLE
        }

        // Lock 버튼에서 PasswordInputActivity로 이동
        lockButton.setOnClickListener {
            val intent = Intent(this, PasswordInputActivity::class.java)
            startActivity(intent)
        }
    }
}
