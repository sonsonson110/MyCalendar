package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Task
import com.example.mycalendar.core.database.dao.TaskDao
import com.example.mycalendar.core.database.model.TaskAndUser
import com.example.mycalendar.core.database.model.toTask
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao): TaskRepository {
    override suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks().map(TaskAndUser::toTask)
    }
}