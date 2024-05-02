package com.example.mycalendar.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.mycalendar.core.database.model.TaskAndUser
import com.example.mycalendar.core.database.model.TaskEntity

@Dao
interface TaskDao {
    @Transaction
    @Query("SELECT * FROM Task")
    suspend fun getAllTasks(): List<TaskAndUser>

    @Transaction
    @Query("SELECT * FROM Task WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): TaskAndUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(taskEntity: TaskEntity)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)
}