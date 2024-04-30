package com.example.mycalendar.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.mycalendar.core.database.model.TaskAndUser

@Dao
interface TaskDao {
    @Transaction
    @Query("SELECT * FROM Task")
    suspend fun getAllTasks(): List<TaskAndUser>
}