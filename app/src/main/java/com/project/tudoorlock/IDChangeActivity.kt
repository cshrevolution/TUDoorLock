package com.project.tudoorlock

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class IDChangeActivity : AppCompatActivity() {

    private lateinit var currentIdText: TextView
    private lateinit var newIdInput: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_change)

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // UI 요소 초기화
        currentIdText = findViewById(R.id.current_id_text)
        newIdInput = findViewById(R.id.new_id_input)
        val confirmButton: Button = findViewById(R.id.confirm_button)
        val backButton: ImageButton = findViewById(R.id.back_button)

        // 현재 ID 로드
        loadCurrentId()

        // 확인 버튼 클릭 이벤트
        confirmButton.setOnClickListener {
            val newId = newIdInput.text.toString()

            if (newId.isNotEmpty()) {
                // ID가 비어 있지 않을 경우
                val editor = sharedPreferences.edit()
                editor.putString("currentId", newId) // 새로운 ID 저장
                editor.apply()

                // 앱 종료 후 재시작
                finishAffinity() // 모든 Activity 종료
                val intent = packageManager.getLaunchIntentForPackage(packageName)
                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent) // 앱 재시작
            } else {
                // ID가 비어 있을 경우 사용자에게 알림
                Toast.makeText(this, "ID를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 뒤로가기 버튼 클릭 이벤트
        backButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // 현재 ID 로드
    private fun loadCurrentId() {
        val currentId = sharedPreferences.getString("currentId", "666") // 기본값: 666
        currentIdText.text = "현재 ID: $currentId"
    }
}
