package com.example.crud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.crud.data.TaskDatabase
import com.example.crud.data.TaskRepository
import com.example.crud.ui.TaskScreen
import com.example.crud.ui.TaskViewModel
import com.example.crud.ui.TaskViewModelFactory
import com.example.crud.ui.theme.CRUDTheme

class MainActivity : ComponentActivity() {

    private val viewModel: TaskViewModel by viewModels {
        val database = TaskDatabase.getDatabase(this)
        val repository = TaskRepository(database.taskDao())
        TaskViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CRUDTheme {
                TaskScreen(viewModel = viewModel)
            }
        }
    }
}
