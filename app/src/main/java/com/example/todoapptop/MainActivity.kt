package com.example.todoapptop

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapptop.data.TaskContract
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

  companion object {
    const val TASK_LOADER_ID = 0
  }

  private lateinit var recyclerView: RecyclerView
  private lateinit var floatingActionButton: FloatingActionButton
  private lateinit var customCursorAdapter: CustomCursorAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    floatingActionButton = findViewById(R.id.floatingActionButton)
    recyclerView = findViewById(R.id.recyclerView)

    recyclerView.layoutManager = LinearLayoutManager(this)
    customCursorAdapter = CustomCursorAdapter()
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

          LoaderManager.getInstance(this@MainActivity).restartLoader(
            TASK_LOADER_ID,
            null,
            this@MainActivity
          )
        }
      }

    ItemTouchHelper(callback).attachToRecyclerView(recyclerView)

    LoaderManager.getInstance(this).initLoader(
      TASK_LOADER_ID,
      null,
      this
    )
  }

  override fun onResume() {
    super.onResume()

    LoaderManager.getInstance(this).restartLoader(
      TASK_LOADER_ID,
      null,
      this
    )
  }

  override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
    return object : AsyncTaskLoader<Cursor>(this) {
      var mCursor: Cursor? = null

      override fun onStartLoading() {
        if (mCursor != null) {
          deliverResult(mCursor)
        } else {
          forceLoad()
        }
      }

      override fun loadInBackground(): Cursor? {
        return try {
          contentResolver.query(
            TaskContract.TaskEntry.CONTENT_URI,
            null,
            null,
            null,
            TaskContract.TaskEntry.COLUMN_PRIORITY
          )
        } catch (e: Exception) {
          e.printStackTrace()

          null
        }
      }

      override fun deliverResult(data: Cursor?) {
        mCursor = data
        super.deliverResult(data)
      }
    }
  }

  override fun onLoaderReset(loader: Loader<Cursor>) {
    customCursorAdapter.swapCursor(null)
  }

  override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
    customCursorAdapter.swapCursor(data)
  }
}