package com.example.crud.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.crud.data.Task
import com.example.crud.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel que administra el estado de la UI y gestiona los eventos CRUD.
 */
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // Estado observable que emite la lista de tareas actualizada desde Room
    val tasks: StateFlow<List<Task>> = repository.allTasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // CREAR
    fun addTask(title: String, description: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.insert(Task(title = title.trim(), description = description.trim()))
        }
    }

    // ACTUALIZAR
    fun updateTask(task: Task, newTitle: String, newDescription: String) {
        if (newTitle.isBlank()) return
        viewModelScope.launch {
            repository.update(
                task.copy(
                    title = newTitle.trim(),
                    description = newDescription.trim()
                )
            )
        }
    }

    // TOGGLE COMPLETADO
    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            repository.update(task.copy(isCompleted = !task.isCompleted))
        }
    }

    // ELIMINAR
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}
