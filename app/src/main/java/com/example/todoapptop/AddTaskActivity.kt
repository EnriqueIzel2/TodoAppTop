package com.example.todoapptop

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.todoapptop.data.TaskContract

class AddTaskActivity : AppCompatActivity() {

  private var mPriority = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_task)

    findViewById<RadioButton>(R.id.radio_button_high_priority).isChecked = true
    mPriority = 1
  }

  fun setPriorityValue(view: View) {
    mPriority = if (findViewById<RadioButton>(R.id.radio_button_high_priority).isChecked) {
      1
    } else if (findViewById<RadioButton>(R.id.radio_button_medium_priority).isChecked) {
      2
    } else {
      3
    }
  }

  fun addTask(view: View) {
    val description = findViewById<EditText>(R.id.textView_task_description).text.toString()

    if (description.isEmpty()) {
      return
    }

    val contentValues = ContentValues()
    contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, description)
    contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority)

    val uri = contentResolver.insert(TaskContract.TaskEntry.CONTENT_URI, contentValues)

    uri?.let {
      Toast.makeText(
        this,
        uri.toString(),
        Toast.LENGTH_SHORT
      ).show()
    }

    finish()
  }
}