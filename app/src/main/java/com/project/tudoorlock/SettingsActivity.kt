package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // 뒤로가기 버튼 초기화 및 클릭 이벤트
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 비밀번호 변경 버튼 초기화 및 클릭 이벤트
        val passwordChangeButton: Button = findViewById(R.id.btn_product_password_change)
        passwordChangeButton.setOnClickListener {
            val intent = Intent(this, PasswordChangeActivity::class.java)
            startActivity(intent)
        }
            // RFID카드 관리 버튼 클릭 시 RFID 화면으로 이동
            val rfidCardButton: Button = findViewById(R.id.btn_rfid_card)
            rfidCardButton.setOnClickListener {
                val intent = Intent(this, RFIDActivity::class.java)
                startActivity(intent)

        }
    }
}
