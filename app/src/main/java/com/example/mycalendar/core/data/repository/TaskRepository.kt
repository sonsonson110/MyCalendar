package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Task
import com.example.mycalendar.core.data.model.toTaskEntity
import com.example.mycalendar.core.database.dao.TaskDao
import com.example.mycalendar.core.database.model.TaskAndUser
import com.example.mycalendar.core.database.model.toTask
import javax.inject.Inject

interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun getTaskById(taskId: Int): Task
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao): TaskRepository {
    override suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks().map(TaskAndUser::toTask)
    }

    override suspend fun getTaskById(taskId: Int): Task {
        return taskDao.getTaskById(taskId).toTask()
    }

    override suspend fun addTask(task: Task) {
        taskDao.addTask(task.toTaskEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toTaskEntity())
    }
}