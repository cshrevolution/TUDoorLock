package com.project.tudoorlock

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var productIdInput: EditText
    private lateinit var submitButton: Button
    private lateinit var dbHelper: DoorlockDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 뷰 초기화
        productIdInput = findViewById(R.id.product_id_input)
        submitButton = findViewById(R.id.btn_confirm)

        // SharedPreferences 준비
        val preferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // 데이터베이스 초기화
        dbHelper = DoorlockDBHelper(this, "doorlockDB", null, 1)
        database = dbHelper.writableDatabase

        // 기본 제품 ID 설정
        val defaultId = "0000"

        // 확인 버튼 클릭 이벤트
        submitButton.setOnClickListener {
            val inputId = productIdInput.text.toString().trim()

            // 입력된 ID가 비어있으면 기본값으로 설정
            val finalId = if (inputId.isEmpty()) defaultId else inputId

            // ID를 데이터베이스에서 확인
            val queryResult = dbHelper.select(database, finalId.toIntOrNull() ?: -1)

            if (queryResult != null && !queryResult.contains("데이터가 없습니다.")) {
                // 제품 아이디 저장
                preferences.edit().putString("product_id", finalId).apply()

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
