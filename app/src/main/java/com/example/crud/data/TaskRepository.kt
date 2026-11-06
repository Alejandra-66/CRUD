package com.example.crud.data

import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que sirve como capa de abstracción sobre el DAO de Room.
 */
class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }
}
