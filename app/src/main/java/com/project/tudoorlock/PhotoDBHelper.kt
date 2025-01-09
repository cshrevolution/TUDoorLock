package com.project.tudoorlock

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class PhotoDBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Photo 테이블 생성
        val sql = "CREATE TABLE IF NOT EXISTS Photo ( " +
                "photoID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + // 기본 키 추가
                "photoSave VARCHAR NOT NULL, " +
                "photoSaveTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 기존 테이블 삭제
        db?.execSQL("DROP TABLE IF EXISTS Photo")
        // 새로운 테이블 생성
        onCreate(db)
    }

    // 데이터 삽입 메서드
    fun insert(db: SQLiteDatabase, photoSave: String) {
        val sql = "INSERT INTO Photo(photoSave) VALUES(?)"
        db.execSQL(sql, arrayOf(photoSave))
        Log.d("PhotoDBHelper", "Inserted: photoSave = $photoSave")
    }

    // 데이터 조회 메서드
    fun select(db: SQLiteDatabase): String? {
        val sql = "SELECT * FROM Photo"
        val result = db.rawQuery(sql, null)

        if (result.count == 0) {
            return "데이터가 없습니다."
        }

        var str = ""
        while (result.moveToNext()) {
            val photoIDIndex = result.getColumnIndex("photoID")
            val photoSaveIndex = result.getColumnIndex("photoSave")
            val photoSaveTimeIndex = result.getColumnIndex("photoSaveTime")

            if (photoIDIndex != -1 && photoSaveIndex != -1 && photoSaveTimeIndex != -1) {
                str += "사진 ID: ${result.getInt(photoIDIndex)}\n" +
                        "사진 저장 경로: ${result.getString(photoSaveIndex)}\n" +
                        "사진 저장 시간: ${result.getString(photoSaveTimeIndex)}\n"
            } else {
                return "컬럼을 찾을 수 없습니다."
            }
        }

        result.close()
        return str
    }

    // 데이터 삭제 메서드
    fun delete(db: SQLiteDatabase, photoID: Int) {
        val sql = "DELETE FROM Photo WHERE photoID = ?"
        db.execSQL(sql, arrayOf(photoID))
        Log.d("PhotoDBHelper", "Deleted: photoID = $photoID")
    }
}
