package com.example.todoapptop.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

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
    p0: Uri,
    p1: Array<out String>?,
    p2: String?,
    p3: Array<out String>?,
    p4: String?
  ): Cursor? {
    TODO("Not yet implemented")
  }

  override fun getType(p0: Uri): String? {
    TODO("Not yet implemented")
  }

  override fun insert(p0: Uri, p1: ContentValues?): Uri? {
    TODO("Not yet implemented")
  }

  override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
    TODO("Not yet implemented")
  }

  override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
    TODO("Not yet implemented")
  }

}