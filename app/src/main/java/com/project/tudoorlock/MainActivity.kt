package com.project.tudoorlock

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var productIdInput: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productIdInput = findViewById(R.id.product_id_input)
        submitButton = findViewById(R.id.submit_button)

        submitButton.setOnClickListener {
            val input = productIdInput.text.toString().trim()

            if (input == "666") {

                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            } else {

                Toast.makeText(this, "잘못된 제품 아이디입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

