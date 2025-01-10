package com.project.tudoorlock

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PasswordChangeActivity : AppCompatActivity() {

    private lateinit var dbHelper: DoorlockDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_change)

        dbHelper = DoorlockDBHelper(this, "doorlockDB", null, 1)
        database = dbHelper.writableDatabase

        val password_input = findViewById<EditText>(R.id.password_input)
        val new_password_input = findViewById<EditText>(R.id.new_password_input)
        val confirm_new_password_input = findViewById<EditText>(R.id.confirm_new_password_input)
        val confirm_button = findViewById<Button>(R.id.confirm_button)
        val prefrences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        confirm_button.setOnClickListener {
            val password = password_input.text.toString()
            val newPassword = new_password_input.text.toString()
            val confirmNewPassword = confirm_new_password_input.text.toString()

            if (password == dbHelper.pwdSelect(dbHelper.dbReturn(), prefrences.getInt("product_id", 1111))) {
                if(confirmNewPassword == newPassword){
                    dbHelper.pwdUpdate(dbHelper.dbReturn(), prefrences.getInt("product_id", 1111), newPassword.toInt())
                    Toast.makeText(this, "비밀번호 변경 완료.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }

        }


    }
}
