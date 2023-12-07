package com.example.todoapptop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
  }
}