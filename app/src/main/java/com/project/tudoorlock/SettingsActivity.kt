package com.project.tudoorlock

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val prefrences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = prefrences.edit()

        // 뒤로가기 버튼 초기화 및 클릭 이벤트
        val backButton: ImageButton = findViewById(R.id.back_button)
        val passwordChangeButton: Button = findViewById(R.id.btn_product_password_change)
        val rfidCardButton: Button = findViewById(R.id.btn_rfid_card)
        val productIdChangeButton: Button = findViewById(R.id.btn_product_id_change) // 제품 ID 변경 버튼 초기화
        val switch1: Switch = findViewById(R.id.switch1)

        switch1.isChecked = prefrences.getBoolean("switchStatus", false)

        switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editor.putBoolean("switchStatus", true)
                editor.apply()
            }
            else {
                editor.putBoolean("switchStatus", false)
                editor.apply()
            }
        }

        // RFID 카드 관리 버튼 클릭 시 RFID 화면으로 이동
        rfidCardButton.setOnClickListener {
            Toast.makeText(this@SettingsActivity, "RFID", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SettingsActivity, RFIDActivity::class.java))
            finish()
        }

        // 뒤로가기 버튼 클릭 이벤트
        backButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 비밀번호 변경 버튼 클릭 이벤트
        passwordChangeButton.setOnClickListener {
            val intent = Intent(this@SettingsActivity, PasswordChangeActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 제품 ID 변경 버튼 클릭 이벤트
        productIdChangeButton.setOnClickListener {
            val intent = Intent(this, IDChangeActivity::class.java) // IDChangeActivity로 이동
            startActivity(intent)
        }
    }
}
