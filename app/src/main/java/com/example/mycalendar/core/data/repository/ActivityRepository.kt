package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.database.dao.ActivityDao
import com.example.mycalendar.core.database.model.ActivityEntity
import com.example.mycalendar.core.database.model.toActivity
import javax.inject.Inject

interface ActivityRepository {
    suspend fun getAllPlainActivity(): List<Activity>
    suspend fun getTaskById(activityId: Int): Activity
    suspend fun addTask(activity: Activity)
    suspend fun updateTask(activity: Activity)
    suspend fun deleteTask(activity: Activity)
}

class ActivityRepositoryImpl @Inject constructor(private val activityDao: ActivityDao): ActivityRepository {
    override suspend fun getAllPlainActivity(): List<Activity> {
        return activityDao.getAllActivityEntity().map(ActivityEntity::toActivity)
    }

    override suspend fun getTaskById(activityId: Int): Activity {
        TODO("Not yet implemented")
    }

    override suspend fun addTask(activity: Activity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(activity: Activity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(activity: Activity) {
        TODO("Not yet implemented")
    }
}