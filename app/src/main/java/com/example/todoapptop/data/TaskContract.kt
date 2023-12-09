package com.example.todoapptop.data

import android.net.Uri

class TaskContract {

  companion object {
    const val AUTHORITY = "com.example.todoapptop"
    val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")
    const val PATH_TASKS = "tasks"
  }

  object TaskEntry {
    const val TABLE_NAME = "tasks"
    const val COLUMN_DESCRIPTION = "description"
    const val COLUMN_PRIORITY = "priority"

    val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build()
  }
}