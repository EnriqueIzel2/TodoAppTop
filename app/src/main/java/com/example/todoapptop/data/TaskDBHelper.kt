package com.example.todoapptop.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

  companion object {
    const val DATABASE_NAME = "taskDB.db"
    const val VERSION = 1
  }

  override fun onCreate(db: SQLiteDatabase?) {
    val sql = """
      CREATE TABLE ${TaskContract.TaskEntry.TABLE_NAME} (
      id INTEGER PRIMARY KEY,
      ${TaskContract.TaskEntry.COLUMN_DESCRIPTION} TEXT NOT NULL,
      ${TaskContract.TaskEntry.COLUMN_PRIORITY} INTEGER NOT NULL
      );
    """.trimIndent()

    db?.execSQL(sql)
  }

  override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    val sql = """DROP TABLE IF EXISTS ${TaskContract.TaskEntry.TABLE_NAME}"""

    db?.execSQL(sql)
  }
}