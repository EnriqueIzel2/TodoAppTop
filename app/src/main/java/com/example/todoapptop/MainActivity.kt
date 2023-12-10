package com.example.todoapptop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapptop.data.TaskContract
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

  private lateinit var recyclerView: RecyclerView
  private lateinit var floatingActionButton: FloatingActionButton

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    floatingActionButton = findViewById(R.id.floatingActionButton)
    recyclerView = findViewById(R.id.recyclerView)

    recyclerView.layoutManager = LinearLayoutManager(this)
    val customCursorAdapter = CustomCursorAdapter()
    recyclerView.adapter = customCursorAdapter

    floatingActionButton.setOnClickListener {
      val intent = Intent(this, AddTaskActivity::class.java)
      startActivity(intent)
    }

    val callback =
      object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
          recyclerView: RecyclerView,
          viewHolder: RecyclerView.ViewHolder,
          target: RecyclerView.ViewHolder
        ): Boolean {
          return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
          val id = viewHolder.itemView.tag as Int
          val contentUri = TaskContract.TaskEntry.CONTENT_URI
          val uri = contentUri.buildUpon().appendPath(id.toString()).build()

          contentResolver.delete(uri, null, null)
        }
      }

    ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
  }
}