package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Task

interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
}