package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private var isUnlocked: Boolean = false // 문 잠금 상태를 관리

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // 잠금 및 잠금 해제 아이콘
        val lockButton: ImageButton = findViewById(R.id.iv_lock)
        val unlockButton: ImageButton = findViewById(R.id.iv_unlock)
        val tvLockText: TextView = findViewById(R.id.tv_lock_text)
        val tvUnlockText: TextView = findViewById(R.id.tv_unlock_text)

        // ic_bell 및 ic_settings 버튼
        val notificationButton: ImageButton = findViewById(R.id.btn_notification)
        val settingsButton: ImageButton = findViewById(R.id.btn_settings)

        // 초기 상태 설정
        updateLockState(lockButton, unlockButton, tvLockText, tvUnlockText)

        // ic_lock 클릭 시 PasswordInputActivity로 이동
        lockButton.setOnClickListener {
            val intent = Intent(this, PasswordInputActivity::class.java)
            startActivityForResult(intent, REQUEST_PASSWORD) // 비밀번호 입력 결과를 받기 위해 startActivityForResult 사용
        }

        // ic_unlock 클릭 시 바로 잠금 상태로 전환
        unlockButton.setOnClickListener {
            isUnlocked = false
            updateLockState(lockButton, unlockButton, tvLockText, tvUnlockText)
            Toast.makeText(this, "문이 잠겼습니다.", Toast.LENGTH_SHORT).show()
        }

        // ic_bell 클릭 시 NotificationActivity로 이동
        notificationButton.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        // ic_settings 클릭 시 SettingsActivity로 이동
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateLockState(lockButton: ImageButton, unlockButton: ImageButton, tvLockText: TextView, tvUnlockText: TextView) {
        if (isUnlocked) {
            // 잠금 해제 상태
            lockButton.visibility = ImageButton.GONE
            unlockButton.visibility = ImageButton.VISIBLE
            tvLockText.visibility = TextView.GONE
            tvUnlockText.visibility = TextView.VISIBLE
        } else {
            // 잠금 상태
            lockButton.visibility = ImageButton.VISIBLE
            unlockButton.visibility = ImageButton.GONE
            tvLockText.visibility = TextView.VISIBLE
            tvUnlockText.visibility = TextView.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PASSWORD && resultCode == RESULT_OK) {
            val password = data?.getStringExtra("password")
            if (password == "555") {
                isUnlocked = true
                val lockButton: ImageButton = findViewById(R.id.iv_lock)
                val unlockButton: ImageButton = findViewById(R.id.iv_unlock)
                val tvLockText: TextView = findViewById(R.id.tv_lock_text)
                val tvUnlockText: TextView = findViewById(R.id.tv_unlock_text)
                updateLockState(lockButton, unlockButton, tvLockText, tvUnlockText)
                Toast.makeText(this, "문이 열렸습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val REQUEST_PASSWORD = 100
    }
}
