package com.project.tudoorlock

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var productIdInput: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 뷰 초기화
        productIdInput = findViewById(R.id.product_id_input)
        submitButton = findViewById(R.id.submit_button)

        // SharedPreferences에서 제품 아이디 가져오기
        val preferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedProductId = preferences.getString("product_id", "")

        // 초기 상태 설정
        if (savedProductId == "666") {
            // 제품 아이디가 "666"인 경우
            Toast.makeText(this, "이미 인증된 사용자입니다.", Toast.LENGTH_SHORT).show()
        }

        // 확인 버튼 클릭 이벤트
        submitButton.setOnClickListener {
            val inputId = productIdInput.text.toString().trim()

            if (inputId == "666") {
                // 제품 아이디 저장
                preferences.edit().putString("product_id", inputId).apply()
                Toast.makeText(this, "인증 완료!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "잘못된 제품 아이디입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
