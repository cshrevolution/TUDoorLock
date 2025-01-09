package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PasswordInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_input)

        val passwordInput: EditText = findViewById(R.id.password_input)
        val btnOpenDoor: Button = findViewById(R.id.btn_open_door)

        btnOpenDoor.setOnClickListener {
            val enteredPassword = passwordInput.text.toString()

            if (enteredPassword == "555") {
                val intent = Intent()
                intent.putExtra("password", enteredPassword)
                setResult(RESULT_OK, intent)
                finish() // PasswordInputActivity 종료
            } else {
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
