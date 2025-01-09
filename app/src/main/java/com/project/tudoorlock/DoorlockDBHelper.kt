package com.project.tudoorlock

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DoorlockDBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        // doorlock 테이블 생성
        val sql = "CREATE TABLE IF NOT EXISTS doorlock ( " +
                "doorlockNumber INTEGER PRIMARY KEY NOT NULL, " +
                "doorlockPassword INTEGER NOT NULL)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 기존 테이블 삭제
        db?.execSQL("DROP TABLE IF EXISTS doorlock")
        // 새로운 테이블 생성
        onCreate(db)
    }

    // 데이터 삽입 메서드
    fun insert(db: SQLiteDatabase, doorlockNumber: Int, doorlockPassword: Int) {
        val sql = "INSERT INTO doorlock(doorlockNumber, doorlockPassword) VALUES(?, ?)"
        db.execSQL(sql, arrayOf(doorlockNumber, doorlockPassword))
        Log.d("DBHelper", "Inserted: doorlockNumber = $doorlockNumber, doorlockPassword = $doorlockPassword")
    }

    // 데이터 조회 메서드
    fun select(db: SQLiteDatabase, doorlockNumber: Int): String? {
        val sql = "SELECT * FROM doorlock WHERE doorlockNumber = ?"
        val result = db.rawQuery(sql, arrayOf(doorlockNumber.toString()))

        if (result.count == 0) {
            return "데이터가 없습니다."
        }

        var str = ""
        while (result.moveToNext()) {
            val doorlockNumberIndex = result.getColumnIndex("doorlockNumber")
            val doorlockPasswordIndex = result.getColumnIndex("doorlockPassword")

            if (doorlockNumberIndex != -1 && doorlockPasswordIndex != -1) {
                str += "문 번호: ${result.getString(doorlockNumberIndex)}\n" +
                        "비밀번호: ${result.getInt(doorlockPasswordIndex)}\n"
            } else {
                return "컬럼을 찾을 수 없습니다."
            }
        }

        result.close()
        return str
    }

    // 데이터 삭제 메서드
    fun delete(db: SQLiteDatabase, doorlockNumber: Int) {
        val sql = "DELETE FROM doorlock WHERE doorlockNumber = ?"
        db.execSQL(sql, arrayOf(doorlockNumber))
        Log.d("DBHelper", "Deleted: doorlockNumber = $doorlockNumber")
    }
}
