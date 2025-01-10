package com.project.tudoorlock

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class PasswordInputActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbHelper: DoorlockDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_input)

        val prefrences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val passwordInput: EditText = findViewById(R.id.password_input)
        val openDoorButton: Button = findViewById(R.id.btn_open)
        val backButton: ImageButton = findViewById(R.id.back_button)

        dbHelper = DoorlockDBHelper(this, "doorlockDB", null, 1)
        database = dbHelper.writableDatabase

        backButton.setOnClickListener {
            finish()
        }

        openDoorButton.setOnClickListener {
            val password = passwordInput.text.toString()
            if (password == dbHelper.pwdSelect(dbHelper.dbReturn(), prefrences.getInt("product_id", 1111))) {

                Toast.makeText(this, "문이 열렸습니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
