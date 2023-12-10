package com.example.todoapptop

import android.content.Context
import android.database.Cursor
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapptop.data.TaskContract

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

  override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
    val idIndex = cursor?.getColumnIndex("id") ?: 0
    val descriptionIndex = cursor?.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION) ?: 0
    val priorityIndex = cursor?.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY) ?: 0

    cursor?.moveToPosition(position)

    val id = cursor?.getInt(idIndex)
    val description = cursor?.getString(descriptionIndex)
    val priority = cursor?.getInt(priorityIndex) ?: 0

    holder.itemView.tag = id
    holder.taskDescription.text = description
    holder.taskPriority.text = priority.toString()

    val gradientDrawable = holder.taskPriority.background as GradientDrawable
    val backgroundColor = getPriorityBackgroundColor(holder.taskDescription.context, priority)
    gradientDrawable.setColor(backgroundColor)
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

  private fun getPriorityBackgroundColor(context: Context, priority: Int): Int {
    val color = when (priority) {
      1 -> R.color.red
      2 -> R.color.orange
      else -> R.color.yellow
    }

    return ContextCompat.getColor(context, color)
  }
}