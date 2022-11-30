package com.zybooks.todolistadvanced

import ToDoList
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var toDoList = ToDoList(this)
    private lateinit var itemEditText: EditText
    private lateinit var listTextView: TextView

    //added
    private lateinit var filenameEditText: EditText
    private var filename = "todolist.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemEditText = findViewById(R.id.todo_item)
        listTextView = findViewById(R.id.item_list)

        findViewById<Button>(R.id.add_button).setOnClickListener { addButtonClick() }
        findViewById<Button>(R.id.clear_button).setOnClickListener { clearButtonClick() }

        filenameEditText = findViewById(R.id.filename_edit_text)
        filenameEditText.setText(filename)

        findViewById<Button>(R.id.remove_last_button).setOnClickListener { removeLastButtonClick() }
    }

    override fun onResume() {
        super.onResume()

        // Attempt to load a previously saved list
        val readSuccess = toDoList.readFromFile(filename)
        displayList()
    }

    override fun onPause() {
        super.onPause()

        // Save list for later
        toDoList.saveToFile(filename)
    }

    private fun addButtonClick() {

        // Ignore any leading or trailing spaces
        val item = itemEditText.text.toString().trim()

        // Clear the EditText so it's ready for another item
        itemEditText.setText("")

        // Add the item to the list and display it
        if (item.isNotEmpty()) {
            toDoList.addItem(item)
            displayList()
        }

        //added save file
        toDoList.saveToFile(filename)
    }

    private fun displayList() {

        // Display a numbered list of items
        val itemText = StringBuffer()
        val items = toDoList.getItems()
        val lineSeparator = System.getProperty("line.separator")

        for (i in items.indices) {
            itemText.append(i + 1).append(". ").append(items[i]).append(lineSeparator)
        }

        listTextView.text = itemText.toString()
    }

    private fun clearButtonClick() {
        toDoList.clear()
        displayList()
    }

    private fun removeLastButtonClick() {
        toDoList.removeLastItem()
        displayList()
    }
}