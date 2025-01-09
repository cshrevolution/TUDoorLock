package com.project.tudoorlock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PasswordActivity : AppCompatActivity() {

    private lateinit var passwordInput: EditText
    private lateinit var btnOpenDoor: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        passwordInput = findViewById(R.id.password_input)
        btnOpenDoor = findViewById(R.id.btn_open_door)

        btnOpenDoor.setOnClickListener {
            if (passwordInput.text.toString() == "555") {
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
