package com.project.tudoorlock

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var productIdInput: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // 첫 번째 레이아웃 로드

        // 뷰 초기화
        productIdInput = findViewById(R.id.product_id_input)
        submitButton = findViewById(R.id.btn_confirm)

        // SharedPreferences 준비
        val preferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // 확인 버튼 클릭 이벤트
        submitButton.setOnClickListener {
            val inputId = productIdInput.text.toString().trim()

            if (inputId == "666") {
                // 제품 아이디 저장
                preferences.edit().putString("product_id", inputId).apply()

                // 인증 완료 메시지 표시
                Toast.makeText(this, "인증 완료!", Toast.LENGTH_SHORT).show()

                // 메뉴 화면으로 이동
                navigateToMenuLayout()
            } else {
                Toast.makeText(this, "잘못된 제품 아이디입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMenuLayout() {
        // 새 액티비티로 전환
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish() // 현재 액티비티 종료 (선택 사항)
    }
}
