package com.project.tudoorlock

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID


class MainActivity : AppCompatActivity() {

    private lateinit var productIdInput: EditText
    private lateinit var submitButton: Button
    private lateinit var dbHelper: DoorlockDBHelper
    private lateinit var database: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productIdInput = findViewById(R.id.product_id_input)
        submitButton = findViewById(R.id.btn_confirm)



        val preferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        if (preferences.getInt("count", 0) == 0) {

            dbHelper = DoorlockDBHelper(this, "doorlockDB", null, 1)
            database = dbHelper.writableDatabase

            submitButton.setOnClickListener {
                val inputId = productIdInput.text.toString().trim().toInt()

                val queryResult = dbHelper.idSelect(database, inputId)

                Log.d("queryResult", queryResult.toString())


                if (queryResult?.contains("데이터가 없습니다.") == true) {
                    dbHelper.insert(dbHelper.dbReturn(), 1111, 1234)
                    Toast.makeText(this, "임의 값 추가 완료", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                else if (queryResult != null && queryResult.contains(inputId.toString())) {

                    preferences.edit().putInt("product_id", inputId).apply()

                    Toast.makeText(this, "인증 완료!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(this, "잘못된 제품 아이디입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

    }


}


