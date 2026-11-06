package com.example.crud.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) con las operaciones CRUD para la tabla de tareas.
 */
@Dao
interface TaskDao {

    // READ (Leer todas las tareas en tiempo real usando Flow)
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<Task>>

    // CREATE (Insertar nueva tarea)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    // UPDATE (Actualizar tarea existente)
    @Update
    suspend fun updateTask(task: Task)

    // DELETE (Eliminar tarea)
    @Delete
    suspend fun deleteTask(task: Task)
}
