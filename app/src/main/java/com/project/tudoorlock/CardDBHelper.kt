package com.project.tudoorlock

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class CardDBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        // card 테이블 생성
        val sql = "CREATE TABLE IF NOT EXISTS card ( " +
                "cardNumber INTEGER PRIMARY KEY NOT NULL, " +
                "cardName VARCHAR NOT NULL)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 기존 테이블 삭제
        db?.execSQL("DROP TABLE IF EXISTS card")
        // 새로운 테이블 생성
        onCreate(db)
    }

    // 데이터 삽입 메서드
    fun insert(db: SQLiteDatabase, cardNumber: Int, cardName: String) {
        val sql = "INSERT INTO card(cardNumber, cardName) VALUES(?, ?)"
        db.execSQL(sql, arrayOf(cardNumber, cardName))
        Log.d("DBHelper", "Inserted: cardNumber = $cardNumber, cardName = $cardName")
    }

    // 데이터 조회 메서드
    fun select(db: SQLiteDatabase, cardNumber: Int): String? {
        val sql = "SELECT * FROM card WHERE cardNumber = ?"
        val result = db.rawQuery(sql, arrayOf(cardNumber.toString()))

        if (result.count == 0) {
            return "데이터가 없습니다."
        }

        var str = ""
        while (result.moveToNext()) {
            val cardNumberIndex = result.getColumnIndex("cardNumber")
            val cardNameIndex = result.getColumnIndex("cardName")

            if (cardNumberIndex != -1 && cardNameIndex != -1) {
                str += "카드 번호: ${result.getString(cardNumberIndex)}\n" +
                        "카드 이름: ${result.getString(cardNameIndex)}\n"
            } else {
                return "컬럼을 찾을 수 없습니다."
            }
        }

        result.close()
        return str
    }

    // 데이터 삭제 메서드
    fun delete(db: SQLiteDatabase, cardNumber: Int) {
        val sql = "DELETE FROM card WHERE cardNumber = ?"
        db.execSQL(sql, arrayOf(cardNumber))
        Log.d("DBHelper", "Deleted: cardNumber = $cardNumber")
    }
}
