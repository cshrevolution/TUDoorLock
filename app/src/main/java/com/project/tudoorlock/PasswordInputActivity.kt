package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class PasswordInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_input) // activity_password_input.xml 로드

        val passwordInput: EditText = findViewById(R.id.password_input)
        val openDoorButton: Button = findViewById(R.id.btn_open)
        val backButton: ImageButton = findViewById(R.id.back_button)

        // 뒤로 가기 버튼 클릭 이벤트
        backButton.setOnClickListener {
            finish() // 현재 Activity 종료
        }

        // 문 열기 버튼 클릭 이벤트
        openDoorButton.setOnClickListener {
            val password = passwordInput.text.toString()
            if (password == "555") {
                // 비밀번호가 맞을 경우 결과를 설정
                val intent = Intent()
                intent.putExtra("isUnlocked", true) // 잠금 해제 상태 전달
                setResult(RESULT_OK, intent) // 결과 설정
                finish() // 현재 Activity 종료
            } else {
                // 비밀번호가 틀릴 경우 사용자에게 알림
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
