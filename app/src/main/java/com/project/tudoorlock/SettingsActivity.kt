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

    private lateinit var switch1: Switch
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        switch1 = findViewById(R.id.switch1)

        val isSwitchChecked = sharedPreferences.getBoolean("switch1_state", false)
        switch1.isChecked = isSwitchChecked

        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }

        val passwordChangeButton: Button = findViewById(R.id.btn_product_password_change)
        passwordChangeButton.setOnClickListener {
            val intent = Intent(this, PasswordChangeActivity::class.java)
            startActivity(intent)
        }

        val rfidCardButton: Button = findViewById(R.id.btn_rfid_card)
        rfidCardButton.setOnClickListener {
            val intent = Intent(this, RFIDActivity::class.java)
            startActivity(intent)
        }

        switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val intent = Intent(this, PasswordActivity::class.java)
                startActivityForResult(intent, REQUEST_PASSWORD)
            } else {
                saveSwitchState(isChecked)
            }
        }
    }

    companion object {
        const val REQUEST_PASSWORD = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PASSWORD) {
            if (resultCode == RESULT_OK) {
                switch1.isChecked = true
                saveSwitchState(true)
            } else {
                switch1.isChecked = false
                saveSwitchState(false)
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveSwitchState(isChecked: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("switch1_state", isChecked)
        editor.apply()
    }
}
