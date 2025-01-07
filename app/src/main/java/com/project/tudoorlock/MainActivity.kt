package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // 메인 XML 연결

        // "비밀번호 입력" 버튼
        val passwordInputButton = findViewById<Button>(R.id.btn_password_input)
        passwordInputButton.setOnClickListener {
            // PasswordInputActivity로 전환
            val intent = Intent(this, PasswordInputActivity::class.java)
            startActivity(intent)
            val backButton = findViewById<ImageButton>(R.id.btn_back)
            backButton.setOnClickListener {
                // MainActivity로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // 현재 Activity 종료
                finish()
            }
        }
    }
}
