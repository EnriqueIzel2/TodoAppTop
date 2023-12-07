package com.example.todoapptop

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomCursorAdapter : RecyclerView.Adapter<CustomCursorAdapter.TaskViewHolder>() {

  private var cursor: Cursor? = null

  inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var taskDescription: TextView
    var taskPriority: TextView

    init {
      taskDescription = view.findViewById(R.id.textView_task_description)
      taskPriority = view.findViewById(R.id.textView_task_priority)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
    val context = parent.context
    val layoutResID = R.layout.task_item
    val view = LayoutInflater.from(context).inflate(layoutResID, parent, false)

    return TaskViewHolder(view)
  }

  override fun getItemCount(): Int = cursor?.count ?: 0

  fun swapCursor(c: Cursor?): Cursor? {
    if (cursor == c) {
      return null
    }

    val temp = cursor
    cursor = c

    if (c != null) {
      notifyDataSetChanged()
    }

    return temp
  }

  override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
    TODO("Not yet implemented")
  }
}