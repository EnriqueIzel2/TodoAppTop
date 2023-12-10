package com.example.todoapptop.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import kotlin.UnsupportedOperationException

class TaskContentProvider : ContentProvider() {

  private lateinit var taskDBHelper: TaskDBHelper

  companion object {
    const val TASKS = 100
    const val TASK_WITH_ID = 101
  }

  private val sUriMatcher: UriMatcher = buildUriMatcher()

  private fun buildUriMatcher(): UriMatcher {
    val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS)
    uriMatcher.addURI(TaskContract.AUTHORITY, "${TaskContract.PATH_TASKS}/#", TASK_WITH_ID)

    return uriMatcher
  }

  override fun onCreate(): Boolean {
    context?.let {
      taskDBHelper = TaskDBHelper(it)
    }

    return true
  }

  override fun query(
    uri: Uri,
    projection: Array<out String>?,
    selection: String?,
    selectionArgs: Array<out String>?,
    sortOrder: String?
  ): Cursor? {
    val db = taskDBHelper.readableDatabase
    val match = sUriMatcher.match(uri)
    val mCursor: Cursor?

    when (match) {
      TASKS -> {
        mCursor = db.query(
          TaskContract.TaskEntry.TABLE_NAME,
          projection,
          selection,
          selectionArgs,
          null,
          null,
          sortOrder
        )
      }

      else -> throw UnsupportedOperationException("Unknown uri $uri")
    }

    mCursor?.setNotificationUri(context?.contentResolver, uri)

    return mCursor
  }

  override fun getType(p0: Uri): String? {
    TODO("Not yet implemented")
  }

  override fun insert(uri: Uri, values: ContentValues?): Uri? {
    val db = taskDBHelper.writableDatabase
    val match = sUriMatcher.match(uri)
    val mUri: Uri?

    when (match) {
      TASKS -> {
        val id = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values)
        if (id > 0) {
          mUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id)
        } else {
          throw android.database.SQLException("Failure to insert row $uri")
        }
      }

      else -> throw UnsupportedOperationException("Unknown uri $uri")
    }

    context?.contentResolver?.notifyChange(uri, null)

    return mUri
  }

  override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
    val db = taskDBHelper.writableDatabase
    val match = sUriMatcher.match(uri)
    val taskDeleted: Int

    when (match) {
      TASK_WITH_ID -> {
        val id = uri.pathSegments?.get(1)
        taskDeleted = db.delete(
          TaskContract.TaskEntry.TABLE_NAME,
          "id=?",
          arrayOf(id)
        )
      }

      else -> throw UnsupportedOperationException("Unknown uri $uri")
    }

    if (taskDeleted != 0) {
      context?.contentResolver?.notifyChange(uri, null)
    }

    return taskDeleted
  }

  override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
    TODO("Not yet implemented")
  }

}