package com.project.tudoorlock

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
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
    private lateinit var dbHelper: DoorlockDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_change)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        currentIdText = findViewById(R.id.current_id_text)
        newIdInput = findViewById(R.id.new_id_input)
        val confirmButton: Button = findViewById(R.id.confirm_button)
        val backButton: ImageButton = findViewById(R.id.back_button)

        dbHelper = DoorlockDBHelper(this, "doorlockDB", null, 1)
        database = dbHelper.writableDatabase

        loadCurrentId()

        confirmButton.setOnClickListener {
            val newId = newIdInput.text.toString().toInt()

            if (newIdInput.text != null) {

                val oldDoorLockNumber = sharedPreferences.getInt("product_id", 1111)

                val editor = sharedPreferences.edit()
                editor.putInt("product_id", newId)
                editor.apply()

                dbHelper.idUpdate(database, oldDoorLockNumber, newId)

                finishAffinity()
                val intent = packageManager.getLaunchIntentForPackage(packageName)
                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "ID를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadCurrentId() {
        val currentId = sharedPreferences.getInt("product_id", 1111).toString()
        currentIdText.text = "현재 ID: $currentId"
    }
}
